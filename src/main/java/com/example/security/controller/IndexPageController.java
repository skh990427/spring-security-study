package com.example.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class IndexPageController {

    @GetMapping("/loginPage")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }

    @GetMapping("/failed")
    public String failedPage() {
        return "failed";
    }
}
