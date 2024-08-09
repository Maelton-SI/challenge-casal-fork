package maelton.casal.vehicle_rental_api.api.v1.exception.rental;

public class RentalByVehicleChassisNotFoundException extends RuntimeException {
    public RentalByVehicleChassisNotFoundException(String chassis) {
        super("No vehicle rental registry for vehicle with chassis: " + chassis + "' found");
    }
}
