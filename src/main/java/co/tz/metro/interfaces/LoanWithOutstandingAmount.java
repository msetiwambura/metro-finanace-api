package co.tz.metro.interfaces;

import java.time.LocalDate;

public interface LoanWithOutstandingAmount {
    Long getId();
    Double getPrincipalAmount();
    Double getInterestRate();
    LocalDate getStartDate();
    LocalDate getDueDate();
    String getStatus();
    Double getOutstandingAmount();
}