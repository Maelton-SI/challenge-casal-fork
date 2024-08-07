package maelton.casal.vehicle_rental_api.api.v1.exception.handler;

import maelton.casal.vehicle_rental_api.api.v1.exception.user.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {

    //EMAIL ALREADY EXISTS
    @ExceptionHandler(UserEmailAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleUserEmailAlreadyExistsException(UserEmailAlreadyExistsException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.CONFLICT);
    }

    //EMAIL NOT FOUND
    @ExceptionHandler(UserEmailNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUserEmailNotFoundException(UserEmailNotFoundException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    //REQUEST QUERY PARAM
    @ExceptionHandler(RequestedUserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleRequestedUserNotFoundException(RequestedUserNotFoundException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    //UUID
    @ExceptionHandler(UserUUIDNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUserUUIDNotFoundException(UserUUIDNotFoundException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    //EMAIL OR PASSWORD
    @ExceptionHandler(IncompleteUserDetailsException.class)
    public ResponseEntity<ExceptionResponse> handleIncompleteUserDetailsException(IncompleteUserDetailsException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
