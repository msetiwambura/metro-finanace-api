package co.tz.metro.fusion.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "credit_scores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditScore {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long creditScoreId;
    
    @Column(nullable = false)
    private Integer score;
    
    @Column(nullable = false)
    private LocalDateTime scoreDate;
    
    private LocalDateTime expiryDate;
    
    @Column(nullable = false)
    private String provider;
    
    private BigDecimal totalDebt;
    
    private Integer activeLoans;
    
    private Integer latePayments;
    
    private Integer defaultedLoans;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creditScore", nullable = false, unique = true)
    private Customer customer;


    @Column(length = 1000)
    private String remarks;

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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}