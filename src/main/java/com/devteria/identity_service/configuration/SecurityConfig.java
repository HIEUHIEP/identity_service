package com.devteria.identity_service.configuration;

import com.devteria.identity_service.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final String[] PUBLIC_ENDPOINTS = {
            "/users",
            "/auth/token",
            "/auth/introspect",
            "/auth/logout",
            "/auth/refresh"
    };

    @Autowired
    private CustomJwtDecoder customJwtDecoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                authorizationManagerRequestMatcherRegistry
                        .requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll() // cho phép access mà ko cần token
//                        .requestMatchers(HttpMethod.GET, "/users").hasAuthority("ROLE_ADMIN") // phải là SCOPE_ADMIN thì mới get all users, => đổi prefix scope => role
//                        .requestMatchers(HttpMethod.GET, "/users").hasRole(Role.ADMIN.name()) // viết ngắn hơn. => đây là phân quyền theo end point
                        .anyRequest().authenticated()); // ngược lại, phải authenticated.

        httpSecurity.oauth2ResourceServer(httpSecurityOAuth2ResourceServerConfigurer ->
                httpSecurityOAuth2ResourceServerConfigurer.jwt(jwtConfigurer -> jwtConfigurer.decoder(customJwtDecoder) // .jwkSetUri : dùng khi authen ở 1 server thứ 3, decoder : dùng chinh token đang generate
                                .jwtAuthenticationConverter(jwtAuthenticationConverter()))  //thay đổi prefix mặt định SCOPE_ thành ROLE_
                        .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
        );

        // Default csrf = enable ( cross site )
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {

        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(""); // Đã chủ động thêm prefix ROLE_ ở AuthenticationService lúc tạo token. nên ở đây xoá thành blank
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }


    @Bean
        // Bean : passwordEncoder sẽ được add vào application context khi application start
    PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder(10);
    }
}
