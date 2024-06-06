package iocode.web.app.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

  @Value("${jwt.secret}")
  private String secret;

  private Key generateSigningKey() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
  }

  public String generateJwtToken(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + (1000 * 24 *24)))
        .signWith(generateSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public Claims extractClaims(String jwtToken) {
    return Jwts.parserBuilder()
        .setSigningKey(generateSigningKey())
        .build()
        .parseClaimsJws(jwtToken)
        .getBody();
  }

  public String extractSubject(String jwtToken) {
    return extractClaims(jwtToken).getSubject();
  }
  public Date extractExpiration(String jwtToken) {
    return extractClaims(jwtToken).getExpiration();
  }

  public boolean isTokenValid(String jwtToken){
    return new Date().before(extractExpiration(jwtToken));
  }
}
