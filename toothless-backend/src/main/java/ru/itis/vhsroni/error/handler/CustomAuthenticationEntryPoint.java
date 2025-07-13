package ru.itis.vhsroni.error.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import ru.itis.vhsroni.error.dto.ApiErrorResponse;
import ru.itis.vhsroni.error.enums.ErrorCode;
import ru.itis.vhsroni.error.message.ErrorMessageProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final ErrorMessageProperties errorMessage;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        ApiErrorResponse resp = ApiErrorResponse.builder()
                .code(401)
                .errorCode(ErrorCode.UNAUTHORIZED)
                .exceptionMessage(errorMessage.unauthorized())
                .build();
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        String json = objectMapper.writeValueAsString(resp);
        response.getOutputStream().write(json.getBytes(StandardCharsets.UTF_8));
        response.getOutputStream().flush();
    }
}

