package com.vmware.spring.demo.backendservice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BackendMessage {

    private String message;
    private String uri;
    private String instanceIndex;

}
