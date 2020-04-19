package com.business.app.service;

import com.business.app.domain.User;
import com.business.app.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserService implements UserDetailsService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repository, @Qualifier("bCrypt") PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    // TODO: doubts: exception in log but correct message or 'Bad credentials  without stacktrace in log'
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s is not found", username)));
    }

    public boolean addUser(User user)
    {
        Optional<User> userFromDb = repository.findByUsername(user.getUsername());
        if (userFromDb.isPresent())
        {
            log.info("User {} already exists!", user.getUsername());
            return false;
        }
        log.info("Create new user");
        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);
        return true;
    }
}
