package com.btl.controller;

import com.btl.entity.Role;
import com.btl.entity.User;
import com.btl.dto.LoginDTO;
import com.btl.dto.SignUpDTO;
import com.btl.repo.RoleRepo;
import com.btl.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:63343")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Autowired
//    JwtUtils jwtUtils;

    @PostMapping("/login")
    @ResponseBody
    public List<Role> authenticateUser(@RequestBody LoginDTO loginDto) {
        User user = userRepo.findByUsername(loginDto.getUsername()).get();
        if (user == null) {
            return null;
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword()));
        //System.out.println(user.getFirstRole());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return roleRepo.findByName(user.getFirstRole());
    }

    @PostMapping("/logout") //chỗ này không cần Response, xử lý logout luôn, không return
    public ResponseEntity<?> logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return ResponseEntity.ok("LOG_OUT");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestParam String type, @RequestBody SignUpDTO signUpDto) {
        //type đầu vào sẽ nhận type: admin, dealer, service, factory.
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

        System.out.println("Created " + typeRole);

        if (userRepo.existsByUsername(signUpDto.getUsername())) {
            return new ResponseEntity<>("ALREADY_TAKEN", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles = roleRepo.findByName(typeRole).get(0);
        user.setRoles(Collections.singleton(roles));

        userRepo.save(user);

        return ResponseEntity.ok("SUCCESSFULLY");

    }


}
