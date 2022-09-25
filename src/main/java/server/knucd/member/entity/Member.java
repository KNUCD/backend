package server.knucd.member.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.knucd.base.BaseEntity;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    private Long kakaoId;

    private String name;

    private String image;

    @Enumerated(EnumType.STRING)
    private Role role;


    @Builder
    public Member(Long kakaoId, String name, String image, Role role) {
        this.kakaoId = kakaoId;
        this.name = name;
        this.image = image;
        this.role = role;
    }
}
