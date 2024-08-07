package maelton.casal.vehicle_rental_api.api.v1.exception.handler;

import maelton.casal.vehicle_rental_api.api.v1.exception.user.UserEmailAlreadyExistsException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {

    //EMAIL
    @ExceptionHandler(UserEmailAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleUserEmailAlreadyExistsException(UserEmailAlreadyExistsException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.CONFLICT);
    }
}
