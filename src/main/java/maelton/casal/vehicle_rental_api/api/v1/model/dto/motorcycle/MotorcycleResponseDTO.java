package maelton.casal.vehicle_rental_api.api.v1.model.dto.motorcycle;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "DTO used to represent motorcycles data in responses")
public record MotorcycleResponseDTO(UUID id, String model, String chassis, int tankCapacity) {
}
