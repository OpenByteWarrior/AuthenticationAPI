package com.authentication_api.infrastructure.client;

import com.authentication_api.infrastructure.dto.RoleResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.UUID;

@Component
public class RoleClient {

    private final RestTemplate restTemplate;

    @Value("${role.server.url}")
    private String roleServerUrl;

    public RoleClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getRoleNameById(UUID roleId) {
        String url = roleServerUrl+"/roles/" + roleId;
        RoleResponseDTO roleResponse = restTemplate.getForObject(url, RoleResponseDTO.class);

        if (roleResponse != null && roleResponse.getResponse() != null) {
            return roleResponse.getResponse().getName();
        } else {
            throw new RuntimeException("El microservicio de roles no devolvió una respuesta válida.");
        }
    }
}
