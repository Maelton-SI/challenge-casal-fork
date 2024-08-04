package maelton.casal.vehicle_rental_api.api.v1.exception.vehicle;

public class VehicleUUIDNotFoundException extends RuntimeException {
    public VehicleUUIDNotFoundException(String message) {
        super(message);
    }
}
