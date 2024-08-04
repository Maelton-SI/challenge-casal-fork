package maelton.casal.vehicle_rental_api.api.v1.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for logging in users")
public record UserLoginDTO(String email, String password) {
}
