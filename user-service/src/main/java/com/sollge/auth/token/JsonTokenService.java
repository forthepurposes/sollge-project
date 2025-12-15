package com.sollge.auth.token;

import lombok.RequiredArgsConstructor;
import com.sollge.auth.user.dto.auth.TokenValidatedResponseDTO;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import java.time.Duration;
import java.time.Instant;

@RequiredArgsConstructor
public class JsonTokenService {

    private final String issuer;
    private final Duration ttl;

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public @NonNull String generateToken(final @NonNull String username, final @NonNull Long userId) {
        var claims = JwtClaimsSet.builder()
                .subject(username)
                .claims(map -> map.put("id", userId))
                .issuer(issuer)
                .expiresAt(Instant.now().plus(ttl))
                .build();

        return jwtEncoder
                .encode(JwtEncoderParameters.from(claims))
                .getTokenValue();
    }

    public @NonNull TokenValidatedResponseDTO validateToken(final @NonNull String authorizationHeader) {
        var token = authorizationHeader.substring(7);
        var jwt = jwtDecoder.decode(token);

        var tokenIssuer = jwt.getClaimAsString("iss");
        if (!issuer.equals(tokenIssuer)) {
            throw new AuthenticationServiceException("Invalid token issuer");
        }

        var username = jwt.getSubject();
        var userId = jwt.<Long>getClaim("id");

        return new TokenValidatedResponseDTO(username, userId);
    }
}
