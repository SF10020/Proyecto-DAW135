package com.libcode.dawproject.dawproject;

import java.util.Set;
import java.util.HashSet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    // 👇 Configura cómo extraer los roles desde el token JWT (claim personalizado)
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        grantedAuthoritiesConverter.setAuthoritiesClaimName("https://dawproject.app/roles");

        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return converter;
    }

    // 🔐 Configuración de seguridad principal
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin/**").hasAuthority("ROLE_Admin")
                .requestMatchers("/editor/**").hasAuthority("ROLE_Editor")
                .requestMatchers("/api/tareas/**").hasAnyAuthority("ROLE_Admin", "ROLE_Editor")
                .requestMatchers("/api/simulaciones/**").hasAnyAuthority("ROLE_Admin", "ROLE_Editor")
                // .requestMatchers("/decisiones/crearDecision").hasAnyAuthority("ROLE_Admin", "ROLE_Editor")
                .requestMatchers("/decisiones/crearDecision").permitAll()
                .anyRequest().authenticated()
            )
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/**")
            )
            .oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfo -> userInfo
                    .oidcUserService(oidcUserRequest -> {
                        var oidcUserService = new org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService();
                        var oidcUser = oidcUserService.loadUser(oidcUserRequest);

                         // 🔍 Mostrar todos los claims disponibles
                        System.out.println("🔍 Claims recibidos desde Auth0:");
                        oidcUser.getClaims().forEach((k, v) -> System.out.println("🔸 " + k + ": " + v));

                        var roles = oidcUser.getClaimAsStringList("https://dawproject.app/roles");
                        System.out.println("🔍 Roles recibidos desde Auth0: " + roles);

                        Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
                        if (roles != null) {
                            for (String role : roles) {
                                mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role));
                            }
                        }

                        return new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
                    })
                )
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .jwtAuthenticationConverter(jwtAuthenticationConverter())
                )
            );

        return http.build();
    }

    // (Opcional) Configura el decodificador de JWT basado en el issuer de Auth0
    @Bean
    public JwtDecoder jwtDecoder() {
        String issuerUri = "https://dawproject.us.auth0.com/";
        return JwtDecoders.fromIssuerLocation(issuerUri);
    }
}





