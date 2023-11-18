package org.sunbasedata.rhea.sidana.authentication.service;

import org.springframework.stereotype.Service;
import org.sunbasedata.rhea.sidana.authentication.exception.InvalidAccessException;

@Service
public class AuthenticationService {
    public void validateAuthorizationToken(String authorizationHeader) throws InvalidAccessException {
        if (!isValidAuthorizationToken(authorizationHeader)) {
            throw new InvalidAccessException("Invalid Access");
        }
        System.out.println("Bearer token: " + authorizationHeader.substring("Bearer ".length()));
    }

    private boolean isValidAuthorizationToken(String authorizationHeader) {
        return (authorizationHeader != null && authorizationHeader.startsWith("Bearer "));
    }
}
