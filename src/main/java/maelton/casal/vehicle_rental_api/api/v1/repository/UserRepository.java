package maelton.casal.vehicle_rental_api.api.v1.repository;

import maelton.casal.vehicle_rental_api.api.v1.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    //Required by Spring Security
    UserDetails findByEmail(String email);

    User save(User user);
    List<User> findAll();
    Optional<User> findById(UUID id);
    Optional<User> findUserByEmail(String email);
    void deleteById(UUID id);

    Boolean existsUserById(UUID id);
    Boolean existsUserByEmail(String email);
}
