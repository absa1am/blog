package com.dsinnovators.blog.services;

import com.dsinnovators.blog.dto.LoginDTO;
import com.dsinnovators.blog.models.User;
import com.dsinnovators.blog.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        user.setPassword(getHashedPassword(user.getPassword()));

        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean isCredentialMatched(LoginDTO user, User existingUser) {
        user.setPassword(getHashedPassword(user.getPassword()));

        return (existingUser != null && existingUser.getPassword().equals(user.getPassword()));
    }

    public String getHashedPassword(String password) {
        String hashedPassword = "";

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] digest = messageDigest.digest(password.getBytes());
            BigInteger sigNum = new BigInteger(1, digest);

            hashedPassword = sigNum.toString(16);
        } catch (NoSuchAlgorithmException e) {
            logger.error("An unexpected error occurred during password hashing.", e);
        }

        return hashedPassword;
    }

}
