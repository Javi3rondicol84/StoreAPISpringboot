package com.store.store.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.store.store.Entities.Users.UserDetailsServiceImpl;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public SecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl) {
    this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    //all filters modified HttpSecurity Object
   
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity
    )  throws Exception {
        return httpSecurity
        .csrf(crsf -> crsf.disable()) 
        .httpBasic(Customizer.withDefaults())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
       /*  .authorizeHttpRequests(http -> {
            //configurar endpoints publicos
            http.requestMatchers(HttpMethod.GET, "auth/hello").permitAll();

            http.requestMatchers(HttpMethod.GET, "/users/").permitAll();

            http.requestMatchers(HttpMethod.POST, "/users/add").permitAll();

            //configurar endpoints privados
            http.requestMatchers(HttpMethod.GET, "/auth/hello-secured").hasAnyRole("USER", "ADMIN");

            //denegar el resto
          // http.anyRequest().denyAll();
        })*/
        .build();
    }

   

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(this.passwordEncoder());
        provider.setUserDetailsService(userDetailsServiceImpl);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



}
