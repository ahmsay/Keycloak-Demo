package com.keycloak.demo;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/")
    public String index(@AuthenticationPrincipal Jwt jwt) {
        return String.format("Hello, %s!", jwt.getClaimAsString("preferred_username"));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/protected/premium")
    public String premium(@AuthenticationPrincipal Jwt jwt) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return String.format("Hello, %s!", jwt.getClaimAsString("preferred_username"));
    }
}
