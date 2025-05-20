package co.tz.metro.utils;

import co.tz.metro.fusion.entity.Loan;
import co.tz.metro.fusion.entity.LoanSchedule;
import co.tz.metro.fusion.entity.Repayment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;

public class LoanCalculationUtil {

    public static BigDecimal calculatePrincipalPart(Repayment repayment) {
        Loan loan = repayment.getLoan();
        List<LoanSchedule> schedules = loan.getSchedule()
                                           .stream()
                                           .sorted(Comparator.comparing(LoanSchedule::getDueDate))
                                           .toList();

        double remainingAmount = repayment.getAmount();
        BigDecimal principalPortion = BigDecimal.ZERO;

        for (LoanSchedule schedule : schedules) {
            if (Boolean.TRUE.equals(schedule.getPaid())) continue;

            BigDecimal estimatedPrincipal = estimatePrincipalFromSchedule(schedule, loan.getInterestRate());
            BigDecimal estimatedInterest = schedule.getAmountDue().subtract(estimatedPrincipal);

            if (remainingAmount >= schedule.getAmountDue().doubleValue()) {
                principalPortion = principalPortion.add(estimatedPrincipal);
                remainingAmount -= schedule.getAmountDue().doubleValue();
            } else {
                // Partial payment
                double ratio = remainingAmount / schedule.getAmountDue().doubleValue();
                principalPortion = principalPortion.add(estimatedPrincipal.multiply(BigDecimal.valueOf(ratio)));
                break;
            }
        }

        return principalPortion;
    }

    public static BigDecimal calculateInterestPart(Repayment repayment) {
        return BigDecimal.valueOf(repayment.getAmount()).subtract(calculatePrincipalPart(repayment));
    }

    private static BigDecimal estimatePrincipalFromSchedule(LoanSchedule schedule, BigDecimal interestRate) {
        // Estimate principal as: P = A / (1 + r) where r = monthly interest
        // This is a simplified estimation
        BigDecimal monthlyRate = interestRate.divide(BigDecimal.valueOf(12 * 100), 6, RoundingMode.HALF_UP);
        return schedule.getAmountDue().divide(BigDecimal.ONE.add(monthlyRate), 2, RoundingMode.HALF_UP);
    }
}
