package maelton.casal.vehicle_rental_api.api.v1.exception.vehicle;

import java.util.UUID;

public class VehicleUUIDNotFoundException extends RuntimeException {
    public VehicleUUIDNotFoundException(UUID id) {
        super("Vehicle UUID '" + id + "' not found");
    }
}
