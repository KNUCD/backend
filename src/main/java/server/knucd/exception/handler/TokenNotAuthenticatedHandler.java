package server.knucd.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import server.knucd.utils.api.ApiUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Authentication Exception 핸들링
 * @see AuthenticationException
 * @see org.springframework.security.core.userdetails.UsernameNotFoundException
 */
@RequiredArgsConstructor
public class TokenNotAuthenticatedHandler implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        logger.error("인증 정보가 잘못되었습니다.", authException);
        String body = objectMapper
                .writeValueAsString(ApiUtil.error(HttpServletResponse.SC_UNAUTHORIZED, "인증 정보가 잘못되었습니다."));
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(body);
    }
}
