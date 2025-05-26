package com.libcode.dawproject.dawproject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@EnableMethodSecurity(prePostEnabled = true) // 🔐 Habilita @PreAuthorize, @PostAuthorize, etc.
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/index").permitAll()
                .requestMatchers("/admin/**").hasAuthority("ROLE_Admin")
                .requestMatchers("/editor/**").hasAuthority("ROLE_Editor")
                .requestMatchers("/api/tareas/**").hasAnyAuthority("ROLE_Admin", "ROLE_Editor")
                .requestMatchers("/api/simulaciones/**").hasAnyAuthority("ROLE_Admin", "ROLE_Editor")
                .anyRequest().authenticated()
            )
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/**") // Permitir POST/PUT/DELETE sin token CSRF en API
            )
            .oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfo -> userInfo
                    .oidcUserService(this.oidcUserService()) // Mapeo de roles desde Auth0
                )
                .defaultSuccessUrl("/home", true) // Redirigir a /home después de iniciar sesión
                .failureUrl("/login?error=true") // Redirigir a /login en caso de error
            );

        return http.build();
    }

        private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
            final OidcUserService delegate = new OidcUserService();

            return userRequest -> {
                OidcUser oidcUser = delegate.loadUser(userRequest);

                Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

                List<String> roles = oidcUser.getAttribute("https://dawproject.example.com/roles"); // asegúrate que 'roles' venga en el token
                System.out.println("🔍 Roles recibidos desde Auth0: " + roles);

                if (roles != null) {
                    for (String role : roles) {
                        mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role));
                    }
                }

                return new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
            };
        }

}





