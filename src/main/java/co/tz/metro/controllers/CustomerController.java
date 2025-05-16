package co.tz.metro.controllers;

import co.tz.metro.data.ApiResponse;
import co.tz.metro.data.IdResponse;
import co.tz.metro.dto.CustomerDTO;
import co.tz.metro.dto.CustomerResponseDTO;
import co.tz.metro.dto.loanDtos.CustomerLoanDetailsDTO;
import co.tz.metro.dto.loanDtos.LoanHistoryDTO;
import co.tz.metro.fusion.entity.Customer;
import co.tz.metro.services.CustomerService;
import co.tz.metro.services.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/customers")
public class CustomerController {

    private final LoanService loanService;
    private final CustomerService customerService;

    public CustomerController(LoanService loanService, CustomerService customerService) {
        this.loanService = loanService;
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Customer>> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());
        customer.setIdNumber(customerDTO.getIdNumber());
        customer.setDateOfBirth(customerDTO.getDateOfBirth());
        Customer savedCustomer = customerService.createCustomer(customer);
        ApiResponse<Customer> response = new ApiResponse<>(
                true,
                "success",
                savedCustomer
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomerResponseDTO>>> getAllCustomers() {

        ApiResponse<List<CustomerResponseDTO>> response = new ApiResponse<>(true,"success",customerService.getAllCustomers());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/loans/active/{customerId}")
    public ResponseEntity<ApiResponse<CustomerLoanDetailsDTO>> getCustomerWithActiveLoans(@PathVariable Long customerId) {

        CustomerLoanDetailsDTO customerDetails = loanService.getCustomerWithActiveLoans(customerId);
        ApiResponse<CustomerLoanDetailsDTO> response = new ApiResponse<>(true, "success", customerDetails);
        return ResponseEntity.ok(response);
    }

    @GetMapping("loans/history/{customerId}")
    public ResponseEntity<ApiResponse<List<LoanHistoryDTO>>> getLoanHistory(@PathVariable Long customerId) {

       ApiResponse<List<LoanHistoryDTO>> response = new ApiResponse<>(true,"success",loanService.getLoanHistory(customerId));
        return ResponseEntity.ok(response);
    }
}
