package org.sunbasedata.rhea.sidana.customer.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sunbasedata.rhea.sidana.address.repository.model.Address;
import org.sunbasedata.rhea.sidana.address.service.AddressService;
import org.sunbasedata.rhea.sidana.authentication.exception.InvalidAccessException;
import org.sunbasedata.rhea.sidana.authentication.service.AuthenticationService;
import org.sunbasedata.rhea.sidana.contact.repository.model.Contact;
import org.sunbasedata.rhea.sidana.contact.service.ContactService;
import org.sunbasedata.rhea.sidana.customer.exception.CustomerAlreadyExistsException;
import org.sunbasedata.rhea.sidana.customer.exception.FieldIsEmptyOrBlankException;
import org.sunbasedata.rhea.sidana.customer.exception.InvalidCommandException;
import org.sunbasedata.rhea.sidana.customer.repository.CustomerRepository;
import org.sunbasedata.rhea.sidana.customer.repository.model.Customer;
import org.sunbasedata.rhea.sidana.customer.view.model.request.CreateRequest;
import org.sunbasedata.rhea.sidana.exception.UnableToSaveToDbException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private AddressService addressService;

    @Mock
    private ContactService contactService;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void shouldNotCreateCustomerWhenInvalidateAuthorizationToken() throws InvalidAccessException {
        String exceptionMessage = "Exception";
        doThrow(
                new InvalidAccessException(exceptionMessage)
        ).when(
                authenticationService
        ).validateAuthorizationToken(anyString());

        // Act & Assert
        InvalidAccessException invalidAccessException = assertThrows(InvalidAccessException.class, () ->
                customerService.createNewCustomer("invalidToken", new CreateRequest(), "create")
        );

        verify(authenticationService, times(1)).validateAuthorizationToken("invalidToken");
        assertEquals(exceptionMessage, invalidAccessException.getMessage());
    }

    @Test
    public void shouldNotCreateCustomerWhenInvalidCommand() throws InvalidAccessException {
        String exceptionMessage = "no such command found!!";
        doNothing().when(authenticationService).validateAuthorizationToken(anyString());

        // Act & Assert
        InvalidCommandException invalidCommandException = assertThrows(InvalidCommandException.class, () ->
                customerService.createNewCustomer("Token", new CreateRequest(), "invalid command")
        );

        verify(
                authenticationService,
                times(1)
        ).validateAuthorizationToken(
                "Token"
        );
        assertEquals(exceptionMessage, invalidCommandException.getMessage());
    }

    @Test
    public void shouldNotCreateCustomerWhenEmptyOrBlankFirstName() throws InvalidAccessException {
        String exceptionMessage = "First Name is empty or blank";
        doNothing().when(authenticationService).validateAuthorizationToken(anyString());
        CreateRequest mockCreateRequest = mock(CreateRequest.class);
        when(mockCreateRequest.getFirst_name()).thenReturn("");

        // Act & Assert
        FieldIsEmptyOrBlankException fieldIsEmptyOrBlankException = assertThrows(FieldIsEmptyOrBlankException.class, () ->
                customerService.createNewCustomer("Token", mockCreateRequest, "create")
        );

        verify(
                authenticationService,
                times(1)
        ).validateAuthorizationToken(
                "Token"
        );
        assertEquals(exceptionMessage, fieldIsEmptyOrBlankException.getMessage());
    }

    @Test
    public void shouldNotCreateCustomerWhenEmptyOrBlankLastName() throws InvalidAccessException {
        String exceptionMessage = "Last Name is empty or blank";
        doNothing().when(authenticationService).validateAuthorizationToken(anyString());
        CreateRequest mockCreateRequest = mock(CreateRequest.class);
        when(mockCreateRequest.getFirst_name()).thenReturn("Jane");
        when(mockCreateRequest.getLast_name()).thenReturn("");

        // Act & Assert
        FieldIsEmptyOrBlankException fieldIsEmptyOrBlankException = assertThrows(FieldIsEmptyOrBlankException.class, () ->
                customerService.createNewCustomer("Token", mockCreateRequest, "create")
        );

        verify(
                authenticationService,
                times(1)
        ).validateAuthorizationToken(
                "Token"
        );
        assertEquals(exceptionMessage, fieldIsEmptyOrBlankException.getMessage());
    }

    @Test
    public void shouldNotCreateCustomerWhenUnableToSaveToDb() throws InvalidAccessException, UnableToSaveToDbException {
        String exceptionMessage = "unable to add customer to db";
        doNothing().when(authenticationService).validateAuthorizationToken(anyString());
        CreateRequest mockCreateRequest = mock(CreateRequest.class);
        when(mockCreateRequest.getFirst_name()).thenReturn("Jane");
        when(mockCreateRequest.getLast_name()).thenReturn("Doe");
        Address mockAddress = mock(Address.class);
        when(addressService.addToDB(any(Address.class))).thenReturn(mockAddress);
        when(mockAddress.getId()).thenReturn(1L);

        Contact mockContact = mock(Contact.class);
        when(contactService.addToDB(any(Contact.class))).thenReturn(mockContact);
        when(mockContact.getId()).thenReturn(1L);
        Optional<Customer> customer = Optional.empty();
        ;
        when(customerRepository.findByCustomer(anyString(),
                anyString(),
                anyLong(),
                anyLong())
        ).thenReturn(customer);
        when(customerRepository.save(any(Customer.class))).thenReturn(null);

        // Act & Assert
        UnableToSaveToDbException unableToSaveToDbException = assertThrows(UnableToSaveToDbException.class, () ->
                customerService.createNewCustomer("Token", mockCreateRequest, "create")
        );

        verify(
                authenticationService,
                times(1)
        ).validateAuthorizationToken(
                "Token"
        );
        assertEquals(exceptionMessage, unableToSaveToDbException.getMessage());
    }

    @Test
    public void shouldNotCreateCustomerWhenCustomerAlreadyExists() throws InvalidAccessException,
            UnableToSaveToDbException {
        String exceptionMessage = "customer already exists";
        doNothing().when(authenticationService).validateAuthorizationToken(anyString());
        CreateRequest mockCreateRequest = mock(CreateRequest.class);
        when(mockCreateRequest.getFirst_name()).thenReturn("Jane");
        when(mockCreateRequest.getLast_name()).thenReturn("Doe");
        Address mockAddress = mock(Address.class);
        when(addressService.addToDB(any(Address.class))).thenReturn(mockAddress);
        when(mockAddress.getId()).thenReturn(1L);

        Contact mockContact = mock(Contact.class);
        when(contactService.addToDB(any(Contact.class))).thenReturn(mockContact);
        when(mockContact.getId()).thenReturn(1L);
        Optional<Customer> customer = Optional.of(new Customer());
        ;
        when(customerRepository.findByCustomer(anyString(),
                anyString(),
                anyLong(),
                anyLong())
        ).thenReturn(customer);
//        when(customerRepository.save(any(Customer.class))).thenReturn(null);

        // Act & Assert
        CustomerAlreadyExistsException customerAlreadyExistsException = assertThrows(CustomerAlreadyExistsException.class, () ->
                customerService.createNewCustomer("Token", mockCreateRequest, "create")
        );

        verify(
                authenticationService,
                times(1)
        ).validateAuthorizationToken(
                "Token"
        );
        assertEquals(exceptionMessage, customerAlreadyExistsException.getMessage());
    }

    @Test
    public void shouldCreateCustomer() throws InvalidAccessException,
            UnableToSaveToDbException,
            InvalidCommandException,
            FieldIsEmptyOrBlankException,
            CustomerAlreadyExistsException {

        doNothing().when(authenticationService).validateAuthorizationToken(anyString());

        CreateRequest mockCreateRequest = mock(CreateRequest.class);
        when(mockCreateRequest.getFirst_name()).thenReturn("Jane");
        when(mockCreateRequest.getLast_name()).thenReturn("Doe");

        Address mockAddress = mock(Address.class);
        when(addressService.addToDB(any(Address.class))).thenReturn(mockAddress);
        when(mockAddress.getId()).thenReturn(1L);

        Contact mockContact = mock(Contact.class);
        when(contactService.addToDB(any(Contact.class))).thenReturn(mockContact);
        when(mockContact.getId()).thenReturn(1L);

        Customer mockCustomer = mock(Customer.class);
        when(customerRepository.save(any(Customer.class))).thenReturn(mockCustomer);

        // Act
        Customer customerServiceNewCustomer = customerService.createNewCustomer(
                "Token",
                mockCreateRequest,
                "create"
        );

        verify(
                authenticationService,
                times(1)
        ).validateAuthorizationToken(
                "Token"
        );
        assertEquals(mockCustomer, customerServiceNewCustomer);
    }
}