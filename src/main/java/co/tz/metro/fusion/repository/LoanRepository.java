package co.tz.metro.fusion.repository;
import co.tz.metro.dto.loanDtos.LoanHistoryDTO;
import co.tz.metro.fusion.entity.Loan;
import co.tz.metro.interfaces.LoanWithOutstandingAmount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByCustomerId(Long customerId);

    @Query(value = "SELECT l.id, l.principal_amount, l.interest_rate, l.start_date,\n" +
            "       l.due_date, l.status, COALESCE(SUM(ls.amount_due), 0) AS outstanding_amount\n" +
            "FROM loans l\n" +
            "LEFT JOIN loan_schedules ls ON l.id = ls.loan_id AND ls.is_paid = false\n" +
            "WHERE l.customer_id = :customerId AND l.status = 'active'\n" +
            "GROUP BY l.id, l.principal_amount, l.interest_rate, l.start_date, l.due_date, l.status", nativeQuery = true)
    List<LoanWithOutstandingAmount> findActiveLoanDetailsWithOutstandingAmounts(@Param("customerId") Long customerId);


    @Query("SELECT new co.tz.metro.dto.loanDtos.LoanHistoryDTO(l.id, l.principalAmount, l.interestRate, l.status, " +
            "l.startDate, l.dueDate, CAST(COALESCE(SUM(r.amount), 0) AS bigdecimal)) " +
            "FROM Loan l LEFT JOIN Repayment r ON r.loan.id = l.id " +
            "WHERE l.customer.id = :customerId " +
            "GROUP BY l.id, l.principalAmount, l.interestRate, l.status, l.startDate, l.dueDate " +
            "ORDER BY l.startDate DESC")
    List<LoanHistoryDTO> findLoanHistoryByCustomer(@Param("customerId") Long customerId);

    List<Loan>findByStatus(String status);
    List<Loan> findByStartDateBetween(LocalDate startDate, LocalDate endDate);
    List<Loan> findByStatusAndStartDateBetween(String status, LocalDate startDate, LocalDate endDate);

}
