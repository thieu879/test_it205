package ra.test_exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.test_exam.model.entity.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findCustomerByUsername(String username);
}

