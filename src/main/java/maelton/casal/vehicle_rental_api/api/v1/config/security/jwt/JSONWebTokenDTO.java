package maelton.casal.vehicle_rental_api.api.v1.config.security.jwt;

import io.swagger.v3.oas.annotations.media.Schema;

import maelton.casal.vehicle_rental_api.api.v1.model.dto.user.UserResponseDTO;

import java.time.LocalDateTime;

@Schema(description = "DTO representing a JSON Web Token (JWT) response after successful authentication.")
public record JSONWebTokenDTO(
        String accessToken,
        String tokenType,
        LocalDateTime expiresAt,
        UserResponseDTO user
) {}
