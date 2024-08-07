package maelton.casal.vehicle_rental_api.api.v1.service;

import maelton.casal.vehicle_rental_api.api.v1.config.security.JWTService;
import maelton.casal.vehicle_rental_api.api.v1.dto.user.UserRequestDTO;
import maelton.casal.vehicle_rental_api.api.v1.dto.user.UserResponseDTO;
import maelton.casal.vehicle_rental_api.api.v1.dto.user.UserLoginDTO;
import maelton.casal.vehicle_rental_api.api.v1.entity.user.User;
import maelton.casal.vehicle_rental_api.api.v1.exception.user.RequestedUserNotFoundException;
import maelton.casal.vehicle_rental_api.api.v1.exception.user.UserEmailAlreadyExistsException;
import maelton.casal.vehicle_rental_api.api.v1.exception.user.UserEmailNotFoundException;
import maelton.casal.vehicle_rental_api.api.v1.exception.user.UserUUIDNotFoundException;
import maelton.casal.vehicle_rental_api.api.v1.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
    public List<UserResponseDTO> getAllUsers(UUID id, String email) {
        if(id == null && email == null) {
            return userRepository.findAll()
                    .stream()
                    .map(user -> new UserResponseDTO(
                            user.getId(),
                            user.getName(),
                            user.getEmail(),
                            user.getRole())
                    ).collect(Collectors.toList());
        }

        if(id != null) {
            Optional<User> optionalUser = userRepository.findById(id);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                return Collections.singletonList(
                        new UserResponseDTO(
                                user.getId(),
                                user.getName(),
                                user.getEmail(),
                                user.getRole())
                );
            }
        }

        if(email != null) {
            Optional<User> optionalUser = userRepository.findUserByEmail(email);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                return Collections.singletonList(
                        new UserResponseDTO(
                                user.getId(),
                                user.getName(),
                                user.getEmail(),
                                user.getRole())
                );
            }
        }
        throw new RequestedUserNotFoundException(id, email);
    }
    
    //READ (BY ID)
    public UserResponseDTO getUserById(UUID id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new UserResponseDTO(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getRole()
            );
        }
        throw new UserUUIDNotFoundException(id);
    }
    
    //READ (BY EMAIL)
    public UserResponseDTO getUserByEmail(String email) {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new UserResponseDTO(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getRole()
            );
        }
        throw new UserEmailNotFoundException(email);
    }
    
    //UPDATE (BY ID)
    public UserResponseDTO updateUserById(UUID id, UserRequestDTO userUpdateDTO) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            if(!userUpdateDTO.email().equals(user.getEmail()))
                if(userRepository.existsUserByEmail(userUpdateDTO.email()))
                    throw new UserEmailAlreadyExistsException(userUpdateDTO.email());
            user.setName(userUpdateDTO.name());
            user.setEmail(userUpdateDTO.email());
            user.setPassword(passwordEncoder.encode(userUpdateDTO.password()));
            user.setRole(userUpdateDTO.role());

            userRepository.save(user);

            return new UserResponseDTO(user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getRole()
            );
        }
        throw new UserUUIDNotFoundException(id);
    }

    //UPDATE (BY EMAIL)
    public UserResponseDTO updateUserByEmail(String email, UserRequestDTO userUpdateDTO) {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            if(!userUpdateDTO.email().equals(user.getEmail()))
                if(userRepository.existsUserByEmail(userUpdateDTO.email()))
                    throw new UserEmailAlreadyExistsException(userUpdateDTO.email());
            user.setName(userUpdateDTO.name());
            user.setEmail(userUpdateDTO.email());
            user.setPassword(passwordEncoder.encode(userUpdateDTO.password()));
            user.setRole(userUpdateDTO.role());

            userRepository.save(user);

            return new UserResponseDTO(user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getRole()
            );
        }
        throw new UserEmailNotFoundException(email);
    }

    //DELETE (BY ID)
    public void deleteUserById(UUID id) {
        if(userRepository.existsUserById(id))
            userRepository.deleteById(id);
        else
            throw new UserUUIDNotFoundException(id);
    }

    //DELETE (BY EMAIL)
    public void deleteUserByEmail(String email) {
        if(userRepository.existsUserByEmail(email))
            userRepository.deleteByEmail(email);
        else
            throw new UserEmailNotFoundException(email);
    }
}
