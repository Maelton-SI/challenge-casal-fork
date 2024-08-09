package maelton.casal.vehicle_rental_api.api.v1.service;

import jakarta.transaction.Transactional;

import maelton.casal.vehicle_rental_api.api.v1.exception.user.IncompleteUserDetailsException;
import maelton.casal.vehicle_rental_api.api.v1.exception.user.UserEmailAlreadyExistsException;
import maelton.casal.vehicle_rental_api.api.v1.exception.user.UserUUIDNotFoundException;
import maelton.casal.vehicle_rental_api.api.v1.exception.user.UserEmailNotFoundException;
import maelton.casal.vehicle_rental_api.api.v1.model.dto.user.UserRequestDTO;
import maelton.casal.vehicle_rental_api.api.v1.model.dto.user.UserResponseDTO;
import maelton.casal.vehicle_rental_api.api.v1.model.entity.User;
import maelton.casal.vehicle_rental_api.api.v1.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //CREATE
    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userCreateDTO) {
        if(userCreateDTO.email() == null || userCreateDTO.email().isEmpty())
            throw new IncompleteUserDetailsException("User email address has not been informed");
        if(userCreateDTO.password() == null || userCreateDTO.password().isEmpty())
            throw new IncompleteUserDetailsException("User password has not been informed");

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
    @Transactional
    public List<UserResponseDTO> getAllUsers(UUID id, String email) {
        if(id == null && (email == null || email.isEmpty())) {
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
            User user = userRepository.findById(id).orElseThrow(
                    () -> new UserUUIDNotFoundException(id)
            );

            return Collections.singletonList(
                    new UserResponseDTO(
                            user.getId(),
                            user.getName(),
                            user.getEmail(),
                            user.getRole()
                    )
            );
        }


        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new UserEmailNotFoundException(email)
        );

        return Collections.singletonList(
                new UserResponseDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRole()
                )
        );
    }
    
    //READ (BY ID)
    @Transactional
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
    @Transactional
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
    @Transactional
    public UserResponseDTO updateUserById(UUID id, UserRequestDTO userUpdateDTO) {
        if(userUpdateDTO.email() == null || userUpdateDTO.email().isEmpty())
            throw new IncompleteUserDetailsException("User email address has not been informed");
        if(userUpdateDTO.password() == null || userUpdateDTO.password().isEmpty())
            throw new IncompleteUserDetailsException("User password has not been informed");

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
    @Transactional
    public UserResponseDTO updateUserByEmail(String email, UserRequestDTO userUpdateDTO) {
        if(userUpdateDTO.email() == null || userUpdateDTO.email().isEmpty())
            throw new IncompleteUserDetailsException("User email address has not been informed");
        if(userUpdateDTO.password() == null || userUpdateDTO.password().isEmpty())
            throw new IncompleteUserDetailsException("User password has not been informed");

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
    @Transactional
    public void deleteUserById(UUID id) {
        if(userRepository.existsUserById(id))
            userRepository.deleteById(id);
        else
            throw new UserUUIDNotFoundException(id);
    }

    //DELETE (BY EMAIL)
    @Transactional
    public void deleteUserByEmail(String email) {
        if(userRepository.existsUserByEmail(email))
            userRepository.deleteByEmail(email);
        else
            throw new UserEmailNotFoundException(email);
    }
}
