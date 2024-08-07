package maelton.casal.vehicle_rental_api.api.v1.initializer;

import maelton.casal.vehicle_rental_api.api.v1.dto.user.UserRequestDTO;
import maelton.casal.vehicle_rental_api.api.v1.enums.Role;
import maelton.casal.vehicle_rental_api.api.v1.exception.user.UserEmailAlreadyExistsException;
import maelton.casal.vehicle_rental_api.api.v1.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer {
    @Autowired
    UserService userService;

    @EventListener(ApplicationReadyEvent.class)
    public void createDefaultUsers() {
        createDefaultUser("Super Admin", "superadmin@api.com", "superadmin", Role.SUPER_ADMIN);
        createDefaultUser("Admin", "admin@api.com", "admin", Role.ADMIN);
        createDefaultUser("User", "user@api.com", "user", Role.USER);
    }

    public void createDefaultUser(String name, String email, String password, Role role) {
        try {
            userService.createUser(new UserRequestDTO(name, email, password, role));
        } catch (UserEmailAlreadyExistsException e) {
            return;
        }
    }
}
