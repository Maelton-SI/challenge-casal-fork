package maelton.casal.vehicle_rental_api.api.v1.model.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;
import maelton.casal.vehicle_rental_api.api.v1.model.entity.vehicle.Vehicle;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Table(name="tab_rental")
@Entity
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Setter
    @ManyToOne
    @JoinColumn(name="customer_id")
    private User customer;

    private String customerEmail;

    @Setter
    @ManyToOne
    @JoinColumn(name="vehicle_id")
    private Vehicle vehicle;

    private String vehicleName;

    LocalDateTime rentalStartDate;
    LocalDateTime rentalEndDate;

    public Rental(User customer, Vehicle vehicle, LocalDateTime rentalStartDate, LocalDateTime rentalEndDate) {
        this.customer = customer;
        this.customerEmail = this.customer.getEmail();

        this.vehicleName = vehicle.getName();
        this.vehicle = vehicle;

        this.rentalStartDate = rentalStartDate;
        this.rentalEndDate = rentalEndDate;
    }
}
