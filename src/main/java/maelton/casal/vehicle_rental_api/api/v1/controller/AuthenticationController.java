package maelton.casal.vehicle_rental_api.api.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import maelton.casal.vehicle_rental_api.api.v1.config.security.jwt.JSONWebTokenDTO;
import maelton.casal.vehicle_rental_api.api.v1.exception.handler.ExceptionResponse;
import maelton.casal.vehicle_rental_api.api.v1.model.dto.user.UserLoginDTO;
import maelton.casal.vehicle_rental_api.api.v1.service.AuthenticationService;
import maelton.casal.vehicle_rental_api.api.v1.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@Tag(name = "Authentication", description = "API authentication endpoints")
public class AuthenticationController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    //AUTHENTICATION
    @Operation(summary = "Authenticates a user", method = "POST")
    @ApiResponses(value= {
            @ApiResponse(responseCode = "200",
                         description = "User authenticated successfully"
                         //TODO: add content type
            ),
            @ApiResponse(responseCode = "401",
                         description = "Authentication failed, email or password incorrect",
                         content = {@Content(mediaType = "application/json",
                         schema = @Schema(implementation = ExceptionResponse.class)
                         )}
            )
        }
    )
    @PostMapping("/users")
    public ResponseEntity<JSONWebTokenDTO> authenticateUser(@RequestBody UserLoginDTO userLoginDTO) {
        return ResponseEntity.ok(authenticationService.authenticateUser(userLoginDTO));
    }
}
