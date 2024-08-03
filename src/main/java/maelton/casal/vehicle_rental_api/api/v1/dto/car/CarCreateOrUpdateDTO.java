package maelton.casal.vehicle_rental_api.api.v1.dto.car;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for creating or updating cars")
public record CarCreateOrUpdateDTO(String name, String chassis, byte numberOfDoors) {
}
