package com.store.store.Auth;

import org.springframework.stereotype.Service;

import com.store.store.Jwt.JwtService;
import com.store.store.User.Role;
import com.store.store.User.User;
import com.store.store.User.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthResponse login(LoginRequest request) {
        return null;
    }

    public AuthResponse register(RegisterRequest request) {
          User user = User.builder()
        .userName(request.getUserName())
        .email(request.getEmail())
        .password(request.getPassword())
        .role(Role.USER)
        .build();

        userRepository.save(user);

        return AuthResponse.builder()
            .token(jwtService.getToken(user))
            .build();

    }
    
}
