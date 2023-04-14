package com.embeded.ldap;

import com.embeded.ldap.constants.UtilityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public final class JwtUtils {
    @Value("${jwt.secret.key}")
    public String secretKey;


    public String extractUsername(String token) {
        return (String) extractClaim(token, claims -> claims.get(UtilityConstants.USERNAME));
    }


    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(UtilityConstants.USERNAME, username);
        return createToken(claims);

    }

    public String createToken(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).setIssuedAt(new Date((System.currentTimeMillis())))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    public Boolean isValid(String token) {
        final String username = extractUsername(token);
        return username != null && !isTokenExpired(token);
    }
}
