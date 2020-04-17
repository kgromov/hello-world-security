package com.business.app.controllers;

import com.business.app.domain.User;
import com.business.app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping({"/"})
public class IndexController {
    private final UserRepository repository;

    @GetMapping
    public String getRegistrationForm( Model model)
    {
        log.info("Welcome to registration page!");
       /* repository.save(new User("Dummy", "qwerty"));
        Iterable<User> all = repository.findAll();
        log.info("Save users: {}", all);*/
        return "registrationForm";
    }

    @PostMapping
    public String createUser(@ModelAttribute User user, Model model)
    {
        Optional<User> authenticatedUser = repository.findByUserName(user.getUserName());
        if (authenticatedUser.isPresent())
        {
            log.info("User {} is already exists!", user.getUserName());
            model.addAttribute("param.error", String.format("User %s exists!", user.getUserName()));
            model.addAttribute("userName", user.getUserName());
            return "registrationForm";
        }
        log.info("Save user {} in DB", user.getUserName());
        repository.save(user);
        return "redirect:/login";
    }

}
