package com.nulp.mobilepartsshop.core.service;

import com.nulp.mobilepartsshop.core.model.user.User;
import com.nulp.mobilepartsshop.exception.security.jwt.ClaimNotFoundException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    @Value("${jwt.token.issuer}")
    private String TOKEN_ISSUER;

    private static final long TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24 hours

    public String generateToken(final User user) {
        long currentTime = System.currentTimeMillis();
        Date issuedDate = new Date(currentTime);
        Date expirationDate = new Date(currentTime + TOKEN_EXPIRATION_TIME);
        return Jwts.builder()
                .claims()
                    .issuer(TOKEN_ISSUER)
                    .issuedAt(issuedDate)
                    .expiration(expirationDate)
                    .subject(user.getUsername())
                .and()
                .signWith(getSecretKey(), Jwts.SIG.HS256)
                .compact();
    }

    public String extractUsername(String jwtToken) throws ClaimNotFoundException {
        String username = extractClaim(jwtToken, Claims::getSubject);
        if (username == null || username.isEmpty()) {
            throw new ClaimNotFoundException("Claim 'sub' (subject) not found or is empty in the token");
        }
        return username;
    }

    public boolean isTokenValid(String jwtToken, User user) {
        if (jwtToken == null || jwtToken.isEmpty()) {
            return false;
        }
        final boolean tokenIssuerValid = isTokenIssuerValid(jwtToken);
        final boolean tokenNotExpired = isTokenNotExpired(jwtToken);
        final boolean tokenSubjectValid = isTokenSubjectValid(jwtToken, user);
        return tokenIssuerValid && tokenNotExpired && tokenSubjectValid;
    }

    private boolean isTokenIssuerValid(String jwtToken) {
        final String issuer = extractClaim(jwtToken, Claims::getIssuer);
        return issuer.equals(TOKEN_ISSUER);
    }

    private boolean isTokenNotExpired(String jwtToken) {
        final Date currentDate = new Date();
        final Date expirationDate = extractClaim(jwtToken, Claims::getExpiration);
        return currentDate.before(expirationDate);
    }

    private boolean isTokenSubjectValid(String jwtToken, User user) {
        final String actualSubject = extractUsername(jwtToken);
        return actualSubject.equals(user.getUsername());
    }

    private Claims extractAllClaims(String jwtToken) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }

    private <T> T extractClaim(String jwtToken, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimResolver.apply(claims);
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
