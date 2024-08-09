package maelton.casal.vehicle_rental_api.api.v1.model.dto.rental;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.UUID;

@Schema(description = "This DTO allows a JWT authenticated user to rent a motorcycle")
public record MotorcycleRentalRequestDTO(
        UUID motorcycleId,
        LocalDate rentalStartDate,
        LocalDate rentalEndDate
) {}
