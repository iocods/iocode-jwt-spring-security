package iocode.web.app.config;

import iocode.web.app.entity.Role;
import iocode.web.app.entity.User;
import iocode.web.app.entity.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AppConfig implements CommandLineRunner {

  private final UserRepository userRepository;

  @Bean
  public UserDetailsService userDetailsService(){
    return username -> userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationProvider authenticationProvider(){
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(passwordEncoder());
    daoAuthenticationProvider.setUserDetailsService(userDetailsService());
    return daoAuthenticationProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Override
  public void run(String... args) throws Exception {
    User user = User.builder()
          .firstname("Iocode Firstname")
          .lastname("Iocode Lastname")
          .username("Iocode@gmail.com")
          .password(passwordEncoder().encode("iocode"))
          .roles(List.of(Role.ADMIN.name(), Role.USER.name()))
          .build();
    userRepository.save(user);
  }
}
