package org.sunbasedata.rhea.sidana.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.sunbasedata.rhea.sidana.customer.repository.model.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query(
            value = "Select * from customers " +
                    "where first_name = ?1 and " +
                    "last_name = ?2 and " +
                    "address = ?3 and " +
                    "contact = ?4 ",
            nativeQuery = true
    )
    Optional<Customer> findByCustomer(
            String firstName,
            String lastName,
            Long address,
            Long contact
    );
}
