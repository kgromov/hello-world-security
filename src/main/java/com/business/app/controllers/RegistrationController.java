package com.business.app.controllers;

import com.business.app.domain.User;
import com.business.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
@RequestMapping("/registration")
@Slf4j
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @GetMapping
    public String registration() {
        return "registrationForm";
    }

    @PostMapping
    public String addUser(@Valid User user, BindingResult bindingResult, Model model) {
        log.info("Navigate to registration page for user {}", user.getUsername());

        if (bindingResult.hasErrors()) {
            log.info("Registration form contains errors:");
            bindingResult.getAllErrors().forEach(error -> log.warn("{}", error));
            return "registrationForm";
        }
        if (!userService.addUser(user)) {
            model.addAttribute("userName", user.getUsername());
            return "registrationForm";
        }
        return "redirect:/login";
    }
}
