package maelton.casal.vehicle_rental_api.api.v1.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;
import maelton.casal.vehicle_rental_api.api.v1.entity.user.UserRole;

@Schema(description = "DTO for creating or updating users")
public record UserRequestDTO(String name, String email, String password, UserRole role) {
}
