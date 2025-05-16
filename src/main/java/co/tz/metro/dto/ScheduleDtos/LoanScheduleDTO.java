package co.tz.metro.dto.ScheduleDtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LoanScheduleDTO {
    private Long id;
    private LocalDate dueDate;
    private Double amountDue;
    private Boolean isPaid;
    private Long loanId; // Reference by loan ID only
}
