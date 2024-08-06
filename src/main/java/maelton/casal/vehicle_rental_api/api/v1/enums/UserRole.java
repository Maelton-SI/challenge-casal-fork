package maelton.casal.vehicle_rental_api.api.v1.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRole {
    SUPER_ADMIN("SUPER ADMIN"),
    ADMIN("ADMIN"),
    USER("USER");

    private final String role;
}
