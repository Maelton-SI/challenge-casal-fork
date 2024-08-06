package maelton.casal.vehicle_rental_api.api.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import maelton.casal.vehicle_rental_api.api.v1.dto.user.UserLoginDTO;
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

    //AUTHENTICATION
    @Operation(summary = "Authenticates a user", method = "POST")
//    @ApiResponses(value= {
//            @ApiResponse(responseCode = "200",
//                    description = "User authenticated successfully"
//                    //TODO: add content type
//            ),
//            @ApiResponse(responseCode = "403",
//                    description = "Authentication failed (?)"
//            )
//        }
//    )
    @PostMapping("/users") //TODO: Correct function returning type
    public ResponseEntity<String> authenticateUser(@RequestBody UserLoginDTO userLoginDTO) {
        //Send login data to authentication, a token is generated for the authentication if it's successful
        return ResponseEntity.ok(userService.authenticateUser(userLoginDTO));
    }
}
