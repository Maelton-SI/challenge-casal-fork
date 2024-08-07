package maelton.casal.vehicle_rental_api.api.v1.service;

import maelton.casal.vehicle_rental_api.api.v1.config.security.service.JWTService;
import maelton.casal.vehicle_rental_api.api.v1.dto.user.UserLoginDTO;
import maelton.casal.vehicle_rental_api.api.v1.dto.user.UserRequestDTO;
import maelton.casal.vehicle_rental_api.api.v1.dto.user.UserResponseDTO;
import maelton.casal.vehicle_rental_api.api.v1.entity.user.User;
import maelton.casal.vehicle_rental_api.api.v1.exception.user.UserEmailAlreadyExistsException;
import maelton.casal.vehicle_rental_api.api.v1.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    //AUTHENTICATION //TODO: Correct function return type
    public String authenticateUser(UserLoginDTO userLoginDTO) {
        //Spring Security itself authenticates the received login data for me
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDTO.email(),
                        userLoginDTO.password()
                )
        );
        return jwtService.generateToken(authentication);
    }

    //CREATE
    public UserResponseDTO createUser(UserRequestDTO userCreateDTO) {
        if(userRepository.findUserByEmail(userCreateDTO.email()).isEmpty()) {
            User user = userRepository.save(
                    new User(userCreateDTO.name(),
                             userCreateDTO.email(),
                             passwordEncoder.encode(userCreateDTO.password()),
                             userCreateDTO.role())
            );
            return new UserResponseDTO(user.getId(), user.getName(),user.getEmail(), userCreateDTO.role());
        }
        throw new UserEmailAlreadyExistsException(userCreateDTO.email());
    }

    //READ (ALL)
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRole())
                ).collect(Collectors.toList());
    }
    
    //READ BY (ID)
    //READ BY (EMAIL)
    //UPDATE BY (ID)
    //UPDATE BY (EMAIL)
    //DELETE BY (ID)
    //DELETE BY (EMAIL)
}
