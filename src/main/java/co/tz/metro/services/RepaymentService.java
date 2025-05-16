package co.tz.metro.services;

import co.tz.metro.dto.RepaymentDtos.RepaymentDTO;
import co.tz.metro.fusion.entity.Loan;
import co.tz.metro.fusion.entity.LoanSchedule;
import co.tz.metro.fusion.entity.Repayment;
import co.tz.metro.fusion.repository.LoanRepository;
import co.tz.metro.fusion.repository.LoanScheduleRepository;
import co.tz.metro.fusion.repository.RepaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RepaymentService {

    @Autowired
    private RepaymentRepository repaymentRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LoanScheduleRepository loanScheduleRepository;

    @Value("${metro.loan.closed_status}")
    private String closed;

    @Transactional
    public Repayment makeRepayment(RepaymentDTO dto, Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found with id: " + loanId));

        // Create repayment record
        Repayment repayment = new Repayment();
        repayment.setLoan(loan);
        repayment.setAmount(dto.getAmount());
        repayment.setPaymentDate(dto.getPaymentDate() != null ? dto.getPaymentDate() : LocalDate.now());
        repayment.setPaymentMethod(dto.getPaymentMethod());

        // Save repayment
        Repayment savedRepayment = repaymentRepository.save(repayment);

        // Apply repayment to unpaid schedules
        List<LoanSchedule> schedules = loanScheduleRepository.findByLoanAndIsPaidFalseOrderByDueDateAsc(loan);
        BigDecimal remainingAmount = BigDecimal.valueOf(dto.getAmount());

        for (LoanSchedule schedule : schedules) {
            BigDecimal amountDue = schedule.getAmountDue();

            if (remainingAmount.compareTo(amountDue) >= 0) {
                // Full payment of this schedule
                schedule.setPaid(true);
                remainingAmount = remainingAmount.subtract(amountDue);
                loanScheduleRepository.save(schedule);
            } else {
                // Partial payment (optional: track partials)
                break;
            }

            if (remainingAmount.compareTo(BigDecimal.ZERO) <= 0) {
                break;
            }
        }

        // Check if all schedules are paid => close the loan
        boolean allSchedulesPaid = loanScheduleRepository.findByLoanAndIsPaidFalseOrderByDueDateAsc(loan).isEmpty();
        if (allSchedulesPaid) {
            loan.setStatus(closed); // Or use "loan.setStatus("closed");" if using string
            loanRepository.save(loan);
        }

        return savedRepayment;
    }



    // Get all repayments
    public List<Repayment> getAllRepayments() {
        return repaymentRepository.findAll();
    }

    // Get repayment by ID
    public Repayment getRepaymentById(Long id) {
        return repaymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Repayment not found with id: " + id));
    }

    // Get repayments by loan ID
    public List<Repayment> getRepaymentsByLoanId(Long loanId) {
        return repaymentRepository  .findByLoanId(loanId);
    }
}
