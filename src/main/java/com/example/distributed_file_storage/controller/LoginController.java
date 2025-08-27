package com.example.distributed_file_storage.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.distributed_file_storage.dto.LoginRequest;
import com.example.distributed_file_storage.dto.RegisterRequest;
import com.example.distributed_file_storage.model.User;
import com.example.distributed_file_storage.repository.UserRepository;

@RestController
public class LoginController {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    final AuthenticationManager authenticationManager;
    
    public LoginController(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest){
        try {
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
                )
            );
            if(auth.isAuthenticated()){
                return "login successful";
            }
        } catch (Exception e) {
            return "invalid credentials";
        }
        return "login failed";
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest){

        if(userRepository.findByusername(registerRequest.getUsername()).isPresent()){
            return ResponseEntity.badRequest().body("username exixts");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        userRepository.save(user);

        return ResponseEntity.ok("User Created");
    }
}
