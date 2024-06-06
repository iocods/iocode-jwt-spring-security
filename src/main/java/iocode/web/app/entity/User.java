package iocode.web.app.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "_users")
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String uId;
  private String username;
  private String password;
  private String firstname;
  private String lastname;
  private List<String> roles;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Set<SimpleGrantedAuthority> authorities = new HashSet<>();
    roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
    return authorities;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
