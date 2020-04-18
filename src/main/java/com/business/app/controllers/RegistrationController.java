package com.business.app.controllers;

import com.business.app.domain.User;
import com.business.app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;


@Controller
@RequestMapping("/registration")
@Slf4j
@RequiredArgsConstructor
public class RegistrationController {
    private final UserRepository repository;

    @GetMapping
    public String registration() {
        return "registrationForm";
    }

    @PostMapping
    public String addUser(User user, Model model) {
        log.info("Navigate to registration page for user = {}", user.getUsername());
        Optional<User> userFromDb = repository.findByUsername(user.getUsername());

        if (userFromDb.isPresent())
        {
            log.info("User {} already exists!", user.getUsername());
            model.addAttribute("userName", user.getUsername());
            return "registrationForm";
        }
        log.info("Create new user");
        repository.save(user);
        return "redirect:/login";
    }
}
