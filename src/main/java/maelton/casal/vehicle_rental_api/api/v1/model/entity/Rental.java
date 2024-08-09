package maelton.casal.vehicle_rental_api.api.v1.model.entity;

import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;
import maelton.casal.vehicle_rental_api.api.v1.model.entity.vehicle.Vehicle;

import java.time.LocalDate;
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

    @Setter
    @ManyToOne
    @JoinColumn(name="vehicle_id")
    private Vehicle vehicle;

    @Setter
    LocalDate rentalStartDate;

    @Setter
    LocalDate rentalEndDate;

    public Rental(User customer, Vehicle vehicle, LocalDate rentalStartDate, LocalDate rentalEndDate) {
        this.customer = customer;
        this.vehicle = vehicle;

        this.rentalStartDate = rentalStartDate;
        this.rentalEndDate = rentalEndDate;
    }
}
