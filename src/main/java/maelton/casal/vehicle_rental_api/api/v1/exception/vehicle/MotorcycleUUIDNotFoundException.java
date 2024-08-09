package maelton.casal.vehicle_rental_api.api.v1.exception.vehicle;

import java.util.UUID;

public class MotorcycleUUIDNotFoundException extends RuntimeException {
    public MotorcycleUUIDNotFoundException(UUID id) {
        super("Motorcycle UUID '" + id + "' not found");
    }
}
