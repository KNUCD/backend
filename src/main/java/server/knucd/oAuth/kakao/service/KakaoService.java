package server.knucd.oAuth.kakao.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import server.knucd.member.service.UserInfoDto;
import server.knucd.oAuth.kakao.dto.KakaoToken;
import server.knucd.oAuth.kakao.dto.KakaoTokenInfo;
import server.knucd.oAuth.kakao.dto.TokenDTO;
import server.knucd.utils.redis.RedisUtil;

import java.util.Map;


@Service
@RequiredArgsConstructor
public class KakaoService {

    public static final String REFRESH_TOKEN_PREFIX = "KT::RT::";
    public static final String ACCESS_TOKEN_PREFIX = "KT::AT::";

    public static final long TOKEN_VALIDATION_SECOND = 60 * 120;
    public static final long REFRESH_TOKEN_VALIDATION_TIME = 60 * 60 * 24 * 30;

    private final RedisUtil redisUtil;

    public KakaoToken getToken(String type, String apiKey, String callbackURL, String code)
            throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("grant_type", type);
        param.add("client_id", apiKey);
        param.add("redirect_uri", callbackURL);
        param.add("code", code);

        HttpEntity<MultiValueMap<String, String>> requestInfo
                = new HttpEntity<>(param, headers);

        RestTemplate req = new RestTemplate();
        ResponseEntity<String> response = req.exchange("https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                requestInfo,
                String.class);

        ObjectMapper mapper = new ObjectMapper();
        KakaoToken token = mapper.readValue(response.getBody(), KakaoToken.class);

        return token;
    }

    public KakaoTokenInfo getTokenInfo(String accessToken) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity requestInfo = new HttpEntity(headers);

        RestTemplate req = new RestTemplate();
        ResponseEntity<String> response = req.exchange("https://kapi.kakao.com/v1/user/access_token_info",
                HttpMethod.GET,
                requestInfo,
                String.class);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> result = mapper.readValue(response.getBody(), Map.class);

        KakaoTokenInfo info = new KakaoTokenInfo(result);

        return info;
    }

    public UserInfoDto getUserInfo(String accessToken) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity requestInfo = new HttpEntity(headers);

        RestTemplate req = new RestTemplate();
        ResponseEntity<String> response = req.exchange("https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                requestInfo,
                String.class);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> result = mapper.readValue(response.getBody(), Map.class);

        Long id = (Long)result.get("id");

        Map<String, String> properties = (Map<String, String>) (result.get("properties"));
        String nickname = properties.get("nickname");
        String imageUrl = properties.get("profile_image");

        return new UserInfoDto(id, nickname, imageUrl);
    }

    public TokenDTO reIssueToken(String type, String apiKey, String refreshToken) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("grant_type", type);
        param.add("client_id", apiKey);
        param.add("refresh_token", refreshToken);

        HttpEntity requestInfo = new HttpEntity(param, headers);

        RestTemplate req = new RestTemplate();
        ResponseEntity<String> response = req.exchange("https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                requestInfo,
                String.class);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> result = mapper.readValue(response.getBody(), Map.class);

        return new TokenDTO((String)result.get("access_token"), (String)result.get("refresh_token"));
    }

    public void cacheKakaoToken(Long kakaoId, String accessToken, String refreshToken) {
        redisUtil.set(REFRESH_TOKEN_PREFIX + refreshToken, accessToken);
        redisUtil.expire(REFRESH_TOKEN_PREFIX, TOKEN_VALIDATION_SECOND);

        redisUtil.set(ACCESS_TOKEN_PREFIX + accessToken, kakaoId);
        redisUtil.expire(ACCESS_TOKEN_PREFIX + accessToken, TOKEN_VALIDATION_SECOND);
    }
}
