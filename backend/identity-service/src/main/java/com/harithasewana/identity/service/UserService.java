package com.harithasewana.identity.service;

import com.harithasewana.identity.entity.User;
import com.harithasewana.identity.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepolocal;

    public UserService(UserRepository userRepository){
        this.userRepolocal = userRepository;
    }

    public String getMessage() {
        return "Identity Service is running in service...";
    }

    public User createUser(User user) {
        return userRepolocal.save(user);
    }

    public List<User> getAllUsers() {
        return userRepolocal.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepolocal.findById(id);
    }
//    If the method returned a plain User, we would need to handle the possibility that no user was found.
//    Instead, Optional<User> represents a value that might or might not be present.

//    User exists -> Optional contains the user
//    User doesn't exist -> Optional is empty

    public Optional<User> updateUser(Long id, User updatedUser) {

        String newFirstName = updatedUser.getFirstName();
        String newLastName = updatedUser.getLastName();
        String newEmail = updatedUser.getEmail();

        return userRepolocal.findById(id)
                .map(existingUser -> {
                    existingUser.setFirstName(newFirstName);
                    existingUser.setLastName(newLastName);
                    existingUser.setEmail(newEmail);

                    return userRepolocal.save(existingUser);
                });
    }

    public boolean deleteUser(Long id) {
        if (!userRepolocal.existsById(id)) {
            return false;
        }

        userRepolocal.deleteById(id);
        return true;
    }

}
