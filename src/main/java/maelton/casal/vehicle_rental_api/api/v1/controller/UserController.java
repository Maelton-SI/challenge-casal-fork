package maelton.casal.vehicle_rental_api.api.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import maelton.casal.vehicle_rental_api.api.v1.dto.user.UserResponseDTO;
import maelton.casal.vehicle_rental_api.api.v1.dto.user.UserRequestDTO;
import maelton.casal.vehicle_rental_api.api.v1.exception.handler.ExceptionResponse;
import maelton.casal.vehicle_rental_api.api.v1.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@Tag(name = "Users Management", description = "Endpoints for managing users")
public class UserController {
    @Autowired
    private UserService userService;

    //CREATE
    @Operation(summary = "Creates a new user", method = "POST")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "201",
                         description = "New user created successfully",
                         content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class)
                         )}
            ),
            @ApiResponse(responseCode = "400",
                         description = "Invalid or corrupted request",
                         content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)
                         )}
            ),
            @ApiResponse(responseCode = "401",
                         description = "User has not logged in yet"
            ),
            @ApiResponse(responseCode = "403",
                    description = "Non admin users cannot create new users"
            ),
            @ApiResponse(responseCode = "409",
                         description = "Informed user email already exists",
                         content = {@Content(mediaType = "application/json",
                                schema = @Schema(implementation = ExceptionResponse.class)
                         )}
            )
        }
    )
    @PostMapping(produces = "application/json")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userCreateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userCreateDTO));
    }

    //READ (ALL)
    @Operation(summary = "Retrieves all users", method = "GET")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200",
                         description = "All users returned successfully",
                         content = {@Content(mediaType = "application/json",
                                schema = @Schema(type="array",
                                        implementation = UserResponseDTO.class
                                )
                         )}
            )
        }
    )
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    
    //READ BY (ID)
    //READ BY (EMAIL)
    //UPDATE BY (ID)
    //UPDATE BY (EMAIL)
    //DELETE BY (ID)
    //DELETE BY (EMAIL)
}
