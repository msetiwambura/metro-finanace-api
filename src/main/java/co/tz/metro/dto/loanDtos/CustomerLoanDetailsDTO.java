package co.tz.metro.dto.loanDtos;

import java.util.List;

public class CustomerLoanDetailsDTO {
    private Long customerId;
    private String name;
    private String email;
    private List<LoanDetailsDTO> loans;
    
    // Getters and setters


    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<LoanDetailsDTO> getLoans() {
        return loans;
    }

    public void setLoans(List<LoanDetailsDTO> loans) {
        this.loans = loans;
    }
}