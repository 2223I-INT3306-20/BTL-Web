package com.btl.controller;

import com.btl.entity.Role;
import com.btl.entity.Stored;
import com.btl.entity.User;
import com.btl.dto.LoginDTO;
import com.btl.dto.SignUpDTO;
import com.btl.repo.LocationRepo;
import com.btl.repo.RoleRepo;
import com.btl.repo.UserRepo;
import com.btl.response.AuthResponse;
import com.btl.service.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@CrossOrigin(origins = {"http://localhost:63342", "http://127.0.0.1:5500"})
@Transactional
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    LocationRepo locationRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = userRepo.findByUsername(loginDTO.getUsername()).get();
            String accessToken = tokenAuthenticationService.generateTokenLogin(user);
            AuthResponse response = new AuthResponse(user.getUsername(), user.getName() , accessToken, user.getFirstRole());
            return ResponseEntity.ok().body(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    /*Logout xử lý bên client*/

   /* @PostMapping("/logout")
    public ResponseEntity<?> logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return ResponseEntity.ok("LOG_OUT");
    }*/

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDTO signUpDto) {
        //type đầu vào sẽ nhận type: admin, dealer, service, factory.
        long id = signUpDto.getLocationId();
        Stored stored = locationRepo.findById(id).get();
        String type = stored.getLocationType();
        String typeRole = "";
        if (type.equals("ADMIN")) {
            typeRole = "ROLE_ADMIN";
        } else if (type.equals("DEALER")) {
            typeRole = "ROLE_DEALER";
        } else if (type.equals("FACTORY")) {
            typeRole = "ROLE_FACTORY";
        } else if (type.equals("SERVICE")) {
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

        user.setStoreds(Collections.singleton(stored));

        userRepo.save(user);

        return ResponseEntity.ok("SUCCESSFULLY");

    }

    @GetMapping("/deleteAcc")
    public ResponseEntity<?> deleteAccount(@RequestParam("username") String username) {
        try {
            long id =  userRepo.findByUsername(username).get().getId();

            userRepo.deleteUserByUsername(username);
            return ResponseEntity.ok().body("SUCCESS");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
