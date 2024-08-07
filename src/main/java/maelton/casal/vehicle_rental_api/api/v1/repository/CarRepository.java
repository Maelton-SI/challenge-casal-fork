package maelton.casal.vehicle_rental_api.api.v1.repository;

import maelton.casal.vehicle_rental_api.api.v1.entity.vehicle.Car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<Car, UUID> {
    Car save(Car car);
    List<Car> findAll();
    Optional<Car> findById(UUID id);
    Optional<Car> findCarByChassis(String chassis);
    void deleteById(UUID id);

    Boolean existsCarById(UUID id);
    Boolean existsCarByChassis(String chassis);
}
