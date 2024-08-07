package maelton.casal.vehicle_rental_api.api.v1.model.entity.vehicle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import maelton.casal.vehicle_rental_api.api.v1.model.entity.Rental;

import java.util.HashSet;
import java.util.Set;
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

    @Setter
    @JsonIgnore
    @JsonProperty()
    @OneToMany(mappedBy = "vehicle", fetch=FetchType.LAZY)
    private Set<Rental> rentals = new HashSet<>();

    @Setter
    @Column(name = "num_chassis", unique = true)
    protected String chassis;
}
