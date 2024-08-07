package maelton.casal.vehicle_rental_api.api.v1.exception.vehicle;

public class IncompleteVehicleDetailsException extends RuntimeException {
    public IncompleteVehicleDetailsException(String message) {
        super(message);
    }
}
