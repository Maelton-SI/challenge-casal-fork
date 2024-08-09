package maelton.casal.vehicle_rental_api.api.v1.config.security.jwt;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.ZonedDateTime;
import java.util.List;

@Schema(description = "DTO representing a JSON Web Token (JWT) response after successful authentication.")
public record JSONWebTokenDTO(
        String accessToken,
        String tokenType,
        String expiresAt,
        String userEmail,
        List<String> userRoles
) {}
