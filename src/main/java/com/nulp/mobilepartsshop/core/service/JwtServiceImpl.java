package com.nulp.mobilepartsshop.core.service;

import com.nulp.mobilepartsshop.security.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    @Value("${jwt.token.issuer}")
    private String TOKEN_ISSUER;

    @Value("${jwt.token.expirationTimeMillis}")
    private long TOKEN_EXPIRATION_TIME;

    @Override
    public String generateToken(final UserDetails userDetails) {
        long currentTime = System.currentTimeMillis();
        Date issuedDate = new Date(currentTime);
        Date expirationDate = new Date(currentTime + TOKEN_EXPIRATION_TIME);
        return Jwts.builder()
                .claims()
                .issuer(TOKEN_ISSUER)
                .issuedAt(issuedDate)
                .expiration(expirationDate)
                .subject(userDetails.getUsername())
                .and()
                .signWith(getSecretKey(), Jwts.SIG.HS256)
                .compact();
    }

    @Override
    public boolean isTokenValid(final String jwtToken, final UserDetails userDetails) {
        if (jwtToken == null || jwtToken.isEmpty()) {
            return false;
        }
        final Claims claims = extractAllClaims(jwtToken);
        final boolean tokenIssuerValid = isTokenIssuerValid(claims);
        final boolean tokenNotExpired = isTokenNotExpired(claims);
        final boolean tokenSubjectValid = isTokenSubjectValid(claims, userDetails.getUsername());
        return tokenIssuerValid && tokenNotExpired && tokenSubjectValid;
    }

    @Override
    @Nullable
    public String extractUsername(final String jwtToken) {
        final Claims claims = extractAllClaims(jwtToken);
        return claims.getSubject();
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private boolean isTokenIssuerValid(final Claims claims) {
        final String issuer = claims.getIssuer();
        return issuer != null && issuer.equals(TOKEN_ISSUER);
    }

    private boolean isTokenNotExpired(final Claims claims) {
        final Date currentDate = new Date();
        final Date expirationDate = claims.getExpiration();
        return expirationDate != null && currentDate.before(expirationDate);
    }

    private boolean isTokenSubjectValid(final Claims claims, final String expectedUsername) {
        final String actualSubject = claims.getSubject();
        return actualSubject != null && actualSubject.equals(expectedUsername);
    }

    private Claims extractAllClaims(final String jwtToken) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload();
    }

}
