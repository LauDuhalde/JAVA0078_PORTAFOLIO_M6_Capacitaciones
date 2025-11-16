package cl.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    // Clave secreta para HS256
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generarToken(String username, List<String> roles) {

        return Jwts.builder()
                .setClaims(Map.of("roles", roles))
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) // 1 hora
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extraerUsername(String token) {
        return extraerClaim(token, Claims::getSubject);
    }

    public List<String> extraerRoles(String token) {
        return extraerClaim(token, claims -> claims.get("roles", List.class));
    }

    public Date extraerFechaExpiracion(String token) {
        return extraerClaim(token, Claims::getExpiration);
    }

    public <T> T extraerClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claimsResolver.apply(claims);
    }

    public boolean validarToken(String token, String username) {
        final String usuarioToken = extraerUsername(token);
        return (usuarioToken.equals(username) && !estaExpirado(token));
    }

    public boolean estaExpirado(String token) {
        return extraerFechaExpiracion(token).before(new Date());
    }
}
