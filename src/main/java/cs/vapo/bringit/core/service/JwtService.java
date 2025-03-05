package cs.vapo.bringit.core.service;

import cs.vapo.bringit.core.config.ApplicationConfiguration;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final int HOUR_IN_MILLIS = 1000 * 60 * 60;

    private final ApplicationConfiguration configuration;

    @Autowired
    public JwtService(final ApplicationConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * Generates a JWT for the given {@link UserDetails}
     * @param claims claims to add to the token payload
     * @param userDetails the {@link UserDetails} for the current user
     * @return the generated JWT
     */
    public String generateToken(final Map<String, Object> claims, final UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + HOUR_IN_MILLIS))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Evaluates if a token is valid by checking the username field and if the token has expired
     * @param token the current token
     * @param userDetails the current user's details
     * @return true if still a valid token
     */
    public boolean isValid(final String token, final UserDetails userDetails) {
        final String username = retrieveUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Evaluates if a token has exceeded its expiration date
     * @param token the current token
     * @return true if expired
     */
    private boolean isTokenExpired(final String token) {
        return retrieveExpirationDate(token).before(new Date());
    }

    /**
     * Retrieves the expiration date from the token's claims
     * @param token the current token
     * @return the expiration date stored in the token's claims
     */
    private Date retrieveExpirationDate(final String token) {
        return retrieveClaim(token, Claims::getExpiration);
    }

    /**
     * Retrieves the username from the token's claims
     * @param token the current token
     * @return the subject from the token's claims
     */
    public String retrieveUsername(final String token) {
        return retrieveClaim(token, Claims::getSubject);
    }

    /**
     * Helper method for retrieving a specific claim from a given token
     * @param token the current token
     * @param resolver the resolver method to apply to the Claims object
     * @return whichever claim was requested, null if non-existent
     */
    public <T> T retrieveClaim(final String token, final Function<Claims, T> resolver) {
        final Claims claims = retrieveClaims(token);
        return resolver.apply(claims);
    }

    /**
     * Retrieves the claims payload from the given token
     * @param token the current token
     * @return claims payload
     */
    private Claims retrieveClaims(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Creates a new HMAC-SHA key from current the signing key value
     * @return the signing key
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(configuration.getJwtSigningKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
