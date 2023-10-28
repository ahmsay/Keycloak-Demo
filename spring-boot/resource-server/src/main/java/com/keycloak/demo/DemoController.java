package com.keycloak.demo;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/regular")
    public String index(@AuthenticationPrincipal Jwt jwt) {
        return String.format("Hello, %s!", jwt.getClaimAsString("preferred_username"));
    }

    @PreAuthorize("hasAuthority('PREMIUM')")
    @GetMapping("/premium")
    public String premium(@AuthenticationPrincipal Jwt jwt) {
        return String.format("Hello, premium %s!", jwt.getClaimAsString("preferred_username"));
    }
}
