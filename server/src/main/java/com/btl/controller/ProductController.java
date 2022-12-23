package com.btl.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@CrossOrigin(origins = "http://localhost:63344")
@RequestMapping("/api/products")
public class ProductController {

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts() {
        return null;
    }





}
