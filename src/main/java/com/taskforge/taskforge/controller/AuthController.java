package com.taskforge.taskforge.controller;

import com.taskforge.taskforge.dto.AuthResponse;
import com.taskforge.taskforge.dto.LoginRequest;
import com.taskforge.taskforge.entity.User;
import com.taskforge.taskforge.repository.UserRepository;
import com.taskforge.taskforge.service.CustomUserDetailsService;
import com.taskforge.taskforge.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService, JwtUtil jwtUtil, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest loginRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Incorrect email or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);
        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();

        return new AuthResponse(jwt, user.getRole(), user.getId());
    }
}
