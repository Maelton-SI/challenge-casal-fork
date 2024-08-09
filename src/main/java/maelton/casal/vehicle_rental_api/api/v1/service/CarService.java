package maelton.casal.vehicle_rental_api.api.v1.service;

import jakarta.transaction.Transactional;

import maelton.casal.vehicle_rental_api.api.v1.exception.vehicle.IncompleteVehicleDetailsException;
import maelton.casal.vehicle_rental_api.api.v1.model.dto.vehicle.car.CarRequestDTO;
import maelton.casal.vehicle_rental_api.api.v1.model.dto.vehicle.car.CarResponseDTO;
import maelton.casal.vehicle_rental_api.api.v1.model.entity.vehicle.Car;
import maelton.casal.vehicle_rental_api.api.v1.exception.vehicle.CarUUIDNotFoundException;
import maelton.casal.vehicle_rental_api.api.v1.exception.vehicle.ChassisNumberAlreadyExistsException;
import maelton.casal.vehicle_rental_api.api.v1.repository.CarRepository;
import maelton.casal.vehicle_rental_api.api.v1.repository.VehicleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CarService {
    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private CarRepository carRepository;

    //CREATE
    @Transactional
    public CarResponseDTO createCar(CarRequestDTO carCreateDTO) {
        if(carCreateDTO.model() == null || carCreateDTO.model().isEmpty())
            throw new IncompleteVehicleDetailsException("Vehicle model must be informed");
        if(carCreateDTO.chassis() == null || carCreateDTO.chassis().isEmpty())
            throw new IncompleteVehicleDetailsException("Chassis number must be informed");

        if(!vehicleRepository.existsVehicleByChassis(carCreateDTO.chassis())) {
            Car car = carRepository.save(
                    new Car(carCreateDTO.model(), carCreateDTO.chassis(), carCreateDTO.numberOfDoors())
            );
            return new CarResponseDTO(car.getId(), car.getModel(),car.getChassis(), carCreateDTO.numberOfDoors());
        }
        throw new ChassisNumberAlreadyExistsException(carCreateDTO.chassis());
    }

    //READ (ALL)
    @Transactional
    public List<CarResponseDTO> getAllCars() {
        return carRepository.findAll()
                            .stream()
                            .map(car -> new CarResponseDTO(
                                            car.getId(),
                                            car.getModel(),
                                            car.getChassis(),
                                            car.getNumberOfDoors())
                            ).collect(Collectors.toList());
    }

    //READ (BY ID)
    @Transactional
    public CarResponseDTO getCarById(UUID id) {
        Optional<Car> optionalCar = carRepository.findById(id);
        if(optionalCar.isPresent()) {
            Car car = optionalCar.get();
            return new CarResponseDTO(
                    car.getId(),
                    car.getModel(),
                    car.getChassis(),
                    car.getNumberOfDoors()
            );
        }
        throw new CarUUIDNotFoundException(id);
    }

    //UPDATE
    @Transactional
    public CarResponseDTO updateCar(UUID id, CarRequestDTO carUpdateDTO) {
        if(carUpdateDTO.model() == null || carUpdateDTO.model().isEmpty())
            throw new IncompleteVehicleDetailsException("Vehicle model must be informed");
        if(carUpdateDTO.chassis() == null || carUpdateDTO.chassis().isEmpty())
            throw new IncompleteVehicleDetailsException("Chassis number must be informed");

        Optional<Car> optionalCar = carRepository.findById(id);
        if(optionalCar.isPresent()) {
            Car car = optionalCar.get();
                if(!carUpdateDTO.chassis().equals(car.getChassis()))
                    if(vehicleRepository.existsVehicleByChassis(carUpdateDTO.chassis()))
                        throw new ChassisNumberAlreadyExistsException(carUpdateDTO.chassis());
                car.setModel(carUpdateDTO.model());
                car.setChassis(carUpdateDTO.chassis());
                car.setNumberOfDoors(carUpdateDTO.numberOfDoors());

            carRepository.save(car);

            return new CarResponseDTO(
                    car.getId(),
                    car.getModel(),
                    car.getChassis(),
                    car.getNumberOfDoors()
            );
        }
        throw new CarUUIDNotFoundException(id);
    }

    //DELETE
    @Transactional
    public void deleteCar(UUID id) {
        if(carRepository.existsCarById(id))
            carRepository.deleteById(id);
        else
            throw new CarUUIDNotFoundException(id);
    }
}
