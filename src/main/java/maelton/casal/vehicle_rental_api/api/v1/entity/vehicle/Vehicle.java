package maelton.casal.vehicle_rental_api.api.v1.entity.vehicle;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "tab_vehicle")
@Entity
public abstract class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    @Setter
    protected String name;

    //TODO: ALTER TABLE tab_vehicle ADD CONSTRAINT tab_vehicle_nn_chassis CHECK (chassis IS NOT NULL)
    @Setter
    @Column(name = "num_chassis", unique = true)
    protected String chassis;
}
