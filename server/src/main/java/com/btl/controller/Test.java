package com.btl.controller;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nontest")
public class Test {

    @GetMapping("/getTest")
    public ResponseEntity<?> getTest() {
        return ResponseEntity.ok("test success!");

    }
}
