package org.sunbasedata.rhea.sidana.customer.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sunbasedata.rhea.sidana.authentication.exception.InvalidAccessException;
import org.sunbasedata.rhea.sidana.customer.repository.model.Customer;
import org.sunbasedata.rhea.sidana.customer.service.CustomerService;
import org.sunbasedata.rhea.sidana.customer.view.model.response.Error;

import javax.validation.Valid;

@RestController
@RequestMapping("/sunbase/portal/api")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/assignment.jsp")
    public ResponseEntity<Object> createNewCustomer(
            @RequestHeader("Authorization") String authorizationHeader,
            @Valid @RequestBody Customer customer,
            @RequestParam(required = true, name = "cmd") String cmd) {
        Customer createdCustomer;
        try {
            createdCustomer = customerService.createNewCustomer(authorizationHeader, customer, cmd);
        } catch (InvalidAccessException ex) {
            Error error = new Error("Failure", ex.getMessage());

            return ResponseEntity.status(
                    HttpStatus.BAD_REQUEST
            ).body(
                    error
            );
        } catch (Exception ex) {
            Error error = new Error("failure, error occurred", ex.getMessage());

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
//                "Customer created successfully" +
                (createdCustomer != null ? createdCustomer.toString() : "null")
        );
    }
}
