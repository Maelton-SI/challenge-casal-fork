package maelton.casal.vehicle_rental_api.api.v1.repository;

import maelton.casal.vehicle_rental_api.api.v1.model.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RentalRepository extends JpaRepository<Rental, UUID> {
    Rental save(Rental rental);
    List<Rental> findAll();
    Optional<Rental> findById(UUID id);

    List<Rental> findRentalByCustomerId(UUID customerId);
    List<Rental> findRentalByCustomerEmail(String customerEmail);
    List<Rental> findRentalByVehicleId(UUID vehicleId);
    List<Rental> findRentalByVehicleChassis(String vehicleChassis);

    void deleteById(UUID id);

    Boolean existsRentalById(UUID id);
    Boolean existsRentalByCustomerEmail(String userEmail);
    Boolean existsRentalByVehicleChassis(String vehicleChassis);
}
