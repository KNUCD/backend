package server.knucd.oAuth.kakao.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.knucd.member.service.MemberService;
import server.knucd.member.service.UserInfoDto;
import server.knucd.oAuth.kakao.dto.KakaoToken;
import server.knucd.oAuth.kakao.dto.TokenDTO;
import server.knucd.oAuth.kakao.service.KakaoService;
import server.knucd.utils.api.ApiUtil;
import server.knucd.utils.api.ApiUtil.*;
import server.knucd.utils.cookie.CookieUtil;
import server.knucd.utils.redis.RedisUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class RestKakaoController {

    @Value("${kakao.api-key}")
    private String REST_API_KEY;
    @Value("${kakao.admin-key}")
    private String ADMIN_KEY;
    @Value("${kakao.redirect-url}")
    private String CALLBACK_URL;

    private final MemberService memberService;
    private final CookieUtil cookieUtil;

    private final RedisUtil redisUtil;

    private final KakaoService kakaoService;

    @Operation(summary = "계정 토큰 재발급")
    @PostMapping("/api/v1/auth/account-token")
    public ApiSuccessResult<TokenDTO> reIssueToken(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException {
//      쿠키로 전달된 refresh 토큰 확인
        String refreshToken = KakaoService.REFRESH_TOKEN_PREFIX + cookieUtil.getCookie(req, "PP_refresh").getValue();

        TokenDTO token;

        String cachedAccessToken = (String) redisUtil.get(refreshToken);
        if (cachedAccessToken != null) {
            token = new TokenDTO(cachedAccessToken, null);
        } else {
            refreshToken = refreshToken.replaceFirst(KakaoService.REFRESH_TOKEN_PREFIX, "");
            token = kakaoService.reIssueToken("refresh_token", REST_API_KEY, refreshToken);
            UserInfoDto userInfo = kakaoService.getUserInfo(token.getAccessToken());

            kakaoService.cacheKakaoToken(userInfo.getKakaoId(), token.getAccessToken(), token.getRefreshToken());
        }
        return ApiUtil.success(token);
    }

    @GetMapping("/callback")
    public ApiSuccessResult<TokenDTO> kakaoLoginCallback(@RequestParam(name = "code") String code,
                                                         HttpServletResponse res) throws IOException {

        KakaoToken token = kakaoService.getToken(
                "authorization_code",
                REST_API_KEY,
                CALLBACK_URL,
                code
        );

        String accessToken = token.getAccess_token();
        String refreshToken = token.getRefresh_token();

        UserInfoDto userInfo = kakaoService.getUserInfo(accessToken);

        if (!memberService.existMemberByKakaoId(userInfo.getKakaoId())) {
            memberService.createMember(userInfo);
        }

        kakaoService.cacheKakaoToken(userInfo.getKakaoId(), accessToken, refreshToken);

        ResponseCookie cookie = cookieUtil.createCookie("PP_refresh", refreshToken);
        res.addHeader("Set-Cookie", cookie.toString());

        res.sendRedirect("https://www.pinplaint.com");

        return ApiUtil.success(new TokenDTO(accessToken, refreshToken));
    }
}
