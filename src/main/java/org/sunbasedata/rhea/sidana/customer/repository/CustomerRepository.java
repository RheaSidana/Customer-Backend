package org.sunbasedata.rhea.sidana.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sunbasedata.rhea.sidana.customer.repository.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
