package co.tz.metro.utils;

import io.jsonwebtoken.*;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.List;
import java.util.function.Function;

@Component
public class JwtUtil {
    private final Key key;

    public JwtUtil() {
        // Create a secure key directly instead of using Keys.hmacShaKeyFor
        String secret = "my_super_secret_key_which_is_very_long";
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        // Create an HMAC-SHA256 key directly
        this.key = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    public String generateUserToken(String username, String role, List<String> permissions) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .claim("type", "USER")
                .claim("permissions", permissions)
                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 2)) // 5 minutes
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }
    // Extract username
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Generic claim extractor
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException | MalformedJwtException | SignatureException | IllegalArgumentException ex) {
            throw ex;
        }
    }
    // Check if token expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Validate token
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}