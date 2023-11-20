package org.sunbasedata.rhea.sidana.customer.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sunbasedata.rhea.sidana.authentication.exception.InvalidAccessException;
import org.sunbasedata.rhea.sidana.customer.exception.*;
import org.sunbasedata.rhea.sidana.customer.repository.model.Customer;
import org.sunbasedata.rhea.sidana.customer.service.CustomerService;
import org.sunbasedata.rhea.sidana.customer.view.model.request.CreateRequest;
import org.sunbasedata.rhea.sidana.customer.view.model.response.Success;
import org.sunbasedata.rhea.sidana.customer.view.model.response.Error;
import org.sunbasedata.rhea.sidana.exception.UnableToSaveToDbException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/sunbase/portal/api/assignment.jsp")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("")
    public ResponseEntity<Object> createNewCustomer(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam(required = true, name = "cmd") String cmd,
            @Valid @RequestBody CreateRequest createCustomer
    ) {
        Customer createdCustomer;
        try {
            createdCustomer = customerService.createNewCustomer(authorizationHeader, createCustomer, cmd);
        }
        catch (InvalidAccessException ex) {
            Error error = new Error("Failure", ex.getMessage());

            return ResponseEntity.status(
                    HttpStatus.BAD_REQUEST
            ).body(
                    error
            );
        }
        catch (InvalidCommandException ex){
            Error error = new Error("Invalid Command", ex.getMessage());

            return ResponseEntity.status(
                    HttpStatus.INTERNAL_SERVER_ERROR
            ).body(
                    error
            );
        }
        catch (FieldIsEmptyOrBlankException ex){
            Error error = new Error("Invalid Field", ex.getMessage());

            return ResponseEntity.status(
                    HttpStatus.BAD_REQUEST
            ).body(
                    error
            );
        }
        catch (UnableToSaveToDbException ex){
            Error error = new Error("DB Error", ex.getMessage());

            return ResponseEntity.status(
                    HttpStatus.INTERNAL_SERVER_ERROR
            ).body(
                    error
            );
        }
        catch (CustomerAlreadyExistsException ex){
            Error error = new Error("DB Error", ex.getMessage());

            return ResponseEntity.status(
                    HttpStatus.BAD_REQUEST
            ).body(
                    error
            );
        }

        return ResponseEntity.status(
//                201
                HttpStatus.CREATED
        ).body(
                new Success("Customer created successfully", createdCustomer)
        );
    }

    @GetMapping("")
    public ResponseEntity<Object> getCustomerList(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam(required = true, name = "cmd") String cmd
    ){
        List<Customer> customers ;
        try{
            customers = customerService.getCustomerList(authorizationHeader, cmd);
        } catch (InvalidAccessException ex) {
            Error error = new Error("Failure", ex.getMessage());

            return ResponseEntity.status(
                    HttpStatus.BAD_REQUEST
            ).body(
                    error
            );
        }
        catch (InvalidCommandException ex){
            Error error = new Error("Invalid Command", ex.getMessage());

            return ResponseEntity.status(
                    HttpStatus.INTERNAL_SERVER_ERROR
            ).body(
                    error
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                customers
        );
    }

    @DeleteMapping("")
    public ResponseEntity<Object> deleteCustomer(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam(required = true, name = "cmd") String cmd,
            @RequestParam(required = true, name = "uuid") String uuid
    ){
        Customer customer;
        try{
            customer = customerService.deleteCustomer(
                    authorizationHeader,
                    cmd,
                    uuid
            );
        }
        catch (InvalidAccessException ex) {
            Error error = new Error("Failure", ex.getMessage());

            return ResponseEntity.status(
                    HttpStatus.BAD_REQUEST
            ).body(
                    error
            );
        }
        catch (InvalidCommandException ex){
            Error error = new Error("Invalid Command", ex.getMessage());

            return ResponseEntity.status(
                    HttpStatus.INTERNAL_SERVER_ERROR
            ).body(
                    error
            );
        }
        catch (InvalidCustomerUuidException ex) {
            Error error = new Error("UUID not found", ex.getMessage());

            return ResponseEntity.status(
                    HttpStatus.BAD_REQUEST
            ).body(
                    error
            );
        }
        catch (CustomerNotInDbException ex) {
            Error error = new Error("UUID not found", ex.getMessage());

            return ResponseEntity.status(
                    HttpStatus.BAD_REQUEST
            ).body(
                    error
            );
        }
        catch (UnableToDeleteCustomerException ex){
            Error error = new Error("Error Not Deleted", ex.getMessage());

            return ResponseEntity.status(
                    HttpStatus.INTERNAL_SERVER_ERROR
            ).body(
                    error
            );
        }

        return ResponseEntity.status(
                HttpStatus.OK
        ).body(
                new Success("successfully deleted", customer)
        );
    }
}
