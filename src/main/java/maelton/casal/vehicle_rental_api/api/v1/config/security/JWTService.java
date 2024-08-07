package maelton.casal.vehicle_rental_api.api.v1.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JWTService {
    @Value("${api.security.jwt.secret}")
    private String secret;

    public String generateToken(Authentication authentication) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        List<String> authenticationUserRoles = authentication.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());
        try {
            return JWT.create()
                    .withIssuer("Vehicle Rental API")
                    .withSubject(authentication.getName()) //user email
                    .withClaim("Roles", authenticationUserRoles)
                    .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("JWT authentication failed: ", e);
        }
    }

    public String validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        try {
            return JWT.require(algorithm)
                    .withIssuer("Vehicle Rental API")
                    .build()
                    .verify(token)
                    .getSubject(); //user email
        } catch(JWTVerificationException e) {
            return null;
        }
    }
}
