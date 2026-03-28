package com.nishith.demo.service;

import com.nishith.demo.model.Users;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
@Service
public class JwtService {
    public String tokengeneration (Users users)
    {
        return Jwts.builder()
                .subject(users.getUsername())
                .claim("roles",users.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*60*11))
                .signWith(secretkey())
                .compact();
    }
  private String skey="akfjndkfsdkfohgfytfrtfytcxtfutvcfbn";
    private SecretKey secretkey()
    {
        byte [] b=skey.getBytes();
        return Keys.hmacShaKeyFor(b);
    }

    public String getusername(String token) {
   return Jwts.parser()
           .verifyWith(secretkey())
           .build()
           .parseSignedClaims(token)
           .getPayload()
           .getSubject();
    }

    public boolean istokenvalid(String token, String username) {
      return getusername(token).equals(username) && !istokenexpired(token);
    }
    public boolean istokenexpired(String token)
    {
   Date d=Jwts.parser()
           .verifyWith(secretkey())
           .build()
           .parseSignedClaims(token)
           .getPayload()
           .getExpiration();
    return d.before(new Date());
    }
}
