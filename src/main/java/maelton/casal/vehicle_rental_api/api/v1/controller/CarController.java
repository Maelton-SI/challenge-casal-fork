package maelton.casal.vehicle_rental_api.api.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import maelton.casal.vehicle_rental_api.api.v1.model.dto.vehicle.car.CarRequestDTO;
import maelton.casal.vehicle_rental_api.api.v1.model.dto.vehicle.car.CarResponseDTO;
import maelton.casal.vehicle_rental_api.api.v1.exception.handler.ExceptionResponse;
import maelton.casal.vehicle_rental_api.api.v1.service.CarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value="/v1/cars")
@Tag(name = "Cars Management", description = "Endpoints for managing cars")
@SecurityRequirement(name = "jwtAuthentication")
public class CarController {
    @Autowired
    private CarService carService;

    //CREATE
    @Operation(summary = "Creates a new car", method = "POST")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "201",
                         description = "New car created successfully",
                         content = {@Content(mediaType = "application/json",
                                             schema = @Schema(implementation = CarResponseDTO.class)
                         )}
            ),
            @ApiResponse(responseCode = "400",
                         description = "Invalid or corrupted request",
                         content = {@Content(mediaType = "application/json",
                                schema = @Schema(implementation = ExceptionResponse.class)
                         )}
            ),
            @ApiResponse(responseCode = "403",
                         description = "Non admin users cannot create car registries"
            ),
            @ApiResponse(responseCode = "409",
                    description = "Informed chassis number already exists",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)
                    )}
            )
        }
    )
    @PostMapping(produces = "application/json")
    public ResponseEntity<CarResponseDTO> createCar(@RequestBody CarRequestDTO carCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.createCar(carCreateDTO));
    }

    //READ (ALL)
    @Operation(summary = "Retrieves all cars", method = "GET")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200",
                         description = "All cars returned successfully",
                         content = {@Content(mediaType = "application/json",
                                             schema = @Schema(type="array",
                                                              implementation = CarResponseDTO.class
                                             )
                         )}
            )
        }
    )
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<CarResponseDTO>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars());
    }

    //READ (BY ID)
    @Operation(summary = "Retrieves a car by its id", method = "GET")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200",
                         description = "Car returned successfully",
                         content = {@Content(mediaType = "application/json",
                                             schema = @Schema(implementation = CarResponseDTO.class)
                         )}
            ),
            @ApiResponse(responseCode = "404",
                    description = "Informed car UUID does not exist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)
                    )}
            )
        }
    )
    @GetMapping(value="/{id}", produces = "application/json")
    public ResponseEntity<CarResponseDTO> getCarById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(carService.getCarById(id));
    }

    //UPDATE
    @Operation(summary = "Updates a car by its id", method = "PUT")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200",
                         description = "Car updated successfully",
                         content = {@Content(mediaType = "application/json",
                                             schema = @Schema(implementation = CarResponseDTO.class)
                         )}
            ),
            @ApiResponse(responseCode = "400",
                         description = "Invalid or corrupted request",
                         content = {@Content(mediaType = "application/json",
                                schema = @Schema(implementation = ExceptionResponse.class)
                         )}
            ),
            @ApiResponse(responseCode = "403",
                         description = "Non admin users cannot update car registries"
            ),
            @ApiResponse(responseCode = "404",
                         description = "Informed car UUID does not exist",
                         content = {@Content(mediaType = "application/json",
                                             schema = @Schema(implementation = ExceptionResponse.class)
                         )}
            ),
            @ApiResponse(responseCode = "409",
                    description = "Informed chassis number already exists",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)
                    )}
            )
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<CarResponseDTO> updateCar(@PathVariable UUID id,
                                                    @RequestBody CarRequestDTO carUpdateDTO) {
        return ResponseEntity.ok(carService.updateCar(id, carUpdateDTO));
    }

    //DELETE
    @Operation(summary = "Deletes a car by its id", method = "DELETE")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "204",
                         description = "Car deleted successfully"
            ),
            @ApiResponse(responseCode = "403",
                         description = "Non admin users cannot delete car registries"
            ),
            @ApiResponse(responseCode = "404",
                         description = "Informed car UUID does not exist",
                         content = {@Content(mediaType = "application/json",
                                             schema = @Schema(implementation = ExceptionResponse.class)
                         )}
            )
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable UUID id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
}
