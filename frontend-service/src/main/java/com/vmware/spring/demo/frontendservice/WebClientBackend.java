package com.vmware.spring.demo.frontendservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Qualifier("webClientBackend")
public class WebClientBackend implements BackendClient {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private ReactiveCircuitBreakerFactory circuitBreakerFactory;

    @Override
    public Mono<String> callBackend() {
        WebClient webClient = webClientBuilder
                .baseUrl("//backend-service")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        return webClient
                .get()
                .uri("/")
                .retrieve()
                .bodyToMono(String.class)
                .transform(it -> {
                    return circuitBreakerFactory.create("hello").run(it, throwable -> Mono.just("fallback"));
                });
    }

}
