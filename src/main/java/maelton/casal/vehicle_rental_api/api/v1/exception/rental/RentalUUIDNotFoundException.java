package maelton.casal.vehicle_rental_api.api.v1.exception.rental;

import java.util.UUID;

public class RentalUUIDNotFoundException extends RuntimeException {
    public RentalUUIDNotFoundException(UUID id) {
        super("Rental UUID '" + id + "' not found");
    }
}
