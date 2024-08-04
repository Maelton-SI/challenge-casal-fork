package maelton.casal.vehicle_rental_api.api.v1.repository;

import maelton.casal.vehicle_rental_api.api.v1.entity.vehicle.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MotorcycleRepository extends JpaRepository<Motorcycle, UUID> {
    Motorcycle save(Motorcycle motorcycle);
    List<Motorcycle> findAll();
    Optional<Motorcycle> findById(UUID id);
    Optional<Motorcycle> findMotorcycleByChassis(String chassis);
    void deleteById(UUID id);

    Boolean existsMotorcycleById(UUID id);
    Boolean existsMotorcycleByChassis(String chassis);
}
