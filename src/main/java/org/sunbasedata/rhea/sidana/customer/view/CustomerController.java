package org.sunbasedata.rhea.sidana.customer.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sunbasedata.rhea.sidana.authentication.exception.InvalidAccessException;
import org.sunbasedata.rhea.sidana.customer.exception.CustomerAlreadyExistsException;
import org.sunbasedata.rhea.sidana.customer.exception.FieldIsEmptyOrBlankException;
import org.sunbasedata.rhea.sidana.customer.exception.InvalidCommandException;
import org.sunbasedata.rhea.sidana.customer.repository.model.Customer;
import org.sunbasedata.rhea.sidana.customer.service.CustomerService;
import org.sunbasedata.rhea.sidana.customer.view.model.request.CreateRequest;
import org.sunbasedata.rhea.sidana.customer.view.model.response.CreateResponse;
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
            @Valid @RequestBody CreateRequest createCustomer,
            @RequestParam(required = true, name = "cmd") String cmd) {
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
                new CreateResponse("Customer created successfully", createdCustomer)
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
}
