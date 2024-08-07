package maelton.casal.vehicle_rental_api.api.v1.exception.user;

import java.util.UUID;

public class UserUUIDNotFoundException extends RuntimeException {
    public UserUUIDNotFoundException(UUID id) {
        super("User UUID '" + id + "' not found");
    }
}
