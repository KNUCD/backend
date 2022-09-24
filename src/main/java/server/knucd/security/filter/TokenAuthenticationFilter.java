package server.knucd.security.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import server.knucd.exception.TokenNotFoundException;
import server.knucd.security.provider.TokenProvider;
import server.knucd.utils.redis.RedisUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * spring security 계정 토큰 검증 필터
 */
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends GenericFilterBean {

    private final TokenProvider tokenProvider;

    private static final String TOKEN_HEADER_NAME = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private final RedisUtil redis;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest)request;

        if (req.getMethod().equals("OPTIONS")){
            chain.doFilter(request, response);
            return;
        }

        String authHeader = req.getHeader(TOKEN_HEADER_NAME);
        String memberToken = null;
        if(authHeader != null && authHeader.startsWith(TOKEN_PREFIX)) memberToken = authHeader.replace(TOKEN_PREFIX, "");
        else {
            throw new TokenNotFoundException("헤더에 토큰이 없습니다.");
        }

        Long kakaoId = (Long) redis.get(memberToken);
        if (kakaoId == null) {
            throw new TokenNotFoundException("토큰이 만료되었습니다.");
        }

        request.setAttribute("kakaoId", kakaoId);

        Authentication authenticate = tokenProvider.authenticate(new UsernamePasswordAuthenticationToken(kakaoId, ""));
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        chain.doFilter(request, response);
    }
}
