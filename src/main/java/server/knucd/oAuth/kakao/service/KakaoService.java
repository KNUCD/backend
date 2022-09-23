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

import java.util.Map;


@Service
@RequiredArgsConstructor
public class KakaoService {

    private static final String REFRESH_TOKEN_PREFIX = "KT::RT::";
    private static final String ACCESS_TOKEN_PREFIX = "KT::AT::";

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
}
