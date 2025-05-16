package co.tz.metro.dto.loanDtos;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LoanUpdateDTO {
    @Positive(message = "Principal amount must be greater than zero")
    private BigDecimal principalAmount;
    @PositiveOrZero(message = "Interest rate must be zero or positive")
    private BigDecimal interestRate;
    @Min(value = 1, message = "Term must be at least 1 month")
    private Integer termMonths;
    private LocalDate startDate;
    private LocalDate dueDate;
    private String status;

    private LocalDate firstRepaymentDate;
    private Long customerId;

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

    public Integer getTermMonths() {
        return termMonths;
    }

    public void setTermMonths(Integer termMonths) {
        this.termMonths = termMonths;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public LocalDate getFirstRepaymentDate() {
        return firstRepaymentDate;
    }

    public void setFirstRepaymentDate(LocalDate firstRepaymentDate) {
        this.firstRepaymentDate = firstRepaymentDate;
    }
}
