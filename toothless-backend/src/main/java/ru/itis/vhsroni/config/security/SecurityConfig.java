package ru.itis.vhsroni.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import ru.itis.vhsroni.auth.handler.CustomOAuthSuccessHandler;
import ru.itis.vhsroni.auth.jwt.JwtAuthenticationFilter;
import ru.itis.vhsroni.auth.oauth.CustomOAuth2UserService;
import ru.itis.vhsroni.auth.service.impl.CustomUserDetailsService;
import ru.itis.vhsroni.error.handler.CustomAccessDeniedHandler;
import ru.itis.vhsroni.error.handler.CustomAuthenticationEntryPoint;

import static ru.itis.vhsroni.config.security.SecurityEndpoints.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final CustomUserDetailsService customUserDetailsService;

    private final CustomOAuth2UserService customOAuth2UserService;

    private final CustomOAuthSuccessHandler customOAuthSuccessHandler;

    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/**")
                        .requireCsrfProtectionMatcher(request ->
                                request.getMethod().equals("POST") &&
                                request.getRequestURI().equals(CSRF_PROTECTED_ENDPOINT)
                        )
                )
                .cors(Customizer.withDefaults())
                .headers(headers ->
                        headers.xssProtection(xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK))
                                .contentSecurityPolicy(cps -> cps.policyDirectives("script-src 'self'"))
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, ALL_OPTIONS).permitAll()
                        .requestMatchers(ANONYMOUS_ENDPOINTS).anonymous()
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .requestMatchers(HttpMethod.GET,PUBLIC_GET_ENDPOINTS).permitAll()
                        .requestMatchers(AUTHENTICATED_ENDPOINTS).authenticated()
                        .requestMatchers(HttpMethod.POST, AUTHENTICATED_POST_ENDPOINTS).authenticated()
                        .requestMatchers(HttpMethod.DELETE, AUTHENTICATED_DELETE_ENDPOINTS).authenticated()
                        .requestMatchers(HttpMethod.PATCH, AUTHENTICATED_PATCH_ENDPOINTS).authenticated()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(eh -> eh
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(authorization -> authorization
                                .baseUri(OAUTH2_AUTHORIZATION_BASE_URI)
                        )
                        .redirectionEndpoint(redirection -> redirection
                                .baseUri(OAUTH2_REDIRECTION_BASE_URI)
                        )
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                        .successHandler(customOAuthSuccessHandler)
                )
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder managerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        managerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
        return managerBuilder.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(SWAGGER_ENDPOINTS);
    }
}

