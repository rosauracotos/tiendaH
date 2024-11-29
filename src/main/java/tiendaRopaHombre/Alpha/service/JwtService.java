package tiendaRopaHombre.Alpha.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {


    private final String SECRET_KEY = "lx0eO@9*.q*C7%eT,=01p/I7]YIG~kUaG0(Q7Kh,P}kFZPEu4£46?TB1{-1IIg2k";

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
    private final long EXPIRATION_TIME = 86400000;  // 24 horas en milisegundos


    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 10 horas
                .signWith(getSecretKey(), SignatureAlgorithm.HS512)
                .compact();
    }
}
