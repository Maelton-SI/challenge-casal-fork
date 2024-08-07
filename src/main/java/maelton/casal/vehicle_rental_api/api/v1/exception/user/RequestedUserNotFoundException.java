package maelton.casal.vehicle_rental_api.api.v1.exception.user;

import java.util.UUID;

public class RequestedUserNotFoundException extends RuntimeException {
    public RequestedUserNotFoundException(UUID id, String email) {
        super("Requested user not found: \n id: " + id + "\n email: " + email);
    }
}
