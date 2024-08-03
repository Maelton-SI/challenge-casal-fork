package maelton.casal.vehicle_rental_api.api.v1.entity.vehicle;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import maelton.casal.vehicle_rental_api.api.v1.enums.WheelType;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Motorcycle extends Vehicle {
    @Getter
    @Setter
    private WheelType wheels;

    public Motorcycle(String name, String chassis, WheelType wheels) {
        this.name = name;
        this.chassis = chassis;
        this.wheels = wheels;
    }
}
