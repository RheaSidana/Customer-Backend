package org.sunbasedata.rhea.sidana.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sunbasedata.rhea.sidana.authentication.exception.InvalidAccessException;
import org.sunbasedata.rhea.sidana.authentication.service.AuthenticationService;
import org.sunbasedata.rhea.sidana.customer.repository.CustomerRepository;
import org.sunbasedata.rhea.sidana.customer.repository.model.Customer;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AuthenticationService authenticationService;

    public Customer createNewCustomer(
            String authorizationHeader,
            Customer customer,
            String cmd
    ) throws InvalidAccessException {
        authenticationService.validateAuthorizationToken(authorizationHeader);
        return null;
    }
}
