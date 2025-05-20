package co.tz.metro.dto.loanDtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LoanSummaryDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalLoaned;
    private BigDecimal totalProjectedProfit;
    private BigDecimal totalRepaidPrincipal;
    private BigDecimal totalRepaidInterest;
    private BigDecimal totalRemainingRepayment;
    private BigDecimal totalRemainingInterest;

    public LoanSummaryDto(LocalDate startDate, LocalDate endDate, BigDecimal totalLoaned, BigDecimal totalProjectedProfit, BigDecimal totalRepaidPrincipal, BigDecimal totalRepaidInterest, BigDecimal totalRemainingRepayment, BigDecimal totalRemainingInterest) {
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getTotalLoaned() {
        return totalLoaned;
    }

    public void setTotalLoaned(BigDecimal totalLoaned) {
        this.totalLoaned = totalLoaned;
    }

    public BigDecimal getTotalProjectedProfit() {
        return totalProjectedProfit;
    }

    public void setTotalProjectedProfit(BigDecimal totalProjectedProfit) {
        this.totalProjectedProfit = totalProjectedProfit;
    }

    public BigDecimal getTotalRepaidPrincipal() {
        return totalRepaidPrincipal;
    }

    public void setTotalRepaidPrincipal(BigDecimal totalRepaidPrincipal) {
        this.totalRepaidPrincipal = totalRepaidPrincipal;
    }

    public BigDecimal getTotalRepaidInterest() {
        return totalRepaidInterest;
    }

    public void setTotalRepaidInterest(BigDecimal totalRepaidInterest) {
        this.totalRepaidInterest = totalRepaidInterest;
    }

    public BigDecimal getTotalRemainingRepayment() {
        return totalRemainingRepayment;
    }

    public void setTotalRemainingRepayment(BigDecimal totalRemainingRepayment) {
        this.totalRemainingRepayment = totalRemainingRepayment;
    }

    public BigDecimal getTotalRemainingInterest() {
        return totalRemainingInterest;
    }

    public void setTotalRemainingInterest(BigDecimal totalRemainingInterest) {
        this.totalRemainingInterest = totalRemainingInterest;
    }
}
