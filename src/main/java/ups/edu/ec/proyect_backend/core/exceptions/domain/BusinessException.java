package  ups.edu.ec.proyect_backend.core.exceptions.domain;

import org.springframework.http.HttpStatus;

import ups.edu.ec.proyect_backend.core.exceptions.base.ApplicationException;

public class BusinessException extends ApplicationException {

    public BusinessException(String message) {
        super(HttpStatus.UNPROCESSABLE_CONTENT, message);
    }

    protected BusinessException(HttpStatus status, String message) {
        super(status, message);
    }
}