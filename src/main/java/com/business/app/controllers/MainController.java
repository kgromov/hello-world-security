package com.business.app.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/business")
public class MainController {

    @GetMapping
    public String getStartPage(Model model)
    {
        log.info("Getting main business page");
        return "businessPage";
    }

}
