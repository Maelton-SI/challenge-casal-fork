package maelton.casal.vehicle_rental_api.api.v1.exception;

import java.util.UUID;

public class CarUUIDNotFoundException extends VehicleUUIDNotFoundException {
    public CarUUIDNotFoundException(UUID id) {
        super("Car UUID '" + id + "' not found");
    }
}
