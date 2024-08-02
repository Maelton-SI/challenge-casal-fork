package maelton.casal.vehicle_rental_api.repository;

import maelton.casal.vehicle_rental_api.entity.vehicle.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CarRepository extends JpaRepository<Car, UUID> {
}
