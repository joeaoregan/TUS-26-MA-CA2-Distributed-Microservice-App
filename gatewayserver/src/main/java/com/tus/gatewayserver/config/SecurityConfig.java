package com.tus.gatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity.authorizeExchange(exchanges -> exchanges.pathMatchers(HttpMethod.GET).permitAll() // Allow viewing guitars without login
            .pathMatchers("/guitar/orders/**").hasRole("ORDERS") // Only users with ORDERS role can access order-related endpoints
            .pathMatchers("/guitar/inventory/**").hasRole("INVENTORY")) // Only users with INVENTORY role can access inventory-related endpoints
            .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
                .jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(grantedAuthoritiesExtractor()))); // Use custom JWT converter to extract roles
        
        serverHttpSecurity.csrf(csrfSpec -> csrfSpec.disable()); // Disable CSRF for stateless APIs
        return serverHttpSecurity.build();
    }
    
    private Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
    		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
    		return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }
}