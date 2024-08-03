package maelton.casal.vehicle_rental_api.api.v1.exception.handler;

import maelton.casal.vehicle_rental_api.api.v1.exception.CarUUIDNotFoundException;
import maelton.casal.vehicle_rental_api.api.v1.exception.ChassisNumberAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class VehicleRentalAPIExceptionHandler {

    @ExceptionHandler(CarUUIDNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleCarUUIDNotFoundException(CarUUIDNotFoundException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ChassisNumberAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleChassisNumberAlreadyExistsException(ChassisNumberAlreadyExistsException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.CONFLICT);
    }
}
