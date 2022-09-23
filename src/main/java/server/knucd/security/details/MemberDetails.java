package server.knucd.security.details;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import server.knucd.member.entity.Member;

public class MemberDetails extends User {

    public MemberDetails(Member member) {
        super(String.valueOf(member.getKakaoId()), "",
                AuthorityUtils.createAuthorityList(member.getRole().toString()));
    }

    public Long getKakaoId() {
        return Long.parseLong(this.getUsername());
    }
}
