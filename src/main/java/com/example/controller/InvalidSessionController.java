package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InvalidSessionController {

    @GetMapping("/invalidSession")
    public String getInvalidSessionDetails() {

        return "Here is the Invalid Session page";
    }
}
