package com.harithasewana.identity.controller;

import com.harithasewana.identity.entity.User;
import com.harithasewana.identity.service.UserService;
import com.harithasewana.identity.service.TestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/identity")
public class UserController {

    private final UserService userlocal;
    private final TestService test;

    public UserController(UserService userService, TestService testService){
        this.userlocal = userService;
        this.test = testService;
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return userlocal.createUser(user);
        //@RequestBody
        //Tells Spring to read the incoming request body and convert its JSON into a Java object.
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userlocal.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) { //ResponseEntity lets us control the HTTP response, including its status code and body.
        return userlocal.getUserById(id)
                .map(ResponseEntity::ok) //If the Optional contains a user, this converts that user into a successful HTTP response.
                .orElseGet(() -> ResponseEntity.notFound().build());  //If the Optional is empty, this returns a 404 Not Found response.
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @RequestBody User updatedUser) {

        return userlocal.updateUser(id, updatedUser)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean deleted = userlocal.deleteUser(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
