package com.vmware.spring.demo.frontendservice;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

import static java.lang.String.format;

@FeignClient("backend-service")
@Qualifier("feignBackend")
public interface FeignBackend extends BackendClient {

    @GetMapping
    @CircuitBreaker(name = "backend-service/hello", fallbackMethod = "fallbackBackend")
    Mono<String> callBackend();

    default Mono<String> fallbackBackend(Throwable t) {
        return Mono.just(format("{\"message\": \"Backend not available\", \"cause\": \"%s\"}", t.getMessage()));
    }

}
