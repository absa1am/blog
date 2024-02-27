package com.dsinnovators.blog.services;

import com.dsinnovators.blog.dto.LoginDTO;
import com.dsinnovators.blog.models.User;
import com.dsinnovators.blog.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean isCredentialMatched(LoginDTO user, User existingUser) {
        return (existingUser != null && existingUser.getPassword().equals(user.getPassword()));
    }

}
