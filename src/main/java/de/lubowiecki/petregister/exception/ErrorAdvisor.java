package de.lubowiecki.petregister.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorAdvisor {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(OwnerAlreadyExistsException.class)
    public Map<String, String> handleOwnerExistsException() {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "User existiert bereits");
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PetNotFoundException.class)
    public Map<String, String> handlePetNotFoundException() {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "Kein passendes Tier gefunden");
        return errors;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(OwnerNotFoundException.class)
    public Map<String, String> handleOwnerNotFoundException() {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", "Benutzer nicht gefunden");
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String msg = error.getDefaultMessage();
            errors.put(field, msg);
        });

        return errors;
    }
}
