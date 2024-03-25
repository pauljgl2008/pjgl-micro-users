package com.demo.pjglmicrousers.config;

import com.demo.pjglmicrousers.auth.JWTTokenGeneratorFilter;
import com.demo.pjglmicrousers.auth.JWTTokenValidatorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(x->x.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(x -> x.disable())
                .cors(cors->cors.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/users").authenticated()
                        .requestMatchers("/login").permitAll()
                        .anyRequest().permitAll()
                );
                //.addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                //.addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                //.httpBasic(Customizer.withDefaults())
                //.formLogin(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user1 = User.withUsername("user1")
                .password(passwordEncoder().encode("user1Pass"))
                .roles("USER")
                .build();
        UserDetails user2 = User.withUsername("user2")
                .password(passwordEncoder().encode("user2Pass"))
                .roles("USER")
                .build();
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user1, user2, admin);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}