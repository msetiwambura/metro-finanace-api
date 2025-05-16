package co.tz.metro.services;
import co.tz.metro.dto.CustomerResponseDTO;
import co.tz.metro.fusion.entity.Customer;
import co.tz.metro.fusion.repository.CustomerRepository;
import co.tz.metro.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    @Autowired
    Utils utils;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(Customer customer) {
        String id = utils.generateUniqueRef(customer.getIdNumber());
        customer.setIdNumber(id);
        customer.setCreatedAt(LocalDateTime.now());
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public List<CustomerResponseDTO> getAllCustomers() {
        return customerRepository.findAll().stream().map(
                customer -> {
                    CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();
                    customerResponseDTO.setId(customer.getId());
                    customerResponseDTO.setName(customer.getName());
                    customerResponseDTO.setEmail(customer.getEmail());
                    customerResponseDTO.setCreatedAt(customer.getCreatedAt());
                    customerResponseDTO.setPhone(customer.getPhone());
                    customerResponseDTO.setIdNumber(customer.getIdNumber());
                    customerResponseDTO.setDateOfBirth(customer.getDateOfBirth());
                    return customerResponseDTO;
                }
        ).collect(Collectors.toList());
    }
}
