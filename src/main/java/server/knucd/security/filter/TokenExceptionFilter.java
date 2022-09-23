package server.knucd.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import server.knucd.exception.TokenNotFoundException;
import server.knucd.utils.api.ApiUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 토큰 관련 예외 핸들링
 */
@RequiredArgsConstructor
public class TokenExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        HttpServletResponse res = (HttpServletResponse)response;

        try {
            filterChain.doFilter(request, response);
        } catch (TokenNotFoundException e) {
            String body = objectMapper
                    .writeValueAsString(ApiUtil.error(HttpServletResponse.SC_BAD_REQUEST, e.getMessage()));

            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write(body);
        }
    }
}
