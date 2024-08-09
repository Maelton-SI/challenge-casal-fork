package maelton.casal.vehicle_rental_api.api.v1.service;

import maelton.casal.vehicle_rental_api.api.v1.config.security.jwt.JSONWebTokenDTO;
import maelton.casal.vehicle_rental_api.api.v1.config.security.jwt.JWTService;
import maelton.casal.vehicle_rental_api.api.v1.exception.user.UserAuthenticationFailureException;
import maelton.casal.vehicle_rental_api.api.v1.model.dto.user.UserLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    //AUTHENTICATION
    public JSONWebTokenDTO authenticateUser(UserLoginDTO userLoginDTO) {
        //Spring Security itself authenticates the received login data for me
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginDTO.email(),
                            userLoginDTO.password()
                    )
            );

            return jwtService.generateToken(authentication);
        } catch (AuthenticationException e) {
            throw new UserAuthenticationFailureException("Email or password incorrect!");
        }
    }
}
