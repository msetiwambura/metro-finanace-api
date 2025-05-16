package co.tz.metro.dto.loanDtos;

import co.tz.metro.dto.CustomerResponseDTO;
import co.tz.metro.dto.RepaymentDtos.RepaymentDTO;
import co.tz.metro.dto.ScheduleDtos.LoanScheduleDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

public class LoanResponseDTO {

    private Long id;
    private Double principalAmount;
    private Double interestRate;
    private Integer termMonths;
    private LocalDate startDate;
    private LocalDate dueDate;
    private String status;

    private CustomerResponseDTO customer;

    private List<RepaymentDTO> repayments;
    private List<LoanScheduleDTO> schedules;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrincipalAmount() {
        return principalAmount;
    }

    public void setPrincipalAmount(Double principalAmount) {
        this.principalAmount = principalAmount;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
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

    public CustomerResponseDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerResponseDTO customer) {
        this.customer = customer;
    }

    public List<RepaymentDTO> getRepayments() {
        return repayments;
    }

    public void setRepayments(List<RepaymentDTO> repayments) {
        this.repayments = repayments;
    }

    public List<LoanScheduleDTO> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<LoanScheduleDTO> schedules) {
        this.schedules = schedules;
    }
}
