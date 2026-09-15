package com.bookstore.service;
import com.bookstore.dto.AuthRequest.*;
import com.bookstore.model.User;
import com.bookstore.repository.UserRepository;
import com.bookstore.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired private UserRepository userRepo;
    @Autowired private PasswordEncoder encoder;
    @Autowired private JwtUtils jwtUtils;

    public JwtResponse register(RegisterRequest req) {
        if(userRepo.existsByUsername(req.getUsername())) throw new IllegalArgumentException("Username already exists");
        User user = User.builder()
            .username(req.getUsername())
            .password(encoder.encode(req.getPassword()))
            .email(req.getEmail()).build();
        userRepo.save(user);
        String token = jwtUtils.generateToken(req.getUsername());
        return new JwtResponse(token, req.getUsername());
    }

    public JwtResponse login(LoginRequest req) {
        User user = userRepo.findByUsername(req.getUsername())
            .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
        if(!encoder.matches(req.getPassword(), user.getPassword())) throw new IllegalArgumentException("Invalid credentials");
        String token = jwtUtils.generateToken(user.getUsername());
        return new JwtResponse(token, user.getUsername());
    }
}