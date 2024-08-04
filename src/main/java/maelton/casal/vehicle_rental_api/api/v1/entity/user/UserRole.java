package maelton.casal.vehicle_rental_api.api.v1.entity.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRole {
    SUPER_ADMIN("SUPER ADMIN"),
    ADMIN("ADMIN"),
    COSTUMER("COSTUMER");

    private final String role;
}
