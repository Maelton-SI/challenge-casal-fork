package maelton.casal.vehicle_rental_api.api.v1.exception.vehicle;

public class VehicleChassisNotFoundException extends RuntimeException {
    public VehicleChassisNotFoundException(String chassis) {
        super("Chassis number '" + chassis + "' not found");
    }
}
