package server.knucd.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.knucd.exception.NotFoundException;
import server.knucd.member.entity.Member;
import server.knucd.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public Member findByKakaoId(String kakaoId) {
        return memberRepository.findByKakaoId(kakaoId).orElseThrow(
                () -> new NotFoundException("존재하지 않는 회원입니다."));
    }
}
