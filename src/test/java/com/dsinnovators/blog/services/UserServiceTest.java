package com.dsinnovators.blog.services;

import com.dsinnovators.blog.dto.LoginDTO;
import com.dsinnovators.blog.models.User;
import com.dsinnovators.blog.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @ParameterizedTest
    @MethodSource("usersProvider")
    void saveUser(User user) {
        when(userRepository.save(user)).thenReturn(user);

        var savedUser = userService.saveUser(user);

        assertAll("saveUser",
                () -> assertEquals(user.getId(), savedUser.getId()),
                () -> assertEquals(user.getName(), savedUser.getName()),
                () -> assertEquals(user.getEmail(), savedUser.getEmail()),
                () -> assertEquals(user.getPassword(), savedUser.getPassword())
        );

        verify(userRepository).save(user);
    }

    @ParameterizedTest
    @ValueSource(strings = { "salaam.mbstu@gmail.com", "none@example.com" })
    void findByEmail(String email) {
        when(userRepository.findByEmail(email)).thenReturn(usersProvider()
                .stream()
                .filter(u -> u.getEmail().equals(email))
                .toList()
                .get(0));

        var user = userService.findByEmail(email);

        assertAll("user", () -> assertEquals(email, user.getEmail()));

        verify(userRepository).findByEmail(email);
    }

    @ParameterizedTest
    @MethodSource("loginDtoAndUserProvider")
    void isCredentialMatched(LoginDTO user, User existingUser) {
        var isMatched = userService.isCredentialMatched(user, existingUser);

        assertTrue(isMatched);
    }

    @ParameterizedTest
    @ValueSource(strings = { "123456", "654321" })
    void getHashedPassword(String password) {
        String hashedPassword = userService.getHashedPassword(password);

        if (password.equals("123456")) {
            assertEquals("e10adc3949ba59abbe56e057f20f883e", hashedPassword);
        } else if (password.equals("654321")) {
            assertEquals("c33367701511b4f6020ec61ded352059", hashedPassword);
        }
    }

    static Stream<Arguments> loginDtoAndUserProvider() {
        User existingUser = new User();
        LoginDTO user = new LoginDTO();

        existingUser.setId(1L);
        existingUser.setName("Md. Abdus Salam");
        existingUser.setEmail("salaam.mbstu@gmail.com");
        existingUser.setPassword("e10adc3949ba59abbe56e057f20f883e");

        user.setEmail("salaam.mbstu@gmail.com");
        user.setPassword("123456");

        return Stream.of(
                Arguments.arguments(user, existingUser)
        );
    }

    static List<User> usersProvider() {
        User user;
        List<User> users = new ArrayList<>();

        user = new User();

        user.setId(1L);
        user.setName("Md. Abdus Salam");
        user.setEmail("salaam.mbstu@gmail.com");
        user.setPassword("123456");

        users.add(user);

        user = new User();

        user.setId(2L);
        user.setName("No Name");
        user.setEmail("none@example.com");
        user.setPassword("123456");

        users.add(user);

        return users;
    }

}
