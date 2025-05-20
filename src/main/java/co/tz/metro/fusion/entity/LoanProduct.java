package co.tz.metro.fusion.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
@Entity
@Table(name = "loan_products")
@NoArgsConstructor
@AllArgsConstructor
public class LoanProduct {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String name;
    
    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false)
    private BigDecimal minAmount;
    
    @Column(nullable = false)
    private BigDecimal maxAmount;
    
    @Column(nullable = false)
    private BigDecimal baseInterestRate;
    
    @Column(nullable = false)
    private Integer minTermMonths;
    
    @Column(nullable = false)
    private Integer maxTermMonths;
    
    @Column(nullable = false)
    private Boolean requiresCollateral;
    
    private BigDecimal processingFeePercent;
    
    private BigDecimal latePaymentFeePercent;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InterestType interestType;
    
    @Column(nullable = false)
    private Boolean active = true;

    @OneToMany(mappedBy = "loanProduct", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Loan> loans;


    public enum InterestType {
        FIXED,
        FLAT,
        REDUCING_BALANCE,
        COMPOUND
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }

    public BigDecimal getBaseInterestRate() {
        return baseInterestRate;
    }

    public void setBaseInterestRate(BigDecimal baseInterestRate) {
        this.baseInterestRate = baseInterestRate;
    }

    public Integer getMinTermMonths() {
        return minTermMonths;
    }

    public void setMinTermMonths(Integer minTermMonths) {
        this.minTermMonths = minTermMonths;
    }

    public Integer getMaxTermMonths() {
        return maxTermMonths;
    }

    public void setMaxTermMonths(Integer maxTermMonths) {
        this.maxTermMonths = maxTermMonths;
    }

    public Boolean getRequiresCollateral() {
        return requiresCollateral;
    }

    public void setRequiresCollateral(Boolean requiresCollateral) {
        this.requiresCollateral = requiresCollateral;
    }

    public BigDecimal getProcessingFeePercent() {
        return processingFeePercent;
    }

    public void setProcessingFeePercent(BigDecimal processingFeePercent) {
        this.processingFeePercent = processingFeePercent;
    }

    public BigDecimal getLatePaymentFeePercent() {
        return latePaymentFeePercent;
    }

    public void setLatePaymentFeePercent(BigDecimal latePaymentFeePercent) {
        this.latePaymentFeePercent = latePaymentFeePercent;
    }

    public InterestType getInterestType() {
        return interestType;
    }

    public void setInterestType(InterestType interestType) {
        this.interestType = interestType;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }
}