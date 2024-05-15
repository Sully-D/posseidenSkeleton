package com.nnk.springboot;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testGetUser_Success() {
        // Arrange
        User mockUser = new User();
        mockUser.setUsername("testUser");
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(mockUser));

        // Act
        User result = userService.getUser("testUser");

        // Assert
        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
    }

    @Test
    public void testGetUser_UserNotFound() {
        // Arrange
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            userService.getUser("unknownUser");
        });

        assertEquals("Username not found !", exception.getMessage());
    }
}
