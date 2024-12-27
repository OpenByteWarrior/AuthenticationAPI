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

        String cachedRole = roleCache.getIfPresent(roleId);
        if (cachedRole != null) {
            return cachedRole;
        }

        try {
            String roleName = roleClient.getRoleNameById(roleId);

            if (roleName != null && !roleName.equals("GUESTS")) {
                roleCache.put(roleId, roleName);
            }
            return roleName;
        } catch (Exception e) {
            return getRoleFallback(roleId, e);
        }
    }

    public String getRoleFallback(UUID roleId, Throwable t) {
        return "GUESTS";
    }
}

