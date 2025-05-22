package com.example.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    private final DataService dataService;

    public IndexController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/user")
    public String user() {
        return dataService.getUser();
    }

    @GetMapping("/owner")
    public Account owner(String name) {
        return dataService.getOwner(name);
    }

    @GetMapping("/display")
    public String display() {
        return dataService.display();
    }
}
