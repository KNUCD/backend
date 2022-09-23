package server.knucd.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.knucd.exception.NotFoundException;
import server.knucd.member.entity.Member;
import server.knucd.member.entity.Role;
import server.knucd.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public Boolean existMemberByKakaoId(Long kakaoId) {
        return memberRepository.existMemberByKakaoId(kakaoId);
    }


    public Member findByKakaoId(Long kakaoId) {
        return memberRepository.findByKakaoId(kakaoId).orElseThrow(
                () -> new NotFoundException("존재하지 않는 회원입니다."));
    }

    @Transactional
    public void createMember(UserInfoDto dto) {
        Member member = Member.builder()
                .name(dto.getName())
                .kakaoId(dto.getKakaoId())
                .image(dto.getImage())
                .role(Role.ROLE_USER)
                .build();

        memberRepository.save(member);
    }
}
