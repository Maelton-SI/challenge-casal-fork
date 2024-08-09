package maelton.casal.vehicle_rental_api.api.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import maelton.casal.vehicle_rental_api.api.v1.model.dto.vehicle.VehicleResponseDTO;
import maelton.casal.vehicle_rental_api.api.v1.service.CarService;
import maelton.casal.vehicle_rental_api.api.v1.service.MotorcycleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/v1/vehicles")
@Tag(name = "Vehicle Queries", description = "Endpoints for retrieving information about vehicles")
public class VehicleController {
    @Autowired
    CarService carService;

    @Autowired
    MotorcycleService motorcycleService;

    //READ (ALL)
    @Operation(summary = "Retrieves all vehicles (cars and motorcycles)", method = "GET")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200",
                    description = "All vehicles returned successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(type="array",
                                    implementation = VehicleResponseDTO.class
                            )
                    )}
            )
        }
    )
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<VehicleResponseDTO>> getAllVehicles() {
        List<VehicleResponseDTO> vehicles = new ArrayList<>();

        vehicles.addAll(carService.getAllCars());
        vehicles.addAll(motorcycleService.getAllMotorcycles());

        return ResponseEntity.ok(vehicles);
    }
}
