package maelton.casal.vehicle_rental_api.api.v1.model.dto.rental;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.UUID;

@Schema(description = "DTO for creating or updating rental registries")
public record VehicleRentalRequestDTO(
        UUID userId,
        UUID vehicleId,
        LocalDate rentalStartDate,
        LocalDate rentalEndDate
) {}
