package server.knucd.security.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import server.knucd.security.details.MemberDetails;
import server.knucd.security.details.MemberDetailsService;


/**
 * spring security JWT 검증 수행
 * <p>token 데이터 추출 로직은 유효성 검사가 항상 선수</p>
 * <p><br>현재 JWT 관련 보조 기능도 해당 클래스에 함께 포함되어있는 상태, 다음 버전에서 수정 예정</p>
 *
 */
@Component
@RequiredArgsConstructor
public class TokenProvider implements AuthenticationProvider {

    private final MemberDetailsService memberDetailsService;

    /**
     * 서버 스펙 JWT 유효기간
     */
    private static final long TOKEN_VALIDATION_SECOND = 1000L * 60 * 120;
    private static final long REFRESH_TOKEN_VALIDATION_TIME = 1000L * 60 * 60 * 48;

    /**
     * 인증 토큰 캐싱 유효기간
     */
    public static final long TOKEN_CACHING_SECOND = 1000L * 120;

    /**
     * 서버 스펙 JWT 이름
     */
    public static final String ACCOUNT_TOKEN_NAME = "member_refresh_token";

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MemberDetails userDetails = (MemberDetails) memberDetailsService
                     .loadUserByUsername(String.valueOf(authentication.getPrincipal()));


        return new UsernamePasswordAuthenticationToken(
                userDetails.getKakaoId(),
                userDetails.getPassword(),
                userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
