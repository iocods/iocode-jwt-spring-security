package iocode.web.app.controller;

import iocode.web.app.dto.AuthUser;
import iocode.web.app.dto.RegUser;
import iocode.web.app.entity.User;
import iocode.web.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<User> register(@RequestBody RegUser regUser) {
    return ResponseEntity.ok(userService.register(regUser));
  }

  @PostMapping("/auth")
  public ResponseEntity<String> auth(@RequestBody AuthUser authUser) {
    return ResponseEntity.ok(userService.auth(authUser));
  }
}
