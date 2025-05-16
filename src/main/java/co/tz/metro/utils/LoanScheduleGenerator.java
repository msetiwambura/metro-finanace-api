package co.tz.metro.utils;

import co.tz.metro.fusion.entity.Loan;
import co.tz.metro.fusion.entity.LoanSchedule;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanScheduleGenerator {
    public static List<LoanSchedule> generateSchedule(Loan loan, BigDecimal monthlyInterestRate, int numberOfMonths, LocalDate startDate) {
        List<LoanSchedule> scheduleList = new ArrayList<>();
        BigDecimal monthlyPayment = getBigDecimal(loan, monthlyInterestRate, numberOfMonths);

        for (int i = 0; i < numberOfMonths; i++) {
            LoanSchedule schedule = new LoanSchedule();
            schedule.setLoan(loan);
            schedule.setDueDate(startDate.plusMonths(i));
            schedule.setAmountDue(monthlyPayment);
            schedule.setPaid(false);
            scheduleList.add(schedule);
        }

        return scheduleList;
    }

    private static BigDecimal getBigDecimal(Loan loan, BigDecimal monthlyInterestRate, int numberOfMonths) {
        BigDecimal principal = loan.getPrincipalAmount();
        MathContext mc = new MathContext(10, RoundingMode.HALF_UP);

        // Convert interest rate from percentage to decimal
        BigDecimal interestRateDecimal = monthlyInterestRate.divide(BigDecimal.valueOf(100), mc);

        // (1 + r)^-n
        BigDecimal onePlusRate = BigDecimal.ONE.add(interestRateDecimal, mc);
        BigDecimal denominator = BigDecimal.ONE.subtract(
                onePlusRate.pow(-numberOfMonths, mc), mc
        );

        // A = P * r / (1 - (1 + r)^-n)
        return principal.multiply(interestRateDecimal, mc).divide(denominator, 2, RoundingMode.HALF_UP);
    }
}
