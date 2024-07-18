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
        .csrf(csrf -> csrf.disable()) // Desactivar CSRF para simplificar la configuración (no recomendado para producción)
        .httpBasic(Customizer.withDefaults()) // Configuración básica de autenticación HTTP
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Uso de sesiones sin estado
        .authorizeHttpRequests(http -> {
            // Configurar endpoints públicos
            http.requestMatchers(HttpMethod.GET, "/users/", "/users").permitAll();
            http.requestMatchers(HttpMethod.POST, "/users/exists", "/users/exists/").permitAll();
            http.requestMatchers(HttpMethod.POST, "/users/add", "/users/add/").permitAll();
            http.requestMatchers(HttpMethod.PUT, "/users/update/{id}").permitAll();
            http.requestMatchers(HttpMethod.DELETE, "/users/delete/{id}").permitAll();

            http.requestMatchers(HttpMethod.GET, "/products/", "/products").permitAll();

            http.requestMatchers(HttpMethod.GET, "/products/limit").permitAll();
            
            
            http.requestMatchers(HttpMethod.GET, "/products/filterByCategory").permitAll();

            http.requestMatchers(HttpMethod.GET, "/products/filterByCategoryLimit").permitAll();

            http.requestMatchers(HttpMethod.GET, "/products/categories/").permitAll();

            http.requestMatchers(HttpMethod.GET, "/products/search").permitAll();

            http.requestMatchers(HttpMethod.GET, "/products/category").permitAll();

            // Configurar endpoints privados
            // Productos
           
            http.requestMatchers(HttpMethod.GET, "/products/{id}").hasAnyRole("ADMIN");
            http.requestMatchers(HttpMethod.GET, "/products/filterByCategory").hasAnyRole("ADMIN");
            http.requestMatchers(HttpMethod.GET, "/products/filterByPrice").hasAnyRole("ADMIN");
            http.requestMatchers(HttpMethod.POST, "/products/add", "/products/add/").hasAnyRole("ADMIN");
            http.requestMatchers(HttpMethod.PUT, "/products/update/{id}").hasAnyRole("ADMIN");
            http.requestMatchers(HttpMethod.DELETE, "/products/delete/{id}").hasAnyRole("ADMIN");

            // Usuarios
            http.requestMatchers(HttpMethod.GET, "/users/{id}").hasAnyRole("ADMIN");

            // Denegar el resto
            http.anyRequest().denyAll();
        })
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
