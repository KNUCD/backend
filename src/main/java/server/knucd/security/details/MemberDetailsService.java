package server.knucd.security.details;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import server.knucd.member.entity.Member;
import server.knucd.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String kakaoId) throws UsernameNotFoundException {
        Member member = memberRepository.findByKakaoId(Long.parseLong(kakaoId))
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일과 일치하는 계정이 없습니다."));

        return new MemberDetails(member);
    }
}
