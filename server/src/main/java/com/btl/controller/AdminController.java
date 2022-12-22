package com.btl.controller;

import com.btl.dto.LoginDTO;
import com.btl.dto.SignUpDTO;
import com.btl.entity.Products;
import com.btl.entity.Role;
import com.btl.entity.User;
import com.btl.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:63343")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/test")
    public ResponseEntity<?> getTest() {
        return ResponseEntity.ok("test succesfully!!");
    }

    @GetMapping("/getLine")
    public ResponseEntity<?> getLineProducts() {
        return null;
    }

    @PostMapping("/changeRole")
    public ResponseEntity<?> changRole(@RequestBody LoginDTO loginDTO, @RequestParam int targetRoleId) {
        User user = userRepo.findByUsername(loginDTO.getUsername()).get();

        String tgRole = "";
        if (targetRoleId == 1) {
            tgRole = "ROLE_FACTORY";
        } else if (targetRoleId == 2) {
            tgRole = "ROLE_DEALER";
        } else if (targetRoleId == 3) {
            tgRole = "ROLE_SERVICE";
        } else if (targetRoleId == 4) {
            tgRole = "ROLE_ADMIN";
        } else {
            tgRole = "ROLE_DEALER";
        }
        user.setRoles(roleRepo.findByName(tgRole).stream().collect(Collectors.toSet()));
        userRepo.save(user);
        return ResponseEntity.ok("CHANGE SUCCESSFULLY!!");
    }

    @PostMapping("/addAccount")
    public ResponseEntity<?> addAccount(@RequestParam String type, @RequestBody SignUpDTO signUpDTO) {
        String typeRole = "";
        if (type.equals("admin")) {
            typeRole = "ROLE_ADMIN";
        } else if (type.equals("dealer")) {
            typeRole = "ROLE_DEALER";
        } else if (type.equals("factory")) {
            typeRole = "ROLE_FACTORY";
        } else if (type.equals("service")) {
            typeRole = "ROLE_SERVICE";
        } else {
            return ResponseEntity.ok("NOT_SUPPORT");
        }

        if (userRepo.existsByUsername(signUpDTO.getUsername())) {
            return new ResponseEntity<>("ALREADY_TAKEN", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setName(signUpDTO.getName());
        user.setUsername(signUpDTO.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));

        Role roles = roleRepo.findByName(typeRole).get(0);
        user.setRoles(Collections.singleton(roles));

        userRepo.save(user);

        return ResponseEntity.ok("SUCCESS");
    }

    @GetMapping("/listUser")
    @ResponseBody
    public List<User> getListUser() {
        List<User> users = new ArrayList<>();
        Iterable<User> allUser = userRepo.findAll();
        for (User user : allUser) {
            user.setPassword("hided");
            users.add(user);
        }
        return users;
    }

    @PostMapping("/changePass")
    public ResponseEntity<?> changePass(@RequestParam String username, @RequestBody String newPass) {
        User user = userRepo.findByUsername(username).get();
        if (user == null) {
            return new ResponseEntity<>("USER_NOT_FOUND", HttpStatus.BAD_REQUEST);
        }

        user.setPassword(passwordEncoder.encode(newPass));
        userRepo.save(user);
        return ResponseEntity.ok("SUCCESS");
    }
}