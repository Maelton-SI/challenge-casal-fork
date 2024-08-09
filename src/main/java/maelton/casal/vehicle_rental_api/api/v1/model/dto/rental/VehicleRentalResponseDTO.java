package maelton.casal.vehicle_rental_api.api.v1.model.dto.rental;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.UUID;

@Schema(description = "DTO used to represent rental registries in responses")
public record VehicleRentalResponseDTO(
        UUID rentalId,
        UUID userId,
        String userEmail,
        String userName,
        UUID vehicleId,
        String vehicleChassis,
        String vehicleModel,
        LocalDate rentalStartDate,
        LocalDate rentalEndDate
) {}
