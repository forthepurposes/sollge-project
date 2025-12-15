package com.sollge.auth.token;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.NonNull;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.validation.annotation.Validated;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;

@Getter
@RequiredArgsConstructor
@Validated
@ConfigurationProperties(prefix = "jwt")
public class JsonTokenProperties {

    private final Duration ttl;

    private final RSAPrivateKey privateKey;
    private final RSAPublicKey publicKey;

    @Bean
    public @NonNull JwtEncoder jwtEncoder() {
        RSAKey jwk = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .build();

        return new NimbusJwtEncoder(new ImmutableJWKSet<>(new JWKSet(jwk)));
    }

    @Bean
    public @NonNull JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }

    @Bean
    public @NonNull JsonTokenService jwtService(
            @Value("${spring.application.name}") final String appName,
            final @NonNull JwtEncoder jwtEncoder,
            final @NonNull JwtDecoder jwtDecoder
    ) {
        return new JsonTokenService(appName, ttl, jwtEncoder, jwtDecoder);
    }
}