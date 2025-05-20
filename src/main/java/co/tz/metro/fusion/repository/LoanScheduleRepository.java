package co.tz.metro.fusion.repository;

import co.tz.metro.fusion.entity.Loan;
import co.tz.metro.fusion.entity.LoanSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanScheduleRepository extends JpaRepository<LoanSchedule, Long> {
    List<LoanSchedule> findByLoanId(Long loanId);
    List<LoanSchedule> findByLoanAndIsPaidFalseOrderByDueDateAsc(Loan loan);
    List<LoanSchedule> findByLoanIdAndIsPaidFalse(Long loanId);
    List<LoanSchedule> findByLoan(Loan loan);
}