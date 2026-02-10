package  ups.edu.ec.proyect_backend.core.exceptions.domain;

import org.springframework.http.HttpStatus;

import ups.edu.ec.proyect_backend.core.exceptions.base.ApplicationException;

public class ConflictException extends ApplicationException {

    public ConflictException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}