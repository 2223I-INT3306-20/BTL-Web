package com.btl.controller;

import com.btl.database.UsersData;
import com.btl.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class Users {

    @GetMapping("")
    public ResponseEntity<?> getAllUser() {
        UsersData usersData = new UsersData();
        usersData.getAllUser();
        return ResponseEntity.ok(usersData.getUsers());
    }

    @GetMapping("/account")
    public ResponseEntity<?> getUserByID(@RequestParam(name = "id") String id) {
         UsersData usersData = new UsersData();
         usersData.getAllUser();
         List<User> users = usersData.getUsers();
         for (int i = 0; i < users.size(); i++) {
             if (users.get(i).getId().equals(id)) {
                 return ResponseEntity.ok(users.get(i));
             }
         }
         return null;
    }

    
}
