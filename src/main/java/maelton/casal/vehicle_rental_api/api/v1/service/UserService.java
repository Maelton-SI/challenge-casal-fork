package maelton.casal.vehicle_rental_api.api.v1.service;

import maelton.casal.vehicle_rental_api.api.v1.dto.user.UserRequestDTO;
import maelton.casal.vehicle_rental_api.api.v1.dto.user.UserResponseDTO;
import maelton.casal.vehicle_rental_api.api.v1.entity.user.User;
import maelton.casal.vehicle_rental_api.api.v1.exception.user.UserEmailAlreadyExistsException;
import maelton.casal.vehicle_rental_api.api.v1.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    //@Autowired UserAuthenticationService (?)

    @Autowired
    private UserRepository userRepository;

    //CREATE
    public UserResponseDTO createUser(UserRequestDTO userCreateDTO) {
        if(userRepository.findUserByEmail(userCreateDTO.email()).isEmpty()) {
            User user = userRepository.save(
                    new User(userCreateDTO.name(), userCreateDTO.email(), userCreateDTO.password(), userCreateDTO.role())
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
