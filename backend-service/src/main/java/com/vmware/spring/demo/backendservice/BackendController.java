package com.vmware.spring.demo.backendservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BackendController {

    @Value("${backend.service.version}")
    private String version;

    @Value("${cf.instance.index}")
    private String instanceIndex;

    @Value("${vcap.application.application_uris}")
    private String uri;

    @GetMapping(produces = "application/json")
    private BackendMessage sayHello() {
        return new BackendMessage("Hello from Backend Service " + version, uri, instanceIndex);
    }

}
