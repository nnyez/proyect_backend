package ups.edu.ec.proyect_backend.core.exceptions.domain;

import org.springframework.http.HttpStatus;

import ups.edu.ec.proyect_backend.core.exceptions.base.ApplicationException;


public class BadRequestException extends ApplicationException {

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}