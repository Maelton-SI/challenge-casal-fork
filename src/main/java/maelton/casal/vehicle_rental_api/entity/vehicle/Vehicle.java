package maelton.casal.vehicle_rental_api.entity.vehicle;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Inheritance;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public abstract class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
    String name;
}
