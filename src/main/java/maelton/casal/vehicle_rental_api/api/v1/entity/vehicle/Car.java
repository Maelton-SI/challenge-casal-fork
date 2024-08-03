package maelton.casal.vehicle_rental_api.api.v1.entity.vehicle;

import jakarta.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Car extends Vehicle {
    @Getter
    @Setter
    private byte numberOfDoors;

    public Car(String name, String chassis, byte numberOfDoors) {
        this.name = name;
        this.chassis = chassis;
        this.numberOfDoors = numberOfDoors;
    }
}
