package org.demo.springsecurity.exceptions;

import org.demo.springsecurity.model.ServiceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<ServiceResponse> handleRegistrationException(RegistrationException ex) {
        return ResponseEntity.status(409).body(new ServiceResponse(false, null, Map.of("error",
                "Registration Error", "message", ex.getMessage())));
    }

    @ExceptionHandler(SpringSecurityException.class)
    public ResponseEntity<ServiceResponse> handleSpringSecurityException(SpringSecurityException ex) {
        return ResponseEntity.status(409).body(new ServiceResponse(false, null, Map.of("error",
                "Unexpected Error", "message", ex.getMessage())));
    }

    @ExceptionHandler(CustomDBException.class)
    public ResponseEntity<ServiceResponse> handleCustomDBException(CustomDBException ex) {
        return ResponseEntity.status(409).body(new ServiceResponse(false, null, Map.of("error",
                "Unexpected Error in Database", "message", ex.getMessage())));
    }

    @ExceptionHandler(DataManipulationException.class)
    public ResponseEntity<ServiceResponse> handleDataManipulationException(DataManipulationException ex) {
        return ResponseEntity.status(409).body(new ServiceResponse(false, null, Map.of("error",
                "Unexpected Error", "message", "Data is updated by another user. Please refresh and try again")));
    }

    @ExceptionHandler(UserAuthenticationException.class)
    public ResponseEntity<ServiceResponse> handleAuthorizationException(UserAuthenticationException ex) {
        return ResponseEntity.status(401).body(new ServiceResponse(false, null, Map.of("error",
                "Unexpected Error", "message", ex.getMessage())));
    }
}
