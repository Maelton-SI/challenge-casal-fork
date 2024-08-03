package maelton.casal.vehicle_rental_api.api.v1.repository;

import maelton.casal.vehicle_rental_api.api.v1.entity.vehicle.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MotorcycleRepository extends JpaRepository<Motorcycle, UUID> {
}
