package org.sunbasedata.rhea.sidana.contact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.sunbasedata.rhea.sidana.contact.repository.model.Contact;

import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    @Query(
            value = "Select * from contacts " +
                    "where email = ?1 and " +
                    "phone = ?2",
            nativeQuery = true
    )
    Optional<Contact> findByEmailAndPhone(String email, String phone);
}
