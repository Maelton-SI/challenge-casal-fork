package maelton.casal.vehicle_rental_api.api.v1.model.dto.motorcycle;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for creating or updating motorcycles")
public record MotorcycleRequestDTO(String name, String chassis, int tankCapacity) {
}
