package co.tz.metro.controllers;

import co.tz.metro.data.ApiResponse;
import co.tz.metro.data.IdResponse;
import co.tz.metro.dto.loanDtos.CustomerLoanDetailsDTO;
import co.tz.metro.dto.loanDtos.LoanRequestDTO;
import co.tz.metro.dto.loanDtos.LoanResponseDTO;
import co.tz.metro.dto.loanDtos.LoanUpdateDTO;
import co.tz.metro.fusion.entity.Loan;
import co.tz.metro.services.LoanService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
@Validated
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Loan>> createLoan(@Valid @RequestBody LoanRequestDTO loan) {

        Loan loan1 = loanService.createLoan(loan);
        ApiResponse<Loan> res = new ApiResponse<>(
                true,
                "success",
                loan1
        );
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public Loan getLoanById(@PathVariable Long id) {
        return loanService.getLoanById(id);
    }

    @GetMapping("/customer/{customerId}")
    public List<Loan> getLoansByCustomerId(@PathVariable Long customerId) {
        return loanService.getLoansByCustomerId(customerId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Loan>> updateLoan(@PathVariable Long id, @Valid @RequestBody LoanUpdateDTO requestDTO) {
        Loan updatedLoan = loanService.updateLoan(id, requestDTO);
        ApiResponse<Loan> response = new ApiResponse<>(
                true,
                "success",
                updatedLoan);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Loan>>> getAllLoans(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<Loan> loans = loanService.getLoansFiltered(status, startDate, endDate);
        ApiResponse<List<Loan>> res = new ApiResponse<>(
                true,
                "success",
                loans
        );
        return ResponseEntity.ok(res);
    }
}
