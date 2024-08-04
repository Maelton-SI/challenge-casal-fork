package maelton.casal.vehicle_rental_api.api.v1.exception.user;

public class UserEmailAlreadyExistsException extends RuntimeException {
    public UserEmailAlreadyExistsException(String email) {
        super("User email '" + email + "' already exists.");
    }
}
