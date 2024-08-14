package maelton.casal.vehicle_rental_api.api.v1.service;

import jakarta.transaction.Transactional;

import maelton.casal.vehicle_rental_api.api.v1.exception.vehicle.IncompleteVehicleDetailsException;
import maelton.casal.vehicle_rental_api.api.v1.model.dto.vehicle.motorcycle.MotorcycleRequestDTO;
import maelton.casal.vehicle_rental_api.api.v1.model.dto.vehicle.motorcycle.MotorcycleResponseDTO;
import maelton.casal.vehicle_rental_api.api.v1.model.entity.vehicle.Motorcycle;
import maelton.casal.vehicle_rental_api.api.v1.exception.vehicle.MotorcycleUUIDNotFoundException;
import maelton.casal.vehicle_rental_api.api.v1.exception.vehicle.ChassisNumberAlreadyExistsException;
import maelton.casal.vehicle_rental_api.api.v1.repository.MotorcycleRepository;
import maelton.casal.vehicle_rental_api.api.v1.repository.VehicleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MotorcycleService {
    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    private MotorcycleRepository motorcycleRepository;

    //CREATE
    @Transactional
    public MotorcycleResponseDTO createMotorcycle(MotorcycleRequestDTO motorcycleCreateDTO) {
        if(motorcycleCreateDTO.model() == null || motorcycleCreateDTO.model().isEmpty())
            throw new IncompleteVehicleDetailsException("Vehicle model must be informed");
        if(motorcycleCreateDTO.chassis() == null || motorcycleCreateDTO.chassis().isEmpty())
            throw new IncompleteVehicleDetailsException("Chassis number must be informed");

        if(!vehicleRepository.existsVehicleByChassis(motorcycleCreateDTO.chassis())) {
            Motorcycle motorcycle = motorcycleRepository.save(
                    new Motorcycle(motorcycleCreateDTO.model(), motorcycleCreateDTO.chassis(), motorcycleCreateDTO.tankCapacity())
            );
            return motorcycleToMotorcycleResponseDTO(motorcycle);
        }
        throw new ChassisNumberAlreadyExistsException(motorcycleCreateDTO.chassis());
    }

    //READ (ALL)
    @Transactional
    public List<MotorcycleResponseDTO> getAllMotorcycles() {
        return motorcycleRepository.findAll()
                .stream()
                .map(this::motorcycleToMotorcycleResponseDTO)
                .collect(Collectors.toList());
    }

    //READ (BY ID)
    @Transactional
    public MotorcycleResponseDTO getMotorcycleById(UUID id) {
        Optional<Motorcycle> optionalMotorcycle = motorcycleRepository.findById(id);
        if(optionalMotorcycle.isPresent()) {
            Motorcycle motorcycle = optionalMotorcycle.get();
            return motorcycleToMotorcycleResponseDTO(motorcycle);
        }
        throw new MotorcycleUUIDNotFoundException(id);
    }

    //UPDATE
    @Transactional
    public MotorcycleResponseDTO updateMotorcycle(UUID id, MotorcycleRequestDTO motorcycleUpdateDTO) {
        if(motorcycleUpdateDTO.model() == null)
            throw new IncompleteVehicleDetailsException("Vehicle model must be informed");
        if(motorcycleUpdateDTO.chassis() == null)
            throw new IncompleteVehicleDetailsException("Chassis number must be informed");

        Optional<Motorcycle> optionalMotorcycle = motorcycleRepository.findById(id);
        if(optionalMotorcycle.isPresent()) {
            Motorcycle motorcycle = optionalMotorcycle.get();
            if(!motorcycleUpdateDTO.chassis().equals(motorcycle.getChassis()))
                if(vehicleRepository.existsVehicleByChassis(motorcycleUpdateDTO.chassis()))
                    throw new ChassisNumberAlreadyExistsException(motorcycleUpdateDTO.chassis());

            updateMotorcycle(motorcycleUpdateDTO, motorcycle);
                motorcycleRepository.save(motorcycle);
            return motorcycleToMotorcycleResponseDTO(motorcycle);
        }
        throw new MotorcycleUUIDNotFoundException(id);
    }

    //DELETE
    @Transactional
    public void deleteMotorcycle(UUID id) {
        if(motorcycleRepository.existsMotorcycleById(id))
            motorcycleRepository.deleteById(id);
        else
            throw new MotorcycleUUIDNotFoundException(id);
    }

    private MotorcycleResponseDTO motorcycleToMotorcycleResponseDTO(Motorcycle motorcycle) {
        return new MotorcycleResponseDTO(
                motorcycle.getId(),
                motorcycle.getModel(),
                motorcycle.getChassis(),
                motorcycle.getTankCapacity()
        );
    }

    private void updateMotorcycle(MotorcycleRequestDTO motorcycleUpdateDTO, Motorcycle motorcycle) {
        motorcycle.setModel(motorcycleUpdateDTO.model());
        motorcycle.setChassis(motorcycleUpdateDTO.chassis());
        motorcycle.setTankCapacity(motorcycleUpdateDTO.tankCapacity());
    }
}
