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
import org.sunbasedata.rhea.sidana.customer.exception.*;
import org.sunbasedata.rhea.sidana.customer.repository.CustomerRepository;
import org.sunbasedata.rhea.sidana.customer.repository.model.Customer;
import org.sunbasedata.rhea.sidana.customer.view.model.request.CreateRequest;
import org.sunbasedata.rhea.sidana.exception.UnableToSaveToDbException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
    ) throws InvalidAccessException,
            InvalidCommandException,
            FieldIsEmptyOrBlankException,
            UnableToSaveToDbException, CustomerAlreadyExistsException {
        authenticationService.validateAuthorizationToken(authorizationHeader);

        Command command = Command.CREATE;
        validateCommand(command, cmd);

        validateFirstName(createCustomer);
        validateLastName(createCustomer);

        Customer customer = newCustomer(createCustomer);
        customer = addToDB(customer);

        return customer;
    }

    public List<Customer> getCustomerList(
            String authorizationHeader,
            String cmd
    ) throws InvalidAccessException, InvalidCommandException {
        authenticationService.validateAuthorizationToken(authorizationHeader);

        Command command = Command.GET_CUSTOMER_LIST;
        validateCommand(command, cmd);

        return getAllCustomers();
    }

    public Customer deleteCustomer(
            String authorizationHeader,
            String cmd,
            String uuid
    ) throws InvalidAccessException,
            InvalidCommandException,
            InvalidCustomerUuidException,
            CustomerNotInDbException,
            UnableToDeleteCustomerException {
        authenticationService.validateAuthorizationToken(authorizationHeader);

        Command command = Command.DELETE;
        validateCommand(command, cmd);

        Long id = Long.parseLong(uuid);
        validateCustomerUuid(id);
        Customer customer = validateCustomerInDb(id);

        deleteFromDb(id);
        return customer;
    }

    public Customer updateCustomer(
            String authorizationHeader,
            String cmd,
            String uuid,
            CreateRequest updateCustomer
    ) throws InvalidAccessException,
            InvalidCommandException,
            InvalidCustomerUuidException,
            CustomerNotInDbException,
            UnableToSaveToDbException, NoUpdateAvailableException {
        authenticationService.validateAuthorizationToken(authorizationHeader);

        Command command = Command.UPDATE;
        validateCommand(command, cmd);

        Long id = Long.parseLong(uuid);
        validateCustomerUuid(id);
        Customer customer = validateCustomerInDb(id);

        // update customer
        // if address change
        // if contact change
        // if name change
        customer = updateToDB(customer, updateCustomer);

        return customer;
    }

    private Customer updateToDB(
            Customer customer,
            CreateRequest updateCustomer
    ) throws UnableToSaveToDbException,
            NoUpdateAvailableException {
        Customer foundInDb = new Customer(customer);

        Address address = addressService.getAddress(customer.getAddress());
        address = addressService.createNewIfRequired(address, updateCustomer);

        Contact contact = contactService.getContact(customer.getContact());
        contact = contactService.createNewIfRequired(contact,updateCustomer);

        if(!customer.getFirstName().equals(updateCustomer.getFirst_name())){
            customer.setFirstName(updateCustomer.getFirst_name());
        }
        if(!customer.getLastName().equals(updateCustomer.getLast_name())){
            customer.setLastName(updateCustomer.getLast_name());
        }
        if(customer.getContact() != contact.getId()){
            customer.setContact(contact.getId());
            customer.setCustomerContact(contact);
        }
        if(customer.getAddress() != address.getId()){
            customer.setAddress(address.getId());
            customer.setCustomerAddress(address);
        }

        Customer updatedCustomer = customerRepository.save(customer);
        if(updatedCustomer == foundInDb){
            throw new NoUpdateAvailableException("No update found for the object");
        }

        if(updatedCustomer == null){
            throw new UnableToSaveToDbException("unable to save changes to db: null");
        }

        Optional<Customer> customerInDb = getById(customer.getId());
        if(customerInDb.get() != updatedCustomer){
            throw new UnableToSaveToDbException("unable to save changes to db");
        }
        return updatedCustomer;
    }

    private void deleteFromDb(Long id) throws UnableToDeleteCustomerException {
        customerRepository.deleteById(id);

        Optional<Customer> customerInDb = getById(id);
        if(customerInDb.isPresent()){
            throw new UnableToDeleteCustomerException("unable to delete customer from db");
        }
    }

    private Customer validateCustomerInDb(Long uuid) throws CustomerNotInDbException {
        Optional<Customer> customerInDb = getById(uuid);
        if(!customerInDb.isPresent()){
            throw new CustomerNotInDbException("customer not found in db");
        }

        return customerInDb.get();
    }

    private Optional<Customer> getById(Long uuid) {
        return customerRepository.findById(uuid);
    }

    private void validateCustomerUuid(Long uuid) throws InvalidCustomerUuidException {
        if(uuid < 1L){
            throw new InvalidCustomerUuidException("customer id invalid!");
        }
    }

    private List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Transactional
    private Customer addToDB(Customer customer) throws UnableToSaveToDbException, CustomerAlreadyExistsException {
        Address address = addressService.addToDB(customer.getCustomerAddress());
        Contact contact = contactService.addToDB(customer.getCustomerContact());

        customer.setAddress(address.getId());
        customer.setContact(contact.getId());

        Optional<Customer> alreadyInDB = isAlreadyInDB(customer);
        if(alreadyInDB.isPresent()){
            throw new CustomerAlreadyExistsException("customer already exists");
        }

        Customer customerInDb = customerRepository.save(customer);
        if (customerInDb == null) {
            throw new UnableToSaveToDbException("unable to add customer to db");
        }

        customerInDb.setCustomerAddress(address);
        customerInDb.setCustomerContact(contact);
        return customerInDb;
    }

    private Optional<Customer> isAlreadyInDB(Customer customer){
        Optional<Customer> customerInDb = customerRepository.findByCustomer(
                customer.getFirstName(),
                customer.getLastName(),
                customer.getAddress(),
                customer.getContact()
        );

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
