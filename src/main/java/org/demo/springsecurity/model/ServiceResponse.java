package org.demo.springsecurity.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResponse {

    private Boolean success;
    private Object response;
    private Map<String, String> error;
}
