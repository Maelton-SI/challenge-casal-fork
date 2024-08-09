package maelton.casal.vehicle_rental_api.api.v1.exception.handler;

import maelton.casal.vehicle_rental_api.api.v1.exception.rental.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RentalExceptionHandler {

    //UUID
    @ExceptionHandler(RentalUUIDNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleRentalUUIDNotFoundException(RentalUUIDNotFoundException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    //RENTAL BY EMAIL
    @ExceptionHandler(RentalByUserEmailNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleRentalByUserEmailNotFoundException(RentalByUserEmailNotFoundException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }


    //RENTAL BY CHASSIS
    @ExceptionHandler(RentalByVehicleChassisNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleRentalByVehicleChassisNotFoundException(RentalByVehicleChassisNotFoundException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    //RENTAL NOT FOUND
    @ExceptionHandler(RequestedRentalNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleRequestedRentalNotFoundException(RequestedRentalNotFoundException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }
}
