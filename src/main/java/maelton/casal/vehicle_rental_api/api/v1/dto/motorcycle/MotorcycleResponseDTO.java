package maelton.casal.vehicle_rental_api.api.v1.dto.motorcycle;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "DTO used to represent motorcycle data in the response")
public record MotorcycleResponseDTO(UUID id, String name, String chassis, int tankCapacity) {
}
