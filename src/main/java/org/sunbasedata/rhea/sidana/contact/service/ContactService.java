package org.sunbasedata.rhea.sidana.contact.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sunbasedata.rhea.sidana.contact.repository.ContactRepository;
import org.sunbasedata.rhea.sidana.contact.repository.model.Contact;
import org.sunbasedata.rhea.sidana.exception.UnableToSaveToDbException;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;

    public Contact addToDB(Contact customerContact) throws UnableToSaveToDbException {
        Contact contact = contactRepository.save(customerContact);
        if(contact == null){
            throw new UnableToSaveToDbException("unable to save contact to db");
        }
        return contact;
    }
}
