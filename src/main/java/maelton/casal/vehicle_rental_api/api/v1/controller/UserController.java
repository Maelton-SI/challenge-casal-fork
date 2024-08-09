package maelton.casal.vehicle_rental_api.api.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import maelton.casal.vehicle_rental_api.api.v1.model.dto.user.UserRequestDTO;
import maelton.casal.vehicle_rental_api.api.v1.model.dto.user.UserResponseDTO;
import maelton.casal.vehicle_rental_api.api.v1.exception.handler.ExceptionResponse;
import maelton.casal.vehicle_rental_api.api.v1.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
            ),
            @ApiResponse(responseCode = "404",
                    description = "Queried user UUID or email address not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)
                    )}
            )
        }
    )
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(
            @RequestParam(value = "id", required = false) UUID id,
            @RequestParam(value = "email", required = false) String email
    ) {
        return ResponseEntity.ok(userService.getAllUsers(id, email));
    }

    //READ (BY ID)
    @Operation(summary = "Retrieves a user by its id", method = "GET")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200",
                    description = "User returned successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponseDTO.class)
                    )}
            ),
            @ApiResponse(responseCode = "404",
                    description = "Informed user UUID does not exist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)
                    )}
            )
        }
    )
    @GetMapping(value="/{id}", produces = "application/json")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
    
    //UPDATE (BY ID)
    @Operation(summary = "Updates a user by its id", method = "PUT")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200",
                    description = "User updated successfully",
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
            @ApiResponse(responseCode = "404",
                    description = "Informed user UUID does not exist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)
                    )}
            ),
            @ApiResponse(responseCode = "409",
                    description = "Informed email address already exists",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)
                    )}
            )
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable UUID id,
                                                    @RequestBody UserRequestDTO userUpdateDTO) {
        return ResponseEntity.ok(userService.updateUserById(id, userUpdateDTO));
    }

    
    //DELETE (BY ID)
    @Operation(summary = "Deletes a user by its id", method = "DELETE")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "204",
                    description = "User deleted successfully"
            ),
            @ApiResponse(responseCode = "404",
                    description = "Informed user UUID does not exist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class)
                    )}
            )
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
