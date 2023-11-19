package org.sunbasedata.rhea.sidana.address.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.sunbasedata.rhea.sidana.address.repository.model.Address;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query(
            value = "Select * from Address " +
                    "where address=?1 and " +
                    "street=?2 and " +
                    "city=?3 and " +
                    "state=?4",
            nativeQuery = true
    )
    Optional<Address> findByAddress(String address, String street, String city, String state);
}
