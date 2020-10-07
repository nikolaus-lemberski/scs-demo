package com.vmware.spring.demo.frontendservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FrontendController {

    @Autowired
    private BackendClient backendClient;

    @GetMapping(produces = "application/json")
    private String sayHello() {
        return "{\"message\": \"Hello from Frontend-Service\"}";
    }

    @GetMapping(value = "backend", produces = "application/json")
    private String callBackend() {
        return backendClient.callBackend();
    }

}
