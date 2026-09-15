package com.bookstore.dto;
import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor
public class AuthRequest {
    @Data @AllArgsConstructor @NoArgsConstructor
    public static class LoginRequest {
        private String username;
        private String password;
    }
    @Data @AllArgsConstructor @NoArgsConstructor
    public static class RegisterRequest {
        private String username;
        private String password;
        private String email;
    }
    @Data @AllArgsConstructor @NoArgsConstructor
    public static class JwtResponse {
        private String token;
        private String username;
    }
}