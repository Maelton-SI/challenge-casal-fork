package maelton.casal.vehicle_rental_api.api.v1.exception;

import java.util.UUID;

public class MotorcycleUUIDNotFoundException extends VehicleUUIDNotFoundException {
    public MotorcycleUUIDNotFoundException(UUID id) {
        super("Motorcycle UUID '" + id + "' not found");
    }
}
