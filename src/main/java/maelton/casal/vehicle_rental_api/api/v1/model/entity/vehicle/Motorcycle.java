package maelton.casal.vehicle_rental_api.api.v1.model.entity.vehicle;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tab_motorcycle")
@Entity
public class Motorcycle extends Vehicle {
    @Getter
    @Setter
    @Column(name = "tank_capacity")
    private int tankCapacity;

    public Motorcycle(String model, String chassis, int tankCapacity) {
        this.model = model;
        this.chassis = chassis;
        this.tankCapacity = tankCapacity;
    }
}
