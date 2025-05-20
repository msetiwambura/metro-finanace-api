package co.tz.metro.services;

import co.tz.metro.dto.loanDtos.*;
import co.tz.metro.fusion.entity.Customer;
import co.tz.metro.fusion.entity.Loan;
import co.tz.metro.fusion.entity.LoanSchedule;
import co.tz.metro.fusion.repository.CustomerRepository;
import co.tz.metro.fusion.repository.LoanRepository;
import co.tz.metro.fusion.repository.LoanScheduleRepository;
import co.tz.metro.fusion.repository.RepaymentRepository;
import co.tz.metro.interfaces.LoanWithOutstandingAmount;
import co.tz.metro.utils.LoanCalculationUtil;
import co.tz.metro.utils.LoanScheduleGenerator;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static co.tz.metro.utils.LoanCalculationUtil.calculateInterestPart;
import static co.tz.metro.utils.LoanCalculationUtil.calculatePrincipalPart;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository;
    private final LoanScheduleRepository loanScheduleRepository;
    private final RepaymentRepository repaymentRepository;

    public LoanService(LoanRepository loanRepository, CustomerRepository customerRepository, LoanScheduleRepository loanScheduleRepository, RepaymentRepository repaymentRepository) {
        this.loanRepository = loanRepository;
        this.customerRepository = customerRepository;
        this.loanScheduleRepository = loanScheduleRepository;
        this.repaymentRepository = repaymentRepository;
    }

    @Transactional
    public Loan createLoan(LoanRequestDTO loan) {
        Customer customer = customerRepository.findById(loan.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Loan ln = new Loan();
        ln.setCustomer(customer);
        ln.setStatus(loan.getStatus());
        ln.setStartDate(loan.getStartDate());
        ln.setPrincipalAmount(loan.getPrincipalAmount());
        ln.setInterestRate(loan.getInterestRate());
        ln.setDueDate(loan.getDueDate());
        ln.setFirstRepaymentDate(loan.getFirstRepaymentDate());
        ln.setTermMonths(loan.getTermMonths());
        ln.setDueDate(loan.getStartDate().plusMonths(loan.getTermMonths()));

        Loan newLoan = loanRepository.save(ln);
        List<LoanSchedule> schedules = LoanScheduleGenerator.generateSchedule(
                newLoan,
                newLoan.getInterestRate(),
                newLoan.getTermMonths(),
                newLoan.getFirstRepaymentDate()
        );
        loanScheduleRepository.saveAll(schedules);
        return newLoan;
    }

    public Loan getLoanById(Long id) {
        return loanRepository.findById(id).orElse(null);
    }

    public List<Loan> getLoansByCustomerId(Long customerId) {
        return loanRepository.findByCustomerId(customerId);
    }


    @Transactional
    public Loan updateLoan(Long loanId, LoanUpdateDTO request) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found with id: " + loanId));

        if (request.getPrincipalAmount() != null) {
            loan.setPrincipalAmount(request.getPrincipalAmount());
        }

        if (request.getInterestRate() != null) {
            loan.setInterestRate(request.getInterestRate());
        }

        if (request.getTermMonths() != null) {
            loan.setTermMonths(request.getTermMonths());
        }

        if (request.getStartDate() != null) {
            loan.setStartDate(request.getStartDate());
        }

        if (request.getDueDate() != null) {
            loan.setDueDate(request.getDueDate());
        }

        if (request.getFirstRepaymentDate() != null) {
            loan.setFirstRepaymentDate(request.getFirstRepaymentDate());
        }

        if (request.getCustomerId() != null) {
            Customer customer = customerRepository.findById(request.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Customer not found with id: " + request.getCustomerId()));
            loan.setCustomer(customer);
        }

        if (request.getStatus() != null && !request.getStatus().isBlank()) {
            if (request.getStatus().equalsIgnoreCase("re-activate")) {
                repaymentRepository.deleteByLoan(loan);
                List<LoanSchedule> schedules = loanScheduleRepository.findByLoan(loan);
                for (LoanSchedule schedule : schedules) {
                    schedule.setPaid(false);
                }
                loanScheduleRepository.saveAll(schedules);
                loan.setStatus("active");
            } else {
                loan.setStatus(request.getStatus());
            }
        }

        return loanRepository.save(loan);
    }


    public CustomerLoanDetailsDTO getCustomerWithActiveLoans(Long customerId) {
        // First, retrieve the customer
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + customerId));

        // Create the customer DTO
        CustomerLoanDetailsDTO customerDetails = new CustomerLoanDetailsDTO();
        customerDetails.setCustomerId(customer.getId());
        customerDetails.setName(customer.getName());
        customerDetails.setEmail(customer.getEmail());
        // Get loan details with outstanding amounts in a single query
        List<LoanWithOutstandingAmount> loanDetails = loanRepository.findActiveLoanDetailsWithOutstandingAmounts(customerId);
        List<LoanDetailsDTO> loanDetailsList = loanDetails.stream()
                .map(loan -> {
                    LoanDetailsDTO dto = new LoanDetailsDTO();
                    dto.setLoanId(loan.getId());
                    dto.setPrincipalAmount(loan.getPrincipalAmount());
                    dto.setInterestRate(loan.getInterestRate());
                    dto.setStartDate(loan.getStartDate());
                    dto.setDueDate(loan.getDueDate());
                    dto.setOutstandingAmount(loan.getOutstandingAmount());
                    return dto;
                })
                .collect(Collectors.toList());
        customerDetails.setLoans(loanDetailsList);
        return customerDetails;
    }

    public List<LoanHistoryDTO> getLoanHistory(Long customerId) {
        return loanRepository.findLoanHistoryByCustomer(customerId);
    }


    public List<Loan> getLoansFiltered(String status, LocalDate startDate, LocalDate endDate) {
        System.out.println("This is status : "+status);
        Sort sort = Sort.by(Sort.Direction.ASC, "startDate");
        if (status != null && startDate != null && endDate != null) {
            return loanRepository.findByStatusAndStartDateBetween(status, startDate, endDate, sort);
        } else if (status != null) {
            return loanRepository.findByStatus(status, sort);
        } else if (startDate != null && endDate != null) {
            return loanRepository.findByStartDateBetween(startDate, endDate, sort);
        } else {
            return loanRepository.findByStatusIn(List.of("pending", "active"), sort);
        }
    }


    public LoanSummaryDto getLoanSummary(LocalDate startDate, LocalDate endDate) {
        Sort sort = Sort.by(Sort.Direction.DESC, "startDate");
        List<Loan> loans = loanRepository.findByStartDateBetween(startDate, endDate, sort);
        if (loans.isEmpty()) {
            return null;
        } else {
            BigDecimal totalLoaned = loans.stream()
                    .map(Loan::getPrincipalAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal totalProjectedProfit = loans.stream()
                    .map(loan -> loan.getPrincipalAmount()
                            .multiply(loan.getInterestRate().divide(new BigDecimal("100"))))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal totalRepaidPrincipal = loans.stream()
                    .flatMap(loan -> loan.getRepayments().stream())
                    .map(LoanCalculationUtil::calculatePrincipalPart) // implement this
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal totalRepaidInterest = loans.stream()
                    .flatMap(loan -> loan.getRepayments().stream())
                    .map(LoanCalculationUtil::calculateInterestPart) // implement this
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal totalRemainingRepayment = totalLoaned.subtract(totalRepaidPrincipal);
            BigDecimal totalRemainingInterest = totalProjectedProfit.subtract(totalRepaidInterest);

            return new LoanSummaryDto(startDate, endDate,
                    totalLoaned, totalProjectedProfit,
                    totalRepaidPrincipal, totalRepaidInterest,
                    totalRemainingRepayment, totalRemainingInterest);
        }
    }
}
