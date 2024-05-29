package com.example.WebGoatJava.JWT;

import com.example.WebGoatJava.Entity.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    private final static Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    @Value("${jwt.expiration}")
    private int expiration;

    static SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public String generateToken(Usuario authentication){

        return Jwts.builder()
                .claim("username", authentication.getNombreUsuario()) // Agrega el nombre de usuario como claim
                .claim("password", authentication.getPassword()) // Agrega la contraseña como claim (esto puede ser peligroso, asegúrate de tener buenas prácticas de seguridad)
                .claim("rol", authentication.getRol())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 1000))
                .signWith(key)  //Se deshabilita para que no verifique la firma
                .compact();
    }

    public static String extractRol(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        String rolActual =  (String)claims.get("rol");
        System.out.println(rolActual);
        return rolActual;
    }
    public static String extractUser(String token){
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        String userActual = (String)claims.get("username");
        return userActual;
    }
    public boolean validateToken(String token) {
        try{
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            logger.info("token valido: " + token);
            return true;
        }catch (MalformedJwtException e){
            logger.error("token mal formado " + e);
        }catch (UnsupportedJwtException e){
            logger.error("token no soportado " + e);
        }catch (ExpiredJwtException e){
            logger.error("token expirado "+ e);
        }catch (IllegalArgumentException e){
            logger.error("token vacío " + e);
        }catch (SignatureException e){
            logger.error("Fallo en la firma " + e);
        }
        return false;
    }
    public static Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }
}
