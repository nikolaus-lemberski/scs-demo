package com.vmware.spring.demo.frontendservice;

import reactor.core.publisher.Mono;

public interface BackendClient {

    Mono<String> callBackend();

}
