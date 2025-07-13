package ru.itis.vhsroni.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.lang.Arrays;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.itis.vhsroni.auth.data.principal.CustomUserDetails;
import ru.itis.vhsroni.auth.service.impl.CustomUserDetailsService;
import ru.itis.vhsroni.error.message.ErrorMessageProperties;

import java.io.IOException;
import java.util.stream.Stream;

import static ru.itis.vhsroni.config.security.SecurityEndpoints.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    private final CustomUserDetailsService userDetailsService;

    private final ErrorMessageProperties errorMessage;

    private final AntPathMatcher publicMatcher = new AntPathMatcher();


    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain)
            throws ServletException, IOException {
        boolean isPublic = isPublicRequest(request);
        if (isPublic) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authHeader.substring("Bearer ".length());
        Claims claims;
        try {
            claims = jwtProvider.extractAllClaims(token);
        } catch (Exception e) {
            throw new BadCredentialsException(errorMessage.invalidJwt(), e);
        }
        String email = claims.getSubject();
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            CustomUserDetails userDetails = userDetailsService.loadUserByUsername(email);
            if (jwtProvider.isTokenValid(token)) {
                CustomJwtAuthentication jwtAuthentication = new CustomJwtAuthentication(token);
                jwtAuthentication.setUserDetails(userDetails);
                jwtAuthentication.setAuthenticated(true);
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(jwtAuthentication);
                SecurityContextHolder.setContext(context);
            }
        }
        filterChain.doFilter(request, response);
    }

    public boolean isPublicRequest(HttpServletRequest request) {
        String path = request.getServletPath();
        return Stream.concat(
                        Arrays.asList(PUBLIC_ENDPOINTS).stream(),
                        HttpMethod.GET.matches(request.getMethod())
                                ? Arrays.asList(PUBLIC_GET_ENDPOINTS).stream()
                                : Stream.empty()
                )
                .anyMatch(pattern -> publicMatcher.match(pattern, path));
    }
}
