package maelton.casal.vehicle_rental_api.api.v1.exception.handler;

import maelton.casal.vehicle_rental_api.api.v1.exception.CarUUIDNotFoundException;
import maelton.casal.vehicle_rental_api.api.v1.exception.ChassisNumberAlreadyExistsException;
import maelton.casal.vehicle_rental_api.api.v1.exception.MotorcycleUUIDNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class VehicleExceptionHandler {

    //CAR
    @ExceptionHandler(CarUUIDNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleCarUUIDNotFoundException(CarUUIDNotFoundException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    //MOTORCYCLE
    @ExceptionHandler(MotorcycleUUIDNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleMotorcycleUUIDNotFoundException(MotorcycleUUIDNotFoundException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    //VEHICLE
    @ExceptionHandler(ChassisNumberAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleChassisNumberAlreadyExistsException(ChassisNumberAlreadyExistsException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.CONFLICT);
    }
}
