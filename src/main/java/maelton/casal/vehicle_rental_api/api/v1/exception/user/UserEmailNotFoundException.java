package maelton.casal.vehicle_rental_api.api.v1.exception.user;

public class UserEmailNotFoundException extends RuntimeException {
    public UserEmailNotFoundException(String email) {
        super("User with email " + email + " was not found");
    }
}
