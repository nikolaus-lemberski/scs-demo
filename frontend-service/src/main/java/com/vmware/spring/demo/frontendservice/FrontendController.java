package com.vmware.spring.demo.frontendservice;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class FrontendController {

    @Autowired
    private BackendClient feignBackend;

    @Autowired
    private BackendClient webClientBackend;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public String sayHello() {
        return "{\"message\": \"Hello from Frontend-Service\"}";
    }

    @GetMapping(value = "backend", produces = APPLICATION_JSON_VALUE)
    public Publisher<String> callBackend() {
        return feignBackend.callBackend();
    }

    @GetMapping(value = "webclient-backend", produces = APPLICATION_JSON_VALUE)
    public Publisher<String> callWebclientBackend() {
        return webClientBackend.callBackend();
    }

}
