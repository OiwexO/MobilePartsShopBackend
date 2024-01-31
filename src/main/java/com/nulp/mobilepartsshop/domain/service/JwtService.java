package com.nulp.mobilepartsshop.domain.service;

import com.nulp.mobilepartsshop.domain.model.user.Role;
import com.nulp.mobilepartsshop.domain.model.user.User;
import com.nulp.mobilepartsshop.security.exception.ClaimNotFoundException;
import com.nulp.mobilepartsshop.security.exception.InvalidClaimValueException;
import com.nulp.mobilepartsshop.security.exception.InvalidClaimValueTypeException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.RequiredTypeException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

//https://youtu.be/BVdQ3iuovg0?t=5033&si=GCq3XaWBWP_YIrdS
@Service
public class JwtService {

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    @Value("${jwt.token.issuer}")
    private String TOKEN_ISSUER;
    private static final String KEY_SUBJECT_ROLE = "subject_role";
    private static final long TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24 hours

    public String generateToken(User user) {
        long currentTime = System.currentTimeMillis();
        Date issuedDate = new Date(currentTime);
        Date expirationDate = new Date(currentTime + TOKEN_EXPIRATION_TIME);
        return Jwts.builder()
                .claims()
                    .issuer(TOKEN_ISSUER)
                    .issuedAt(issuedDate)
                    .expiration(expirationDate)
                    .subject(user.getUsername())
                    .add(KEY_SUBJECT_ROLE, user.getRole())
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

    public Role extractSubjectRole(String jwtToken) throws ClaimNotFoundException, InvalidClaimValueTypeException, InvalidClaimValueException {
        try {
            String roleStr = extractClaim(jwtToken, claims -> claims.get(KEY_SUBJECT_ROLE, String.class));
            if (roleStr == null) {
                throw new ClaimNotFoundException("Claim '" + KEY_SUBJECT_ROLE + "' not found in the token");
            }
            try {
                return Role.valueOf(roleStr);
            } catch (IllegalArgumentException e) {
                throw new InvalidClaimValueException("Claim '" + KEY_SUBJECT_ROLE + "' has an invalid value: " + roleStr);
            }
        } catch (RequiredTypeException e) {
            throw new InvalidClaimValueTypeException("Claim '" + KEY_SUBJECT_ROLE + "' expected type is String", e);
        }
    }

    public boolean isTokenValid(String jwtToken, User user) {
        if (jwtToken == null || jwtToken.isEmpty()) {
            return false;
        }
        final boolean tokenIssuerValid = isTokenIssuerValid(jwtToken);
        final boolean tokenNotExpired = isTokenNotExpired(jwtToken);
        final boolean tokenSubjectValid = isTokenSubjectValid(jwtToken, user);
        final boolean tokenSubjectRoleValid = isTokenSubjectRoleValid(jwtToken, user);
        return tokenIssuerValid && tokenNotExpired && tokenSubjectValid && tokenSubjectRoleValid;
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

    private boolean isTokenSubjectRoleValid(String jwtToken, User user) {
        final Role actualRole = extractSubjectRole(jwtToken);
        return actualRole == user.getRole();
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
