package com.yankong.login.global.config;

import com.yankong.login.global.security.CustomAccessDeniedHandler;
import com.yankong.login.global.security.CustomUserDetailsService;
import com.yankong.login.global.security.JwtAuthFilter;
import com.yankong.login.global.security.jwt.JwtUtil;
import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    public SecurityConfig(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService, CustomAccessDeniedHandler accessDeniedHandler) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .headers((headerConfig) -> headerConfig.frameOptions(FrameOptionsConfig::disable))
            .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                .requestMatchers("/**").permitAll()
                .anyRequest().authenticated())
            .exceptionHandling((exceptionHandling) -> exceptionHandling.accessDeniedHandler(accessDeniedHandler))
            .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS))
            .userDetailsService(userDetailsService)
            .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter(jwtUtil, userDetailsService);
    }
}