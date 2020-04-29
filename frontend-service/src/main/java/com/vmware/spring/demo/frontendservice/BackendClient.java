package com.vmware.spring.demo.frontendservice;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import static java.lang.String.format;

@FeignClient("backend-service")
public interface BackendClient {

    @GetMapping
    @CircuitBreaker(name = "backend-service/hello", fallbackMethod = "fallbackBackend")
    String callBackend();

    default String fallbackBackend(Throwable t) {
        return format("{\"message\": \"Backend not available\", \"cause\": \"%s\"}", t.getMessage());
    }

}
