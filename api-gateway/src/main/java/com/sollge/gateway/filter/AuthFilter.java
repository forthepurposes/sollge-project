package com.sollge.gateway.filter;

import lombok.RequiredArgsConstructor;
import com.sollge.gateway.dto.TokenValidatedResponseDTO;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class AuthFilter implements GatewayFilter {

    private static final Collection<Predicate<ServerHttpRequest>> PUBLIC_URLS = new ArrayList<>();

    static {
        PUBLIC_URLS.add(request -> request.getMethod() == HttpMethod.GET && isPathAllowed(request));
    }

    private static boolean isPathAllowed(final @NonNull ServerHttpRequest request) {
        return request.getURI().getPath().startsWith("/api/v1/rentals");
    }

    private final WebClient.Builder webClientBuilder;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var httpRequest = exchange.getRequest();
        if (shouldSkipRequest(httpRequest)) {
            return chain.filter(exchange);
        }

        var authorizationHeader = httpRequest.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return onError(exchange);
        }

        return webClientBuilder.build().get()
                .uri("http://user-service/api/v1/users/validate")
                .header(HttpHeaders.AUTHORIZATION, authorizationHeader)
                .retrieve()
                .onStatus(HttpStatusCode::isError, _ -> Mono.error(new RuntimeException("Unauthorized")))
                .bodyToMono(TokenValidatedResponseDTO.class)
                .flatMap(response -> {
                    ServerHttpRequest mutatedRequest = exchange.getRequest()
                            .mutate()
                            .header("X-User-Id", String.valueOf(response.userId()))
                            .header("X-User-Username", response.username())
                            .build();

                    ServerWebExchange mutatedExchange = exchange.mutate()
                            .request(mutatedRequest)
                            .build();

                    return chain.filter(mutatedExchange);
                });
    }

    private @NonNull Mono<Void> onError(final @NonNull ServerWebExchange exchange) {
        var response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setCacheControl("no-store");
        response.getHeaders().setPragma("no-cache");
        return response.setComplete();
    }

    private boolean shouldSkipRequest(final @NonNull ServerHttpRequest httpRequest) {
        return PUBLIC_URLS
                .stream()
                .anyMatch(request -> request.test(httpRequest));
    }
}
