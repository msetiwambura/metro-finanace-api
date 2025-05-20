package co.tz.metro.fusion.repository;

import co.tz.metro.fusion.entity.Loan;
import co.tz.metro.fusion.entity.Repayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepaymentRepository extends JpaRepository<Repayment, Long> {
    List<Repayment> findByLoanId(Long loanId);
    void deleteByLoan(Loan loan);

}