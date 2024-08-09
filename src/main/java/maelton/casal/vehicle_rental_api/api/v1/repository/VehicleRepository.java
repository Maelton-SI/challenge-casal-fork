package maelton.casal.vehicle_rental_api.api.v1.repository;

import maelton.casal.vehicle_rental_api.api.v1.model.entity.vehicle.Vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {
    List<Vehicle> findAll();
    Optional<Vehicle> findById(UUID id);
    Optional<Vehicle> findVehicleByChassis(String chassis);
    void deleteById(UUID id);

    Boolean existsVehicleById(UUID id);
    Boolean existsVehicleByChassis(String chassis);
}
