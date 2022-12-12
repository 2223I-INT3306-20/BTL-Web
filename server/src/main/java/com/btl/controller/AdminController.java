package com.btl.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/test")
    public ResponseEntity<?> getTest() {
        return ResponseEntity.ok("test succesfully!!");
    }

    @GetMapping("/getLine")
    public ResponseEntity<?> getLineProducts() {
        return null;
    }
}
