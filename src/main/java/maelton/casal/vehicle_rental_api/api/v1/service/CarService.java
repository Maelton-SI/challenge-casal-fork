package maelton.casal.vehicle_rental_api.api.v1.service;

import maelton.casal.vehicle_rental_api.api.v1.dto.car.CarCreateOrUpdateDTO;
import maelton.casal.vehicle_rental_api.api.v1.dto.car.CarResponseDTO;
import maelton.casal.vehicle_rental_api.api.v1.entity.vehicle.Car;
import maelton.casal.vehicle_rental_api.api.v1.exception.CarUUIDNotFoundException;
import maelton.casal.vehicle_rental_api.api.v1.exception.ChassisNumberAlreadyExistsException;
import maelton.casal.vehicle_rental_api.api.v1.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    //CREATE
    public CarResponseDTO createCar(CarCreateOrUpdateDTO carCreateDTO) {
        if(carRepository.findCarByChassis(carCreateDTO.chassis()).isEmpty()) {
            Car car = carRepository.save(
                    new Car(carCreateDTO.name(), carCreateDTO.chassis(), carCreateDTO.numberOfDoors())
            );
            return new CarResponseDTO(car.getId(), car.getName(),car.getChassis(), carCreateDTO.numberOfDoors());
        }
        throw new ChassisNumberAlreadyExistsException(carCreateDTO.chassis());
    }

    //READ (ALL)
    public List<CarResponseDTO> getAllCars() {
        return carRepository.findAll()
                            .stream()
                            .map(car -> new CarResponseDTO(
                                            car.getId(),
                                            car.getName(),
                                            car.getChassis(),
                                            car.getNumberOfDoors())
                            ).collect(Collectors.toList());
    }

    //READ (BY ID)
    public CarResponseDTO getCarById(UUID id) {
        Optional<Car> optionalCar = carRepository.findById(id);
        if(optionalCar.isPresent())
            return new CarResponseDTO(optionalCar.get().getId(),
                                      optionalCar.get().getName(),
                                      optionalCar.get().getChassis(),
                                      optionalCar.get().getNumberOfDoors()
            );
        throw new CarUUIDNotFoundException(id);
    }

    //UPDATE
    public CarResponseDTO updateCar(UUID id, CarCreateOrUpdateDTO carUpdateDTO) {
        Optional<Car> optionalCar = carRepository.findById(id);
        if(optionalCar.isPresent()) {
            Car car = optionalCar.get();
                car.setName(carUpdateDTO.name());
                if(!carUpdateDTO.chassis().equals(car.getChassis()))
                    if(carRepository.existsCarByChassis(carUpdateDTO.chassis()))
                        throw new ChassisNumberAlreadyExistsException(carUpdateDTO.chassis());
                car.setChassis(carUpdateDTO.chassis());
                car.setNumberOfDoors(carUpdateDTO.numberOfDoors());

            carRepository.save(car);

            return new CarResponseDTO(car.getId(),
                                      car.getName(),
                                      car.getChassis(),
                                      car.getNumberOfDoors()
            );
        }
        throw new CarUUIDNotFoundException(id);
    }

    //DELETE
    public void deleteCar(UUID id) {
        if(carRepository.existsCarById(id))
            carRepository.deleteById(id);
        else
            throw new CarUUIDNotFoundException(id);
    }
}
