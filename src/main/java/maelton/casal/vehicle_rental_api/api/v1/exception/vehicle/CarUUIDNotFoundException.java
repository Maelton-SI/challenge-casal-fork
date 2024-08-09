package maelton.casal.vehicle_rental_api.api.v1.exception.vehicle;

import java.util.UUID;

public class CarUUIDNotFoundException extends RuntimeException {
    public CarUUIDNotFoundException(UUID id) {
        super("Car UUID '" + id + "' not found");
    }
}
