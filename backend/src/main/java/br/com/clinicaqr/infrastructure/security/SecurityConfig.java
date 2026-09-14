package br.com.clinicaqr.infrastructure.security;

import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.*;
import java.util.List;

@Configuration
public class SecurityConfig {
    @Bean PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder(); }
    @Bean SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationFilter filter) throws Exception {
        return http.csrf(c->c.disable()).cors(c->c.configurationSource(cors()))
                .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(a->a.requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/public/*").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll().anyRequest().authenticated())
                .addFilterBefore(filter,UsernamePasswordAuthenticationFilter.class).build();
    }
    @Bean CorsConfigurationSource cors(){
        CorsConfiguration c=new CorsConfiguration(); c.setAllowedOrigins(List.of("http://localhost:5173"));
        c.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS")); c.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource(); source.registerCorsConfiguration("/**",c); return source;
    }
}