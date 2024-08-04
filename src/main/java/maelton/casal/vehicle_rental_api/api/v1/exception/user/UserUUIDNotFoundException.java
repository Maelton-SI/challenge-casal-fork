package maelton.casal.vehicle_rental_api.api.v1.exception.user;

public class UserUUIDNotFoundException extends RuntimeException {
    public UserUUIDNotFoundException(String id) {
        super("User UUID '" + id + "' not found");
    }
}
