package com.business.app.repositories;

import com.business.app.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByUsername() {
        Optional<User> user = userRepository.findByUsername("John Doe");
        assertFalse("User is absent is repository", user.isPresent());
    }

    @Test
    public void userIsPresentInRepository() {
        User user = new User("Chuck Norris", "strong_pass");
        User savedUser = userRepository.save(user);
        assertNotNull(savedUser);
        assertTrue("User is absent is repository", userRepository.findByUsername(user.getUsername()).isPresent());
    }
}