package com.business.app.service;

import com.business.app.domain.User;
import com.business.app.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @Mock
    private UserRepository repository;
    @Mock
    private PasswordEncoder encoder;

    private UserService service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = new UserService(repository, encoder);
    }

    @Test
    public void addUser() {
        User user = new User("Joe", "qwerty");
        when(repository.findByUsername("Joe")).thenReturn(Optional.empty());
        assertTrue("User was added", service.addUser(user));
        verify(repository, times(1)).findByUsername(anyString());
        verify(repository, times(1)).save(any());
    }

    @Test
    public void userAlreadyExists() {
        User user = new User("Joe", "qwerty");
        when(repository.findByUsername("Joe")).thenReturn(Optional.of(user));
        assertFalse(service.addUser(user));
        verify(repository, times(1)).findByUsername(anyString());
        verify(repository, never()).save(any());
    }
}