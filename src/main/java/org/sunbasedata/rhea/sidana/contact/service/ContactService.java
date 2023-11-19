package org.sunbasedata.rhea.sidana.contact.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sunbasedata.rhea.sidana.contact.repository.ContactRepository;
import org.sunbasedata.rhea.sidana.contact.repository.model.Contact;
import org.sunbasedata.rhea.sidana.exception.UnableToSaveToDbException;

import java.util.Optional;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;

    public Contact addToDB(Contact customerContact) throws UnableToSaveToDbException {
        Optional<Contact> alreadyInDb = isAlreadyInDb(customerContact);
        if(alreadyInDb.isPresent()){
            return alreadyInDb.get();
        }

        Contact contact = contactRepository.save(customerContact);
        if(contact == null){
            throw new UnableToSaveToDbException("unable to save contact to db");
        }
        return contact;
    }

    private Optional<Contact> isAlreadyInDb(Contact contact){
        Optional<Contact> contactInDb = contactRepository.findByEmailAndPhone(
                contact.getEmail(),
                contact.getPhone()
        );

        return contactInDb;
    }
}
