package maelton.casal.vehicle_rental_api.api.v1.model.dto.vehicle.motorcycle;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for creating or updating motorcycles")
public record MotorcycleRequestDTO(String model, String chassis, int tankCapacity) {
}
