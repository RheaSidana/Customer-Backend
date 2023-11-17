package org.sunbasedaat.rhea.sidana.contact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sunbasedaat.rhea.sidana.contact.repository.model.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
