package maelton.casal.vehicle_rental_api.api.v1.exception.vehicle;

public class ChassisNumberAlreadyExistsException extends RuntimeException {
    public ChassisNumberAlreadyExistsException(String chassis) {
        super("Chassis number '" + chassis + "' already exists.");
    }
}
