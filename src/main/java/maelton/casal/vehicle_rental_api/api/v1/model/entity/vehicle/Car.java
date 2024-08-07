package maelton.casal.vehicle_rental_api.api.v1.model.entity.vehicle;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tab_car")
@Entity
public class Car extends Vehicle {
    @Getter
    @Setter
    @Column(name = "num_doors")
    private int numberOfDoors;

    public Car(String name, String chassis, int numberOfDoors) {
        this.name = name;
        this.chassis = chassis;
        this.numberOfDoors = numberOfDoors;
    }
}
