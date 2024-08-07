package maelton.casal.vehicle_rental_api.api.v1.service;

import maelton.casal.vehicle_rental_api.api.v1.exception.vehicle.IncompleteVehicleDetailsException;
import maelton.casal.vehicle_rental_api.api.v1.model.dto.motorcycle.MotorcycleRequestDTO;
import maelton.casal.vehicle_rental_api.api.v1.model.dto.motorcycle.MotorcycleResponseDTO;
import maelton.casal.vehicle_rental_api.api.v1.model.entity.vehicle.Motorcycle;
import maelton.casal.vehicle_rental_api.api.v1.exception.vehicle.MotorcycleUUIDNotFoundException;
import maelton.casal.vehicle_rental_api.api.v1.exception.vehicle.ChassisNumberAlreadyExistsException;
import maelton.casal.vehicle_rental_api.api.v1.repository.MotorcycleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MotorcycleService {
    @Autowired
    private MotorcycleRepository motorcycleRepository;

    //CREATE
    public MotorcycleResponseDTO createMotorcycle(MotorcycleRequestDTO motorcycleCreateDTO) {
        if(motorcycleCreateDTO.name() == null || motorcycleCreateDTO.name().isEmpty())
            throw new IncompleteVehicleDetailsException("Vehicle name must be informed");
        if(motorcycleCreateDTO.chassis() == null || motorcycleCreateDTO.chassis().isEmpty())
            throw new IncompleteVehicleDetailsException("Chassis number must be informed");

        if(motorcycleRepository.findMotorcycleByChassis(motorcycleCreateDTO.chassis()).isEmpty()) {
            Motorcycle motorcycle = motorcycleRepository.save(
                    new Motorcycle(motorcycleCreateDTO.name(),
                                   motorcycleCreateDTO.chassis(),
                                   motorcycleCreateDTO.tankCapacity()
                    )
            );
            return new MotorcycleResponseDTO(motorcycle.getId(),
                                             motorcycle.getName(),
                                             motorcycle.getChassis(),
                                             motorcycle.getTankCapacity()
            );
        }
        throw new ChassisNumberAlreadyExistsException(motorcycleCreateDTO.chassis());
    }

    //READ (ALL)
    public List<MotorcycleResponseDTO> getAllMotorcycles() {
        return motorcycleRepository.findAll()
                .stream()
                .map(motorcycle -> new MotorcycleResponseDTO(
                        motorcycle.getId(),
                        motorcycle.getName(),
                        motorcycle.getChassis(),
                        motorcycle.getTankCapacity())
                ).collect(Collectors.toList());
    }

    //READ (BY ID)
    public MotorcycleResponseDTO getMotorcycleById(UUID id) {
        Optional<Motorcycle> optionalMotorcycle = motorcycleRepository.findById(id);
        if(optionalMotorcycle.isPresent()) {
            return new MotorcycleResponseDTO(
                    optionalMotorcycle.get().getId(),
                    optionalMotorcycle.get().getName(),
                    optionalMotorcycle.get().getChassis(),
                    optionalMotorcycle.get().getTankCapacity()
            );
        }
        throw new MotorcycleUUIDNotFoundException(id);
    }

    //UPDATE
    public MotorcycleResponseDTO updateMotorcycle(UUID id, MotorcycleRequestDTO motorcycleUpdateDTO) {
        if(motorcycleUpdateDTO.name() == null)
            throw new IncompleteVehicleDetailsException("Vehicle name must be informed");
        if(motorcycleUpdateDTO.chassis() == null)
            throw new IncompleteVehicleDetailsException("Chassis number must be informed");

        Optional<Motorcycle> optionalMotorcycle = motorcycleRepository.findById(id);
        if(optionalMotorcycle.isPresent()) {
            Motorcycle motorcycle = optionalMotorcycle.get();
            if(!motorcycleUpdateDTO.chassis().equals(motorcycle.getChassis()))
                if(motorcycleRepository.existsMotorcycleByChassis(motorcycleUpdateDTO.chassis()))
                    throw new ChassisNumberAlreadyExistsException(motorcycleUpdateDTO.chassis());
            motorcycle.setName(motorcycleUpdateDTO.name());
            motorcycle.setChassis(motorcycleUpdateDTO.chassis());
            motorcycle.setTankCapacity(motorcycleUpdateDTO.tankCapacity());

            motorcycleRepository.save(motorcycle);

            return new MotorcycleResponseDTO(motorcycle.getId(),
                    motorcycle.getName(),
                    motorcycle.getChassis(),
                    motorcycle.getTankCapacity()
            );
        }
        throw new MotorcycleUUIDNotFoundException(id);
    }

    //DELETE
    public void deleteMotorcycle(UUID id) {
        if(motorcycleRepository.existsMotorcycleById(id))
            motorcycleRepository.deleteById(id);
        else
            throw new MotorcycleUUIDNotFoundException(id);
    }
}
