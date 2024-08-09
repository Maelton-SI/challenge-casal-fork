package maelton.casal.vehicle_rental_api.api.v1.model.dto.car;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "DTO used to represent cars data in responses")
public record CarResponseDTO(UUID id, String model, String chassis, int numberOfDoors) {
}
