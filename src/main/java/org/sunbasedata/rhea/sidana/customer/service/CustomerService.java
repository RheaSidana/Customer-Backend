package org.sunbasedata.rhea.sidana.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sunbasedata.rhea.sidana.address.repository.model.Address;
import org.sunbasedata.rhea.sidana.address.service.AddressService;
import org.sunbasedata.rhea.sidana.authentication.exception.InvalidAccessException;
import org.sunbasedata.rhea.sidana.authentication.service.AuthenticationService;
import org.sunbasedata.rhea.sidana.contact.repository.model.Contact;
import org.sunbasedata.rhea.sidana.contact.service.ContactService;
import org.sunbasedata.rhea.sidana.customer.commands.Command;
import org.sunbasedata.rhea.sidana.customer.exception.FieldIsEmptyOrBlankException;
import org.sunbasedata.rhea.sidana.customer.exception.InvalidCommandException;
import org.sunbasedata.rhea.sidana.customer.repository.CustomerRepository;
import org.sunbasedata.rhea.sidana.customer.repository.model.Customer;
import org.sunbasedata.rhea.sidana.customer.view.model.request.CreateRequest;
import org.sunbasedata.rhea.sidana.exception.UnableToSaveToDbException;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ContactService contactService;

    public Customer createNewCustomer(
            String authorizationHeader,
            CreateRequest createCustomer,
            String cmd
    ) throws InvalidAccessException, InvalidCommandException, FieldIsEmptyOrBlankException, UnableToSaveToDbException {
        authenticationService.validateAuthorizationToken(authorizationHeader);

        Command command = Command.CREATE;
        validateCommand(command, cmd);

        validateFirstName(createCustomer);
        validateLastName(createCustomer);

        Customer customer = newCustomer(createCustomer);
        customer = addToDB(customer);

        return customer;
    }

    private Customer addToDB(Customer customer) throws UnableToSaveToDbException {
        Address address = addressService.addToDB(customer.getCustomerAddress());
        Contact contact = contactService.addToDB(customer.getCustomerContact());

        customer.setAddress(address.getId());
        customer.setContact(contact.getId());

        Customer customerInDb = customerRepository.save(customer);
        if(customerInDb == null){
            throw new UnableToSaveToDbException("unable to add customer to db");
        }

        customerInDb.setCustomerAddress(address);
        customerInDb.setCustomerContact(contact);
        return customerInDb;
    }

    private Customer newCustomer(CreateRequest createCustomer) {
        Customer customer = new Customer();
        customer.setFirstName(createCustomer.getFirst_name());
        customer.setLastName(createCustomer.getLast_name());
        customer.setCustomerAddress(new Address(
                0L,
                createCustomer.getAddress(),
                createCustomer.getStreet(),
                createCustomer.getCity(),
                createCustomer.getState()
        ));
        customer.setCustomerContact(new Contact(
                0L,
                createCustomer.getEmail(),
                createCustomer.getPhone()
        ));
        return customer;
    }

    private void validateLastName(CreateRequest createCustomer) throws FieldIsEmptyOrBlankException {
        validateField(createCustomer.getLast_name(), "Last Name");
    }

    private void validateFirstName(CreateRequest createCustomer) throws FieldIsEmptyOrBlankException {
        validateField(createCustomer.getFirst_name(), "First Name");
    }

    private void validateField(String field, String fieldName) throws FieldIsEmptyOrBlankException {
        if (field.isEmpty() || field.isBlank()) {
            throw new FieldIsEmptyOrBlankException(fieldName + " is empty or blank");
        }
    }

    private void validateCommand(Command command, String cmd) throws InvalidCommandException {
        if (!command.getCmd().equals(cmd)) {
            throw new InvalidCommandException("no such command found!!");
        }
    }
}
