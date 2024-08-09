package maelton.casal.vehicle_rental_api.api.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import maelton.casal.vehicle_rental_api.api.v1.model.dto.motorcycle.MotorcycleRequestDTO;
import maelton.casal.vehicle_rental_api.api.v1.model.dto.motorcycle.MotorcycleResponseDTO;
import maelton.casal.vehicle_rental_api.api.v1.exception.handler.ExceptionResponse;
import maelton.casal.vehicle_rental_api.api.v1.service.MotorcycleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value="/v1/motorcycles", produces = {"application/json"})
@Tag(name = "Motorcycles Management", description = "Endpoints for managing motorcycles")
public class MotorcycleController {
    @Autowired
    private MotorcycleService motorcycleService;

    //CREATE
    @Operation(summary = "Creates a new motorcycle", method = "POST")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "201",
                    description = "New motorcycle created successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MotorcycleResponseDTO.class)
                    )}
            ),
            @ApiResponse(responseCode = "400",
                         description = "Invalid or corrupted request",
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
    @PostMapping(produces = "application/json")
    public ResponseEntity<MotorcycleResponseDTO> createMotorcycle(@RequestBody MotorcycleRequestDTO motorcycleCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(motorcycleService.createMotorcycle(motorcycleCreateDTO));
    }

    //READ (ALL)
    @Operation(summary = "Retrieves all motorcycles", method = "GET")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200",
                    description = "All motorcycles returned successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(type="array",
                                    implementation = MotorcycleResponseDTO.class
                            )
                    )}
            )
        }
    )
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<MotorcycleResponseDTO>> getAllMotorcycles() {
        return ResponseEntity.ok(motorcycleService.getAllMotorcycles());
    }

    //READ (BY ID)
    @Operation(summary = "Retrieves a motorcycle by its id", method = "GET")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200",
                    description = "Motorcycle returned successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MotorcycleResponseDTO.class)
                    )}
            ),
            @ApiResponse(responseCode = "404",
                    description = "Informed motorcycle UUID does not exist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)
                    )}
            )
        }
    )
    @GetMapping(value="/{id}", produces = "application/json")
    public ResponseEntity<MotorcycleResponseDTO> getMotorcycleById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(motorcycleService.getMotorcycleById(id));
    }

    //UPDATE
    @Operation(summary = "Updates a motorcycle by its id", method = "PUT")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200",
                    description = "Motorcycle updated successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MotorcycleResponseDTO.class)
                    )}
            ),
            @ApiResponse(responseCode = "400",
                         description = "Invalid or corrupted request",
                         content = {@Content(mediaType = "application/json",
                                schema = @Schema(implementation = ExceptionResponse.class)
                         )}
            ),
            @ApiResponse(responseCode = "404",
                    description = "Informed motorcycle UUID does not exist",
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
    public ResponseEntity<MotorcycleResponseDTO> updateMotorcycle(@PathVariable UUID id,
                                                    @RequestBody MotorcycleRequestDTO motorcycleUpdateDTO) {
        return ResponseEntity.ok(motorcycleService.updateMotorcycle(id, motorcycleUpdateDTO));
    }

    //DELETE
    @Operation(summary = "Deletes a motorcycle by its id", method = "DELETE")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "204",
                    description = "Motorcycle deleted successfully"
            ),
            @ApiResponse(responseCode = "404",
                    description = "Informed motorcycle UUID does not exist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)
                    )}
            )
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMotorcycle(@PathVariable UUID id) {
        motorcycleService.deleteMotorcycle(id);
        return ResponseEntity.noContent().build();
    }
}
