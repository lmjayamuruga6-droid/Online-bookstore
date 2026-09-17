
package com.bookstore.service;

import com.bookstore.entity.User;
import com.bookstore.repository.UserRepository;
import com.bookstore.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtTokenProvider jwtProvider;

    public String register(String username, String password, String email) {
        log.info("Register attempt username={}", username);
        if (userRepo.findByUsername(username).isPresent()) throw new IllegalStateException("User exists");
        User user = User.builder().username(username).password(encoder.encode(password)).email(email).build();
        userRepo.save(user);
        log.info("User registered username={}", username);
        return jwtProvider.generateToken(username);
    }

    public String login(String username, String password) {
        log.info("Login attempt username={}", username);
        User user = userRepo.findByUsername(username).orElseThrow(() -> new IllegalStateException("Invalid credentials"));
        if (!encoder.matches(password, user.getPassword())) throw new IllegalStateException("Invalid credentials");
        String token = jwtProvider.generateToken(username);
        log.info("Login success username={}", username);
        return token;
    }
}
