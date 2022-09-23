package server.knucd.member.service;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoDto {
    private Long kakaoId;
    private String name;
    private String image;

    public UserInfoDto(Long kakaoId, String name, String image) {
        this.kakaoId = kakaoId;
        this.name = name;
        this.image = image;
    }
}
