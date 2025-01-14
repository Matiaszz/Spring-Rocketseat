package dev.matias.todolist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Create a route (localhost:8080/example)
@RestController
@RequestMapping("/first-route")
public class MyFirstController {

    @GetMapping("/first-method")
    public String FistMessage() {
        return "Working";
    }
}