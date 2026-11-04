package com.harithasewana.identity.controller;

import com.harithasewana.identity.dto.*;
import com.harithasewana.identity.entity.User;
import com.harithasewana.identity.service.JwtService;
import com.harithasewana.identity.service.UserService;
import com.harithasewana.identity.service.TestService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/identity")
public class UserController {

    private final UserService userlocal;

    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService){
        this.userlocal = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/users")
    public UserResponse createUser(@Valid @RequestBody
                           UserRegistrationRequest request) {

        return userlocal.createUser(request);
        //@Valid - This tells Spring to execute the validation rules defined in UserRegistrationRequest
        //@RequestBody - Tells Spring to read the incoming request body and convert its JSON into a Java object.
        //UserRegistrationRequest - We now accept a DTO instead of directly accepting the database entity
    }

    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {
        return userlocal.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) { //ResponseEntity lets us control the HTTP response, including its status code and body.
        return userlocal.getUserById(id)
                .map(ResponseEntity::ok) //If the Optional contains a user, this converts that user into a successful HTTP response.
                .orElseGet(() -> ResponseEntity.notFound().build());  //If the Optional is empty, this returns a 404 Not Found response.
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequest request) {

        return userlocal.updateUser(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean deleted = userlocal.deleteUser(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response = userlocal.login(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/validate-token")
    public ResponseEntity<String> validateToken(
            @RequestParam String token) {

        if (!jwtService.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid or expired token");
        }

        Long userId = jwtService.extractUserId(token);

        return ResponseEntity.ok("Valid token. User ID: " + userId);
    }
}
