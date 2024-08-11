package maelton.casal.vehicle_rental_api.api.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import maelton.casal.vehicle_rental_api.api.v1.exception.handler.ExceptionResponse;
import maelton.casal.vehicle_rental_api.api.v1.model.dto.rental.CarRentalRequestDTO;
import maelton.casal.vehicle_rental_api.api.v1.model.dto.rental.MotorcycleRentalRequestDTO;
import maelton.casal.vehicle_rental_api.api.v1.model.dto.rental.VehicleRentalRequestDTO;
import maelton.casal.vehicle_rental_api.api.v1.model.dto.rental.VehicleRentalResponseDTO;
import maelton.casal.vehicle_rental_api.api.v1.service.RentalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/rentals")
@Tag(name = "Rentals Management", description = "Endpoints for managing rental registries")
@SecurityRequirement(name = "jwtAuthentication")
public class RentalController {
    @Autowired
    RentalService rentalService;

    //CREATE
    @Operation(summary = "Creates a new rental registry by specifying customer and renting vehicle", method = "POST")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "201",
                         description = "New rental registry created successfully",
                         content = {@Content(mediaType = "application/json",
                                 schema = @Schema(implementation = VehicleRentalResponseDTO.class)
                         )}
            ),
            @ApiResponse(responseCode = "403",
                         description = "Non admin users cannot create rental registries to any user"
            ),
            @ApiResponse(responseCode = "404",
                         description = "Informed rental id or vehicle id not found",
                         content = {@Content(mediaType = "application/json",
                                 schema = @Schema(implementation = ExceptionResponse.class)
                         )}
            )
        }
    )
    @PostMapping(produces = "application/json")
    public ResponseEntity<VehicleRentalResponseDTO> createRental(@RequestBody VehicleRentalRequestDTO rentalCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rentalService.createRental(rentalCreateDTO));
    }

    //CREATE CAR RENTAL
    @Operation(summary = "Allows a JWT authenticated user to rent a car by specifying its UUID", method = "POST")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "201",
                         description = "New car rental registry created successfully",
                         content = {@Content(mediaType = "application/json",
                                 schema = @Schema(implementation = VehicleRentalResponseDTO.class)
                         )}
            ),
            @ApiResponse(responseCode = "404",
                         description = "Informed car UUID does not exist",
                         content = {@Content(mediaType = "application/json",
                                 schema = @Schema(implementation = ExceptionResponse.class)
                         )}
            ),
            @ApiResponse(responseCode = "500",
                         description = "Internal server error while trying to get customer",
                         content = {@Content(mediaType = "application/json",
                                 schema = @Schema(implementation = ExceptionResponse.class)
                         )}
            )
        }
    )
    @PostMapping(value="/car", produces = "application/json")
    public ResponseEntity<VehicleRentalResponseDTO> createCarRental(@RequestBody CarRentalRequestDTO carRentalCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rentalService.createCarRental(carRentalCreateDTO));
    }

    //CREATE MOTORCYCLE RENTAL
    @Operation(summary = "Allows a JWT authenticated user to rent a motorcycle by specifying its UUID", method = "POST")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "201",
                    description = "New motorcycle rental registry created successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = VehicleRentalResponseDTO.class)
                    )}
            ),
            @ApiResponse(responseCode = "404",
                         description = "Informed motorcycle UUID does not exist",
                         content = {@Content(mediaType = "application/json",
                                 schema = @Schema(implementation = ExceptionResponse.class)
                         )}
            ),
            @ApiResponse(responseCode = "500",
                         description = "Internal server error while trying to get customer",
                         content = {@Content(mediaType = "application/json",
                                 schema = @Schema(implementation = ExceptionResponse.class)
                         )}
            )
        }
    )
    @PostMapping(value="/motorcycle",produces = "application/json")
    public ResponseEntity<VehicleRentalResponseDTO> createMotorcycleRental(@RequestBody MotorcycleRentalRequestDTO motorcycleRentalCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rentalService.createMotorcycleRental(motorcycleRentalCreateDTO));
    }

    //READ (ALL)
    @Operation(summary = "Retrieves all rental registries", method = "GET")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200",
                    description = "All rental registries returned successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(type="array",
                                    implementation = VehicleRentalResponseDTO.class
                            )
                    )}
            ),
            @ApiResponse(responseCode = "404",
                    description = "Queried customer email or vehicle chassis not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)
                    )}
            )
        }
    )
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<VehicleRentalResponseDTO>> getAllRentals(
            @RequestParam(value = "customerEmail", required = false) String customerEmail,
            @RequestParam(value = "vehicleChassis", required = false) String vehicleChassis
    ) {
        return ResponseEntity.ok(rentalService.getAllRentals(customerEmail, vehicleChassis));
    }

    //READ (BY ID)
    @Operation(summary = "Retrieves a rental registry by its id", method = "GET")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200",
                         description = "Rental registry returned successfully",
                         content = {@Content(mediaType = "application/json",
                                 schema = @Schema(implementation = VehicleRentalResponseDTO.class)
                         )}
            ),
            @ApiResponse(responseCode = "404",
                         description = "Informed rental UUID does not exist",
                         content = {@Content(mediaType = "application/json",
                                 schema = @Schema(implementation = ExceptionResponse.class)
                         )}
            )
        }
    )
    @GetMapping(value="/{id}", produces = "application/json")
    public ResponseEntity<VehicleRentalResponseDTO> getRentalById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(rentalService.getRentalById(id));
    }

    //UPDATE
    @Operation(summary = "Updates a rental registry by its id", method = "PUT")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200",
                         description = "Rental registry updated successfully",
                         content = {@Content(mediaType = "application/json",
                                 schema = @Schema(implementation = VehicleRentalResponseDTO.class)
                         )}
            ),
            @ApiResponse(responseCode = "403",
                         description = "Non admin users cannot update rental registries"
            ),
            @ApiResponse(responseCode = "400",
                         description = "Invalid or corrupted request",
                         content = {@Content(mediaType = "application/json",
                                 schema = @Schema(implementation = ExceptionResponse.class)
                         )}
            ),
            @ApiResponse(responseCode = "404",
                         description = "Informed rental UUID does not exist",
                         content = {@Content(mediaType = "application/json",
                                 schema = @Schema(implementation = ExceptionResponse.class)
                         )}
            )
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<VehicleRentalResponseDTO> updateRental(@PathVariable UUID id,
                                                                 @RequestBody VehicleRentalRequestDTO rentalUpdateDTO) {
        return ResponseEntity.ok(rentalService.updateRental(id, rentalUpdateDTO));
    }

    //DELETE
    @Operation(summary = "Deletes a rental registry by its id", method = "DELETE")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "204",
                         description = "Rental deleted successfully"
            ),
            @ApiResponse(responseCode = "403",
                        description = "Non admin users cannot delete rental registries"
            ),
            @ApiResponse(responseCode = "404",
                         description = "Informed rental UUID does not exist",
                         content = {@Content(mediaType = "application/json",
                                 schema = @Schema(implementation = ExceptionResponse.class)
                         )}
            )
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable UUID id) {
        rentalService.deleteRental(id);
        return ResponseEntity.noContent().build();
    }
}
