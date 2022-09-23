package server.knucd.oAuth.kakao.dto;

import lombok.Data;

import java.util.Map;

@Data
public class KakaoTokenInfo {
    private Long id;
    private Integer accessExpireSec;
    private Integer appId;

    public KakaoTokenInfo(Map<String, Object> info) {
        id = (Long) info.get("id");
        accessExpireSec = (Integer)info.get("expires_in");
        appId = (Integer)info.get("app_id");
    }
}
