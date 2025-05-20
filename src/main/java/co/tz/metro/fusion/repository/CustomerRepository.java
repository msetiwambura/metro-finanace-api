package co.tz.metro.fusion.repository;

import co.tz.metro.fusion.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByEmail(String email);
    List<Customer> findAllByOrderByCreatedAtDesc();
}
