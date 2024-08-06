package maelton.casal.vehicle_rental_api.api.v1.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import maelton.casal.vehicle_rental_api.api.v1.enums.UserRole;

import java.util.UUID;

@Schema(description = "DTO used to represent users data in responses")
public record UserResponseDTO(UUID id, String name, String email, UserRole role) {
}
