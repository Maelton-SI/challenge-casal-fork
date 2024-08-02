package maelton.casal.vehicle_rental_api.repository;

import maelton.casal.vehicle_rental_api.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
