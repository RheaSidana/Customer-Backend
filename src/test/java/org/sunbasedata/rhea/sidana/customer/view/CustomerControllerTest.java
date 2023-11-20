package org.sunbasedata.rhea.sidana.customer.view;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.sunbasedata.rhea.sidana.authentication.exception.InvalidAccessException;
import org.sunbasedata.rhea.sidana.customer.exception.FieldIsEmptyOrBlankException;
import org.sunbasedata.rhea.sidana.customer.exception.InvalidCommandException;
import org.sunbasedata.rhea.sidana.customer.repository.model.Customer;
import org.sunbasedata.rhea.sidana.customer.service.CustomerService;
import org.sunbasedata.rhea.sidana.customer.view.model.request.CreateRequest;
import org.sunbasedata.rhea.sidana.customer.view.model.response.Success;
import org.sunbasedata.rhea.sidana.customer.view.model.response.Error;
import org.sunbasedata.rhea.sidana.exception.UnableToSaveToDbException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerControllerTest {

    @Test
    public void shouldNotCreateCustomerWhenInvalidAccess() throws Exception {
        // Arrange
        CustomerService mockCustomerService = mock(CustomerService.class);
        String exceptionMessage = "Exception";
        CreateRequest createRequest = new CreateRequest();

        when(mockCustomerService.createNewCustomer(
                        "token",
                        createRequest,
                        "create"
                )
        ).thenThrow(new InvalidAccessException(exceptionMessage));

        Error error = new Error("Failure", exceptionMessage);

        // Act
        CustomerController customerController = new CustomerController(mockCustomerService);
        ResponseEntity<Object> response = customerController.createNewCustomer(
                "token",
                "create",
                createRequest
        );

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(error, response.getBody());
    }

    @Test
    public void shouldNotCreateCustomerWhenInvalidCommand() throws Exception {
        // Arrange
        CustomerService mockCustomerService = mock(CustomerService.class);
        String exceptionMessage = "Exception";
        CreateRequest createRequest = new CreateRequest();

        when(mockCustomerService.createNewCustomer(
                        "token",
                        createRequest,
                        "create"
                )
        ).thenThrow(new InvalidCommandException(exceptionMessage));

        Error error = new Error("Invalid Command", exceptionMessage);

        // Act
        CustomerController customerController = new CustomerController(mockCustomerService);
        ResponseEntity<Object> response = customerController.createNewCustomer(
                "token",
                "create",
                createRequest
        );

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(error, response.getBody());
    }

    @Test
    public void shouldNotCreateCustomerWhenFieldIsEmptyOrBlank() throws Exception {
        // Arrange
        CustomerService mockCustomerService = mock(CustomerService.class);
        String exceptionMessage = "Exception";
        CreateRequest createRequest = new CreateRequest();

        when(mockCustomerService.createNewCustomer(
                        "token",
                        createRequest,
                        "create"
                )
        ).thenThrow(new FieldIsEmptyOrBlankException(exceptionMessage));

        Error error = new Error("Invalid Field", exceptionMessage);

        // Act
        CustomerController customerController = new CustomerController(mockCustomerService);
        ResponseEntity<Object> response = customerController.createNewCustomer(
                "token",
                "create",
                createRequest
        );

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(error, response.getBody());
    }

    @Test
    public void shouldNotCreateCustomerWhenUnableToSaveToDb() throws Exception {
        // Arrange
        CustomerService mockCustomerService = mock(CustomerService.class);
        String exceptionMessage = "Exception";
        CreateRequest createRequest = new CreateRequest();

        when(mockCustomerService.createNewCustomer(
                        "token",
                        createRequest,
                        "create"
                )
        ).thenThrow(new UnableToSaveToDbException(exceptionMessage));

        Error error = new Error("DB Error", exceptionMessage);

        // Act
        CustomerController customerController = new CustomerController(mockCustomerService);
        ResponseEntity<Object> response = customerController.createNewCustomer(
                "token",
                "create",
                createRequest
        );

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(error, response.getBody());
    }

    @Test
    public void shouldCreateCustomer() throws Exception {
        // Arrange
        CustomerService mockCustomerService = mock(CustomerService.class);
        Customer mockCustomer = new Customer();
        String exceptionMessage = "Exception";
        CreateRequest createRequest = new CreateRequest();

        when(mockCustomerService.createNewCustomer(
                        "token",
                        createRequest,
                        "create"
                )
        ).thenReturn(mockCustomer);

        Success createResponse = new Success("Customer created successfully", mockCustomer);

        // Act
        CustomerController customerController = new CustomerController(mockCustomerService);
        ResponseEntity<Object> response = customerController.createNewCustomer(
                "token",
                "create",
                createRequest
        );

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createResponse, response.getBody());
    }
}