package com.business.app.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationDataValidationTest {
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void createAnotherUser() throws Exception {
        this.mockMvc.perform(post("/registration")
                .param("username", "jonh")
                .param("password", "quewrty")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login"));
    }

    @Test
    public void emptyUserName() throws Exception {
        this.mockMvc.perform(post("/registration")
                .param("username", "")
                .param("password", "123")
                .with(csrf()))
                .andExpect(view().name("registrationForm"))
//                .andExpect(content().string(containsString("Please correct errors below")))
        ;
    }

    @Test
    public void emptyPassword() throws Exception {
        this.mockMvc.perform(post("/registration")
                .param("username", "jonh")
                .param("password", "")
                .with(csrf()))
                .andExpect(view().name("registrationForm"))
//                .andExpect(content().string(containsString("Please correct errors below")))
        ;
    }

    @Test
    public void longUserName() throws Exception {
        this.mockMvc.perform(post("/registration")
                .param("username", "Johann Ludwig Heinrich Julius Schliemann")
                .param("password", "qwerty")
                .with(csrf()))
                .andExpect(view().name("registrationForm"))
//                .andExpect(content().string(containsString("Please correct errors below")))
        ;
    }
}