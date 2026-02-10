package  ups.edu.ec.proyect_backend.core.exceptions.domain;

import org.springframework.http.HttpStatus;

import ups.edu.ec.proyect_backend.core.exceptions.base.ApplicationException;

public class NotFoundException extends ApplicationException {

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}