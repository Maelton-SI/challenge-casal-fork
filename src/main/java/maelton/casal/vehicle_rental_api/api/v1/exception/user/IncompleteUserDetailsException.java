package maelton.casal.vehicle_rental_api.api.v1.exception.user;

public class IncompleteUserDetailsException extends RuntimeException {
    public IncompleteUserDetailsException(String message) {
        super(message);
    }
}
