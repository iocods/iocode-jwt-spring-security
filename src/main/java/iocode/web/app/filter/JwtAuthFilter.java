package iocode.web.app.filter;

import iocode.web.app.entity.User;
import iocode.web.app.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
          @NonNull HttpServletRequest request,
          @NonNull HttpServletResponse response,
          @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    SecurityContext context = SecurityContextHolder.getContext();
    String jwtToken = request.getHeader("Authorization");
    if(jwtToken == null || context.getAuthentication() != null){
      filterChain.doFilter(request, response);
      return;
    }
    jwtToken = jwtToken.substring(7);
    String username = jwtService.extractSubject(jwtToken);
    if(username != null && jwtService.isTokenValid(jwtToken)){
      User user = (User) userDetailsService.loadUserByUsername(username);
      var authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
      authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      context.setAuthentication(authToken);
    }
    filterChain.doFilter(request, response);
  }
}
