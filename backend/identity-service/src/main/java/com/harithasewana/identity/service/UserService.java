package com.harithasewana.identity.service;

import com.harithasewana.identity.entity.User;
import com.harithasewana.identity.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepolocal;

    public UserService(UserRepository userRepository){
        this.userRepolocal = userRepository;
    }

    public String getMessage()
    {
        return "Identity Service is running in service...";
    }

    public User createUser(User user)
    {
        return userRepolocal.save(user);
    }
}
