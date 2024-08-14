package maelton.casal.vehicle_rental_api.api.v1.service;

import jakarta.transaction.Transactional;

import maelton.casal.vehicle_rental_api.api.v1.exception.rental.RentalException;
import maelton.casal.vehicle_rental_api.api.v1.exception.user.UserUUIDNotFoundException;
import maelton.casal.vehicle_rental_api.api.v1.exception.rental.RentalUUIDNotFoundException;
import maelton.casal.vehicle_rental_api.api.v1.exception.vehicle.CarUUIDNotFoundException;
import maelton.casal.vehicle_rental_api.api.v1.exception.vehicle.MotorcycleUUIDNotFoundException;
import maelton.casal.vehicle_rental_api.api.v1.exception.vehicle.VehicleUUIDNotFoundException;
import maelton.casal.vehicle_rental_api.api.v1.model.dto.rental.CarRentalRequestDTO;
import maelton.casal.vehicle_rental_api.api.v1.model.dto.rental.MotorcycleRentalRequestDTO;
import maelton.casal.vehicle_rental_api.api.v1.model.dto.rental.VehicleRentalRequestDTO;
import maelton.casal.vehicle_rental_api.api.v1.model.dto.rental.VehicleRentalResponseDTO;
import maelton.casal.vehicle_rental_api.api.v1.model.entity.Rental;
import maelton.casal.vehicle_rental_api.api.v1.model.entity.User;
import maelton.casal.vehicle_rental_api.api.v1.model.entity.vehicle.Car;
import maelton.casal.vehicle_rental_api.api.v1.model.entity.vehicle.Motorcycle;
import maelton.casal.vehicle_rental_api.api.v1.model.entity.vehicle.Vehicle;
import maelton.casal.vehicle_rental_api.api.v1.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private MotorcycleRepository motorcycleRepository;
    
    //CREATE
    @Transactional
    public VehicleRentalResponseDTO createRental(VehicleRentalRequestDTO rentalCreateDTO) {
        Rental rental = createRentalFromVehicleRentalRequestDTO(rentalCreateDTO);
            rentalRepository.save(rental);
        return rentalToVehicleRentalResponseDTO(rental);
    }

    //CREATE CAR RENTAL
    @Transactional
    public VehicleRentalResponseDTO createCarRental(CarRentalRequestDTO carRentalCreateDTO) {
        //TODO: Catch and treat possible exception
        String customerEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        User rentalCustomer = userRepository.findUserByEmail(customerEmail).orElseThrow(
                () -> new RentalException("Could not rent car (UUID: '"
                                        + carRentalCreateDTO.carId() + "')"
                                        + " to user (email address: '"
                                        + customerEmail + "')"
                )
        );

        Car rentalCar = carRepository.findById(carRentalCreateDTO.carId()).orElseThrow(
                () -> new CarUUIDNotFoundException(carRentalCreateDTO.carId())
        );

        Rental rental = new Rental(
                rentalCustomer,
                rentalCar,
                carRentalCreateDTO.rentalStartDate(),
                carRentalCreateDTO.rentalEndDate()
        );

        rentalRepository.save(rental);
        return rentalToVehicleRentalResponseDTO(rental);
    }

    //CREATE MOTORCYCLE RENTAL
    @Transactional
    public VehicleRentalResponseDTO createMotorcycleRental(MotorcycleRentalRequestDTO motorcycleRentalCreateDTO) {
        //TODO: Catch and treat possible exception
        String customerEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        User rentalCustomer = userRepository.findUserByEmail(customerEmail).orElseThrow(
                () -> new RentalException("Could not rent motorcycle (UUID: '"
                                        + motorcycleRentalCreateDTO.motorcycleId() + "')"
                                        + " to user (email address: '"
                                        + customerEmail + "')"
                )
        );

        Motorcycle rentalMotorcycle = motorcycleRepository.findById(motorcycleRentalCreateDTO.motorcycleId()).orElseThrow(
                () -> new MotorcycleUUIDNotFoundException(motorcycleRentalCreateDTO.motorcycleId())
        );

        Rental rental = new Rental(
                rentalCustomer,
                rentalMotorcycle,
                motorcycleRentalCreateDTO.rentalStartDate(),
                motorcycleRentalCreateDTO.rentalEndDate()
        );

        rentalRepository.save(rental);
        return rentalToVehicleRentalResponseDTO(rental);
    }

    //READ (ALL)
    @Transactional
    public List<VehicleRentalResponseDTO> getAllRentals(String customerEmail, String vehicleChassis) {
        if(
            (customerEmail == null || customerEmail.isEmpty()) &&
            (vehicleChassis == null || vehicleChassis.isEmpty())
        ) {
            return rentalRepository.findAll()
                    .stream()
                    .map(this::rentalToVehicleRentalResponseDTO)
                    .toList();
        }

        if(
            (customerEmail != null && !customerEmail.isEmpty()) &&
            (vehicleChassis != null && !vehicleChassis.isEmpty())
        ) {
            List<VehicleRentalResponseDTO> rentals = rentalRepository.findRentalByCustomerEmail(customerEmail)
                                    .stream()
                                    .filter(rental -> rental.getVehicle().getChassis().equals(vehicleChassis))
                                    .map(this::rentalToVehicleRentalResponseDTO)
                                    .toList();
            return rentals;
        }

        if(customerEmail != null) {
            List<VehicleRentalResponseDTO> rentals = rentalRepository.findRentalByCustomerEmail(customerEmail)
                    .stream()
                    .map(this::rentalToVehicleRentalResponseDTO)
                    .toList();
            return rentals;
        }

        return rentalRepository.findRentalByVehicleChassis(vehicleChassis)
                .stream()
                .map(this::rentalToVehicleRentalResponseDTO)
                .toList();
    }

    //READ (BY ID)
    @Transactional
    public VehicleRentalResponseDTO getRentalById(UUID id) {
        Rental rental = rentalRepository.findById(id).orElseThrow(
                () -> new RentalUUIDNotFoundException(id)
        );

        return rentalToVehicleRentalResponseDTO(rental);
    }

    //UPDATE
    @Transactional
    public VehicleRentalResponseDTO updateRental(UUID id, VehicleRentalRequestDTO rentalUpdateDTO) {
        Rental rental = rentalRepository.findById(id).orElseThrow(
                () -> new RentalUUIDNotFoundException(id)
        );

        updateRental(rentalUpdateDTO, rental);
            rentalRepository.save(rental);
        return rentalToVehicleRentalResponseDTO(rental);
    }

    //DELETE
    @Transactional
    public void deleteRental(UUID id) {
        if(rentalRepository.existsRentalById(id))
            rentalRepository.deleteById(id);
        else
            throw new RentalUUIDNotFoundException(id);
    }



    private VehicleRentalResponseDTO rentalToVehicleRentalResponseDTO(Rental rental) {
        return new VehicleRentalResponseDTO(
                rental.getId(),
                rental.getCustomer().getId(),
                rental.getCustomer().getEmail(),
                rental.getCustomer().getName(),
                rental.getVehicle().getId(),
                rental.getVehicle().getChassis(),
                rental.getVehicle().getModel(),
                rental.getRentalStartDate(),
                rental.getRentalEndDate()
        );
    }

    private void updateRental(VehicleRentalRequestDTO rentalUpdateDTO, Rental rental) {
        User updatedRentalCustomer = userRepository.findById(rentalUpdateDTO.userId()).orElseThrow(
                () -> new UserUUIDNotFoundException(rentalUpdateDTO.userId())
        );

        Vehicle updatedRentalVehicle = vehicleRepository.findById(rentalUpdateDTO.vehicleId()).orElseThrow(
                () -> new VehicleUUIDNotFoundException(rentalUpdateDTO.vehicleId())
        );

        rental.setCustomer(updatedRentalCustomer);
        rental.setVehicle(updatedRentalVehicle);
        rental.setRentalStartDate(rentalUpdateDTO.rentalStartDate());
        rental.setRentalEndDate(rentalUpdateDTO.rentalEndDate());
    }

    private Rental createRentalFromVehicleRentalRequestDTO(VehicleRentalRequestDTO rentalCreateDTO) {
        User rentalCustomer = userRepository.findById(rentalCreateDTO.userId()).orElseThrow(
                () -> new UserUUIDNotFoundException(rentalCreateDTO.userId())
        );

        Vehicle rentalVehicle = vehicleRepository.findById(rentalCreateDTO.vehicleId()).orElseThrow(
                () -> new VehicleUUIDNotFoundException(rentalCreateDTO.vehicleId())
        );

        return new Rental(
                rentalCustomer,
                rentalVehicle,
                rentalCreateDTO.rentalStartDate(),
                rentalCreateDTO.rentalEndDate()
        );
    }
}
