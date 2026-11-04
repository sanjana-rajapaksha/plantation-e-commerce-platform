package com.harithasewana.identity.service;

import com.harithasewana.identity.dto.*;
import com.harithasewana.identity.entity.User;
import com.harithasewana.identity.exception.EmailAlreadyExistsException;
import com.harithasewana.identity.exception.InvalidCredentialsException;
import com.harithasewana.identity.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.harithasewana.identity.service.JwtService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepolocal;
    private final PasswordEncoder passEncorderLocal;
    private final JwtService jwtService;

    /*
    ------------------------
    Dependancy Injection(DI)
    ------------------------
    IoC: Spring controls object creation and management.
    Dependency Injection: Spring provides the required objects to your classes.
    IoC Container: The place where Spring manages those objects (commonly called Spring Beans).

    Conceptually, Spring performs something similar to this:

     Spring creates the repository implementation
             UserRepository repository = Spring Data JPA implementation ;

     Spring creates the password encoder
             PasswordEncoder encoder = new BCryptPasswordEncoder();

    Spring injects both dependencies
            UserService userService = new UserService(repository, encoder);
    */

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService)
    {
        this.userRepolocal = userRepository;
        this.passEncorderLocal = passwordEncoder;
        this.jwtService = jwtService;;
    }

    public UserResponse createUser(UserRegistrationRequest request) {

        // Check whether email already exists
        if(userRepolocal.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException("Email is already registered");
        }

        User user = new User();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passEncorderLocal.encode(request.getPassword()));

        User savedUser = userRepolocal.save(user);

        return mapToResponse(savedUser);
    }

    private UserResponse mapToResponse(User user) {
        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());

        return response;
    }

    public List<UserResponse> getAllUsers() {

        return userRepolocal.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public Optional<UserResponse> getUserById(Long id) {
        return userRepolocal.findById(id)
                .map(this::mapToResponse);
    }
//    If the method returned a plain User, we would need to handle the possibility that no user was found.
//    Instead, Optional<User> represents a value that might or might not be present.

//    User exists -> Optional contains the user
//    User doesn't exist -> Optional is empty

    public Optional<UserResponse> updateUser(Long id, UserUpdateRequest request) {

        if (userRepolocal.existsByEmailAndIdNot(request.getEmail(), id)) {
            throw new EmailAlreadyExistsException("Email is already registered");
        }

        return userRepolocal.findById(id).map(user -> {

            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());

            User updatedUser = userRepolocal.save(user);

            return mapToResponse(updatedUser);
        });
    }

    public boolean deleteUser(Long id) {
        if (!userRepolocal.existsById(id)) {
            return false;
        }

        userRepolocal.deleteById(id);
        return true;
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepolocal.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid email or password"));

        if (!passEncorderLocal.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = jwtService.generateToken(user);

        LoginResponse response = new LoginResponse();
        response.setAccessToken(token);
        response.setTokenType("Bearer");
        response.setExpiresIn(3600);

        return response;
    }

}
