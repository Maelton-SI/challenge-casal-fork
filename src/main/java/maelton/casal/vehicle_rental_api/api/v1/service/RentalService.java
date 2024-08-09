package maelton.casal.vehicle_rental_api.api.v1.service;

import jakarta.transaction.Transactional;

import maelton.casal.vehicle_rental_api.api.v1.exception.user.UserUUIDNotFoundException;
import maelton.casal.vehicle_rental_api.api.v1.exception.rental.RentalUUIDNotFoundException;
import maelton.casal.vehicle_rental_api.api.v1.exception.vehicle.VehicleUUIDNotFoundException;
import maelton.casal.vehicle_rental_api.api.v1.model.dto.rental.RentalRequestDTO;
import maelton.casal.vehicle_rental_api.api.v1.model.dto.rental.RentalResponseDTO;
import maelton.casal.vehicle_rental_api.api.v1.model.entity.Rental;
import maelton.casal.vehicle_rental_api.api.v1.model.entity.User;
import maelton.casal.vehicle_rental_api.api.v1.model.entity.vehicle.Vehicle;
import maelton.casal.vehicle_rental_api.api.v1.repository.RentalRepository;
import maelton.casal.vehicle_rental_api.api.v1.repository.UserRepository;
import maelton.casal.vehicle_rental_api.api.v1.repository.VehicleRepository;

import org.springframework.beans.factory.annotation.Autowired;
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
    
    //CREATE
    @Transactional
    public RentalResponseDTO createRental(RentalRequestDTO rentalCreateDTO) {
        User rentalCustomer = userRepository.findById(rentalCreateDTO.userId()).orElseThrow(
                () -> new UserUUIDNotFoundException(rentalCreateDTO.userId())
        );

        Vehicle rentalVehicle = vehicleRepository.findById(rentalCreateDTO.vehicleId()).orElseThrow(
                () -> new VehicleUUIDNotFoundException(rentalCreateDTO.vehicleId())
        );

        Rental rental = new Rental(
                rentalCustomer,
                rentalVehicle,
                rentalCreateDTO.rentalStartDate(),
                rentalCreateDTO.rentalEndDate()
        );

        rentalRepository.save(rental);
        
        return new RentalResponseDTO(
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

    //READ (ALL)
    @Transactional
    public List<RentalResponseDTO> getAllRentals(String customerEmail, String vehicleChassis) {
        if(
            (customerEmail == null || customerEmail.isEmpty()) &&
            (vehicleChassis == null || vehicleChassis.isEmpty())
        ) {
            return rentalRepository.findAll()
                    .stream()
                    .map(rental -> new RentalResponseDTO(
                            rental.getId(),
                            rental.getCustomer().getId(),
                            rental.getCustomer().getEmail(),
                            rental.getCustomer().getName(),
                            rental.getVehicle().getId(),
                            rental.getVehicle().getChassis(),
                            rental.getVehicle().getModel(),
                            rental.getRentalStartDate(),
                            rental.getRentalEndDate())
                    ).toList();
        }

        if(
            (customerEmail != null && !customerEmail.isEmpty()) &&
            (vehicleChassis != null && !vehicleChassis.isEmpty())
        ) {
            List<RentalResponseDTO> rentals = rentalRepository.findRentalByCustomerEmail(customerEmail)
                                    .stream()
                                    .filter(rental -> rental.getVehicle().getChassis().equals(vehicleChassis))
                                    .map(rental -> new RentalResponseDTO(
                                            rental.getId(),
                                            rental.getCustomer().getId(),
                                            rental.getCustomer().getEmail(),
                                            rental.getCustomer().getName(),
                                            rental.getVehicle().getId(),
                                            rental.getVehicle().getChassis(),
                                            rental.getVehicle().getModel(),
                                            rental.getRentalStartDate(),
                                            rental.getRentalEndDate())
                                    ).toList();
            return rentals;
        }

        if(customerEmail != null) {
            List<RentalResponseDTO> rentals = rentalRepository.findRentalByCustomerEmail(customerEmail)
                    .stream()
                    .map(rental -> new RentalResponseDTO(
                            rental.getId(),
                            rental.getCustomer().getId(),
                            rental.getCustomer().getEmail(),
                            rental.getCustomer().getName(),
                            rental.getVehicle().getId(),
                            rental.getVehicle().getChassis(),
                            rental.getVehicle().getModel(),
                            rental.getRentalStartDate(),
                            rental.getRentalEndDate())
                    ).toList();
            return rentals;
        }

        return rentalRepository.findRentalByVehicleChassis(vehicleChassis)
                .stream()
                .map(rental -> new RentalResponseDTO(
                        rental.getId(),
                        rental.getCustomer().getId(),
                        rental.getCustomer().getEmail(),
                        rental.getCustomer().getName(),
                        rental.getVehicle().getId(),
                        rental.getVehicle().getChassis(),
                        rental.getVehicle().getModel(),
                        rental.getRentalStartDate(),
                        rental.getRentalEndDate())
                ).toList();
    }

    //READ (BY ID)
    @Transactional
    public RentalResponseDTO getRentalById(UUID id) {
        Rental rental = rentalRepository.findById(id).orElseThrow(
                () -> new RentalUUIDNotFoundException(id)
        );

        return new RentalResponseDTO(
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

    //UPDATE
    @Transactional
    public RentalResponseDTO updateRental(UUID id, RentalRequestDTO rentalUpdateDTO) {
        Rental rental = rentalRepository.findById(id).orElseThrow(
                () -> new RentalUUIDNotFoundException(id)
        );

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

        rentalRepository.save(rental);

        return new RentalResponseDTO(
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

    //DELETE
    @Transactional
    public void deleteRental(UUID id) {
        if(rentalRepository.existsRentalById(id))
            rentalRepository.deleteById(id);
        else
            throw new RentalUUIDNotFoundException(id);
    }
}
