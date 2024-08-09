package maelton.casal.vehicle_rental_api.api.v1.initializer;

import maelton.casal.vehicle_rental_api.api.v1.exception.vehicle.ChassisNumberAlreadyExistsException;
import maelton.casal.vehicle_rental_api.api.v1.model.dto.car.CarRequestDTO;
import maelton.casal.vehicle_rental_api.api.v1.model.dto.motorcycle.MotorcycleRequestDTO;
import maelton.casal.vehicle_rental_api.api.v1.model.dto.user.UserRequestDTO;
import maelton.casal.vehicle_rental_api.api.v1.model.enums.Role;
import maelton.casal.vehicle_rental_api.api.v1.exception.user.UserEmailAlreadyExistsException;
import maelton.casal.vehicle_rental_api.api.v1.service.CarService;
import maelton.casal.vehicle_rental_api.api.v1.service.MotorcycleService;
import maelton.casal.vehicle_rental_api.api.v1.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer {
    @Autowired
    UserService userService;

    @Autowired
    CarService carService;

    @Autowired
    MotorcycleService motorcycleService;

    @EventListener(ApplicationReadyEvent.class)
    public void initializeDatabase() {
        createDefaultUser("Super Admin", "superadmin@api.com", "superadmin", Role.SUPER_ADMIN);
        createDefaultUser("Admin", "admin@api.com", "admin", Role.ADMIN);
        createDefaultUser("User", "user@api.com", "user", Role.USER);

        createDefaultCar("Car Example Model", "1", 4);
        createDefaultMotorcycle("Motorcycle Example Model", "2", 16);
    }

    public void createDefaultUser(String name, String email, String password, Role role) {
        try {
            userService.createUser(new UserRequestDTO(name, email, password, role));
        } catch (UserEmailAlreadyExistsException e) {
            return;
        }
    }

    public void createDefaultCar(String model, String chassis, int numberOfDoors) {
        try {
            carService.createCar(new CarRequestDTO(model, chassis, numberOfDoors));
        } catch (ChassisNumberAlreadyExistsException e) {
            return;
        }
    }

    public void createDefaultMotorcycle(String model, String chassis, int tankCapacity) {
        try {
            motorcycleService.createMotorcycle(new MotorcycleRequestDTO(model, chassis, tankCapacity));
        } catch (ChassisNumberAlreadyExistsException e) {
            return;
        }
    }
}
