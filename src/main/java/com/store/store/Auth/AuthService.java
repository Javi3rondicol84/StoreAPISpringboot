package com.store.store.Auth;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;

import com.store.store.Jwt.JwtService;
import com.store.store.User.Role;
import com.store.store.User.User;
import com.store.store.User.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userRepository.findUserEntityByUserName(request.getUsername()).orElseThrow();
        if(user != null) {
            System.out.println("xd");
        }
        else {
            System.out.println("sssssss");
        }
        String token = jwtService.getToken(user);
        System.out.println("login"+request.getPassword());
        System.out.println("login"+user.getPassword());
        return AuthResponse.builder()
              .token(token)
              .build();
    }

    public AuthResponse register(RegisterRequest request) {
          User user = User.builder()
        .userName(request.getUserName())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(Role.USER)
        .build();

        System.out.println(request.getPassword());
        userRepository.save(user);

        return AuthResponse.builder()
            .token(jwtService.getToken(user))
            .build();

    }
    
}