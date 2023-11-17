package org.sunbasedaat.rhea.sidana.address.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sunbasedaat.rhea.sidana.address.repository.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
