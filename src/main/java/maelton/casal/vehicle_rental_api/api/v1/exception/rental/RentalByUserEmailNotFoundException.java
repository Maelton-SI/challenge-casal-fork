package maelton.casal.vehicle_rental_api.api.v1.exception.rental;

public class RentalByUserEmailNotFoundException extends RuntimeException {
    public RentalByUserEmailNotFoundException(String email) {
        super("No vehicle rental by user with email address '" + email + "' found");
    }
}
