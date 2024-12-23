package com.authentication_api.application.service;

import com.authentication_api.infrastructure.client.RoleClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.github.benmanes.caffeine.cache.Cache;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleClient roleClient;
    private final Cache<UUID, String> roleCache;

    @CircuitBreaker(name = "roleService", fallbackMethod = "getRoleFallback")
    public String getRoleNameById(UUID roleId) {
        return roleClient.getRoleNameById(roleId);
    }


    public String getRoleFallback(UUID roleId, Throwable t) {
        return roleCache.getIfPresent(roleId) != null
                ? roleCache.getIfPresent(roleId)
                : "GUEST";
    }
}
