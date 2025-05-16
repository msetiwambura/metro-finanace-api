package co.tz.metro.dto.loanDtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LoanHistoryDTO {
    private Long loanId;
    private BigDecimal principalAmount;
    private BigDecimal interestRate;
    private String status;
    private LocalDate startDate;
    private LocalDate dueDate;
    private BigDecimal totalRepaid;

    public LoanHistoryDTO(Long loanId, BigDecimal principalAmount, BigDecimal interestRate,
                          String status, LocalDate startDate, LocalDate dueDate, BigDecimal totalRepaid) {
        this.loanId = loanId;
        this.principalAmount = principalAmount;
        this.interestRate = interestRate;
        this.status = status;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.totalRepaid = totalRepaid;
    }


    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public BigDecimal getPrincipalAmount() {
        return principalAmount;
    }

    public void setPrincipalAmount(BigDecimal principalAmount) {
        this.principalAmount = principalAmount;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getTotalRepaid() {
        return totalRepaid;
    }

    public void setTotalRepaid(BigDecimal totalRepaid) {
        this.totalRepaid = totalRepaid;
    }

    // getters
}
