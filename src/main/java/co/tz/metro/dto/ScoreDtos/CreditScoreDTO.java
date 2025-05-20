package co.tz.metro.dto.ScoreDtos;
import lombok.Getter; // Assuming Lombok is used
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter // Generates getters
@Setter // Generates setters
@NoArgsConstructor // Generates no-arg constructor
@AllArgsConstructor // Generates all-args constructor
public class CreditScoreDTO {
    private Long creditScoreId;
    private Integer score;
    private LocalDateTime scoreDate;
    private LocalDateTime expiryDate;
    private String provider;
    private BigDecimal totalDebt;
    private Integer activeLoans;
    private Integer latePayments;
    private Integer defaultedLoans;
    private String remarks;
    private Long customerId;

    public Long getCreditScoreId() {
        return creditScoreId;
    }

    public void setCreditScoreId(Long creditScoreId) {
        this.creditScoreId = creditScoreId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public LocalDateTime getScoreDate() {
        return scoreDate;
    }

    public void setScoreDate(LocalDateTime scoreDate) {
        this.scoreDate = scoreDate;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public BigDecimal getTotalDebt() {
        return totalDebt;
    }

    public void setTotalDebt(BigDecimal totalDebt) {
        this.totalDebt = totalDebt;
    }

    public Integer getActiveLoans() {
        return activeLoans;
    }

    public void setActiveLoans(Integer activeLoans) {
        this.activeLoans = activeLoans;
    }

    public Integer getLatePayments() {
        return latePayments;
    }

    public void setLatePayments(Integer latePayments) {
        this.latePayments = latePayments;
    }

    public Integer getDefaultedLoans() {
        return defaultedLoans;
    }

    public void setDefaultedLoans(Integer defaultedLoans) {
        this.defaultedLoans = defaultedLoans;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
