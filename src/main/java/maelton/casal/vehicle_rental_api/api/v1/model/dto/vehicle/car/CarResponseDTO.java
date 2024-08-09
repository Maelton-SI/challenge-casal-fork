package maelton.casal.vehicle_rental_api.api.v1.model.dto.vehicle.car;

import io.swagger.v3.oas.annotations.media.Schema;

import maelton.casal.vehicle_rental_api.api.v1.model.dto.vehicle.VehicleResponseDTO;

import java.util.UUID;

@Schema(description = "DTO used to represent cars data in responses")
public record CarResponseDTO(
        UUID id,
        String model,
        String chassis,
        int numberOfDoors
) implements VehicleResponseDTO {}
