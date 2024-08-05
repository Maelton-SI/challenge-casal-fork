package maelton.casal.vehicle_rental_api.api.v1.config;

import maelton.casal.vehicle_rental_api.api.v1.dto.user.UserRequestDTO;
import maelton.casal.vehicle_rental_api.api.v1.entity.user.User;
import maelton.casal.vehicle_rental_api.api.v1.entity.user.UserRole;
import maelton.casal.vehicle_rental_api.api.v1.exception.user.UserEmailAlreadyExistsException;
import maelton.casal.vehicle_rental_api.api.v1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
//    @Autowired
//    PasswordEncoder passwordEncoder;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    UserService userService;

    @EventListener(ApplicationReadyEvent.class)
    public void adminUserInitializer() {

        User admin = new User(
                "admin",
                "admin@vehiclerentalapi.com",
                passwordEncoder.encode("admin"),
                UserRole.SUPER_ADMIN
        );

        UserRequestDTO adminDTO = new UserRequestDTO(
                admin.getName(),
                admin.getEmail(),
                admin.getPassword(),
                admin.getRole()
        );

        try {
            userService.createUser(adminDTO);
        } catch (UserEmailAlreadyExistsException e) {
            return;
        }
    }
}
