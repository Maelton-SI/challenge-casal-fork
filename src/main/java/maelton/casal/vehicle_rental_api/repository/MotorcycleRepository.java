package maelton.casal.vehicle_rental_api.repository;

import maelton.casal.vehicle_rental_api.entity.vehicle.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MotorcycleRepository extends JpaRepository<Motorcycle, UUID> {
}
