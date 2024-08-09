package maelton.casal.vehicle_rental_api.api.v1.exception.handler;

import maelton.casal.vehicle_rental_api.api.v1.exception.vehicle.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class VehicleExceptionHandler {

    //VEHICLE
    @ExceptionHandler(VehicleUUIDNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleVehicleUUIDNotFoundException(VehicleUUIDNotFoundException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

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

    //CHASSIS ALREADY EXISTS
    @ExceptionHandler(ChassisNumberAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleChassisNumberAlreadyExistsException(ChassisNumberAlreadyExistsException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.CONFLICT);
    }

    //CHASSIS NOT FOUND
    @ExceptionHandler(VehicleChassisNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleVehicleChassisNotFoundException(VehicleChassisNotFoundException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    //NAME OR CHASSIS
    @ExceptionHandler(IncompleteVehicleDetailsException.class)
    public ResponseEntity<ExceptionResponse> handleIncompleteVehicleDetailsException(IncompleteVehicleDetailsException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
