package com.msa.user_service.config;


import com.msa.user_service.filter.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
        throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf((csrf) -> csrf
                .disable()
            )

            .headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.disable())
            )

            .authorizeHttpRequests((auth) -> auth
                    .requestMatchers("/h2-console/**", "/users/**").permitAll()
                    .anyRequest().permitAll()
//                .anyRequest().authenticated()
            )

            .addFilterAt(
                new LoginFilter(authenticationManager(authenticationConfiguration)),
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}
