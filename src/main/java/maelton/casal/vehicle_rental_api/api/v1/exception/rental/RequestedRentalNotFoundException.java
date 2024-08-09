package maelton.casal.vehicle_rental_api.api.v1.exception.rental;

public class RequestedRentalNotFoundException extends RuntimeException {
    public RequestedRentalNotFoundException(String message) {
        super(message);
    }
}
