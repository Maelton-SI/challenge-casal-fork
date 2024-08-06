package maelton.casal.vehicle_rental_api.api.v1.initializer;

import maelton.casal.vehicle_rental_api.api.v1.dto.user.UserRequestDTO;
import maelton.casal.vehicle_rental_api.api.v1.enums.Role;
import maelton.casal.vehicle_rental_api.api.v1.exception.user.UserEmailAlreadyExistsException;
import maelton.casal.vehicle_rental_api.api.v1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @EventListener(ApplicationReadyEvent.class)
    public void createDefaultAdminUser() {
        try {
            userService.createUser(
                    new UserRequestDTO(
                            "admin",
                            "admin@admin.com",
                            "admin",
                            Role.SUPER_ADMIN
                    )
            );
        } catch (UserEmailAlreadyExistsException e) {
            return;
        }
    }
}
