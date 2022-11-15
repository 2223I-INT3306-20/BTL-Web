package com.btl.controller;

import com.btl.database.UsersData;
import com.btl.entity.Account;
import com.btl.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Login {

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> loginCheck(@RequestParam Account account) {
        UsersData usersData = new UsersData();
        User res = usersData.getUserById(account);
        if (res == null) {
            return ResponseEntity.ok("Sai tên tài khoản các bạn ưi!!");
        } else {
            return ResponseEntity.ok(res);
        }

    }

}
