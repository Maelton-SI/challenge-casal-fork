package maelton.casal.vehicle_rental_api.api.v1.service;

import static maelton.casal.vehicle_rental_api.api.v1.common.VehicleConstants.*;

import static org.assertj.core.api.Assertions.assertThat;

import maelton.casal.vehicle_rental_api.api.v1.model.dto.vehicle.car.CarResponseDTO;
import maelton.casal.vehicle_rental_api.api.v1.model.entity.vehicle.Car;
import maelton.casal.vehicle_rental_api.api.v1.repository.CarRepository;
import maelton.casal.vehicle_rental_api.api.v1.repository.VehicleRepository;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

@SpringBootTest(classes = {CarService.class})
public class CarServiceTest {
    @Autowired
    private CarService carService;

    @MockBean
    private CarRepository carRepository;
    @MockBean
    private VehicleRepository vehicleRepository;

    //CREATE
    @Test
    public void saverCar_ReturnsCarResponseDTO() {
        when(vehicleRepository.save(CAR)).thenReturn(CAR);
        assertThat(carService.createCar(CAR_REQUEST_DTO)).isEqualTo(CAR_RESPONSE_DTO);
    };

    //READ ALL
    //READ BY ID
    //UPDATE
}
