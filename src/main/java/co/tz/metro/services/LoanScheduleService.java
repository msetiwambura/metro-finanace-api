package co.tz.metro.services;

import co.tz.metro.fusion.entity.Loan;
import co.tz.metro.fusion.entity.LoanSchedule;
import co.tz.metro.fusion.repository.LoanRepository;
import co.tz.metro.fusion.repository.LoanScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanScheduleService {

    @Autowired
    private LoanScheduleRepository loanScheduleRepository;

    @Autowired
    private LoanRepository loanRepository;

    public LoanSchedule createLoanSchedule(Long loanId, LoanSchedule loanSchedule) {
        Optional<Loan> loanOptional = loanRepository.findById(loanId);
        if (loanOptional.isPresent()) {
            loanSchedule.setLoan(loanOptional.get());
            return loanScheduleRepository.save(loanSchedule);
        } else {
            throw new RuntimeException("Loan not found with ID: " + loanId);
        }
    }

    public List<LoanSchedule> getLoanSchedulesByLoanId(Long loanId) {
        return loanScheduleRepository.findByLoanId(loanId);
    }
}
