package maelton.casal.vehicle_rental_api.api.v1.common;

import maelton.casal.vehicle_rental_api.api.v1.model.dto.vehicle.car.CarRequestDTO;
import maelton.casal.vehicle_rental_api.api.v1.model.dto.vehicle.car.CarResponseDTO;
import maelton.casal.vehicle_rental_api.api.v1.model.dto.vehicle.motorcycle.MotorcycleRequestDTO;
import maelton.casal.vehicle_rental_api.api.v1.model.dto.vehicle.motorcycle.MotorcycleResponseDTO;
import maelton.casal.vehicle_rental_api.api.v1.model.entity.vehicle.Car;
import maelton.casal.vehicle_rental_api.api.v1.model.entity.vehicle.Motorcycle;
import maelton.casal.vehicle_rental_api.api.v1.model.entity.vehicle.Vehicle;

import java.util.UUID;

public class VehicleConstants {

    public static final Car CAR = new Car("Celta", "CH2000", 2);
    public static final CarRequestDTO CAR_REQUEST_DTO = new CarRequestDTO(CAR.getModel(), CAR.getChassis(), CAR.getNumberOfDoors());
    public static final CarResponseDTO CAR_RESPONSE_DTO = new CarResponseDTO(UUID.fromString("cdfd7ba0-ed09-42b0-b5ea-fb75c2fbcfd2"), CAR.getModel(), CAR.getChassis(), CAR.getNumberOfDoors());

    public static final Motorcycle MOTORCYCLE = new Motorcycle("Honda CG 125", "CH2001", 12);
    public static final MotorcycleRequestDTO MOTORCYCLE_REQUEST_DTO = new MotorcycleRequestDTO(MOTORCYCLE.getModel(), MOTORCYCLE.getChassis(), MOTORCYCLE.getTankCapacity());
    public static final MotorcycleResponseDTO MOTORCYCLE_RESPONSE_DTO = new MotorcycleResponseDTO(UUID.fromString("cdfd7ba0-ed09-42b0-b5ea-fb75c2fbcfd3"), MOTORCYCLE.getModel(), MOTORCYCLE.getChassis(), MOTORCYCLE.getTankCapacity());
}
