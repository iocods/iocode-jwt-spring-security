package iocode.web.app.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegUser {
  private String username;
  private String password;
  private String firstname;
  private String lastname;
}
