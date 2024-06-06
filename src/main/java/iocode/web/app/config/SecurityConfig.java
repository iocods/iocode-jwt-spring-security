package iocode.web.app.config;

import iocode.web.app.filter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.h2.engine.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
  private final AuthenticationProvider authenticationProvider;
  private final JwtAuthFilter jwtAuthFilter;
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
    http.csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(httpRequest -> {
              httpRequest.requestMatchers("/register", "/auth")
                      .permitAll();
              httpRequest.requestMatchers(HttpMethod.POST)
                      .hasAnyAuthority("ADMIN")
                      .anyRequest()
                      .authenticated();
            }).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .authenticationProvider(authenticationProvider);
    return http.build();
  }
}
