package server.knucd.member.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import server.knucd.member.entity.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(em.find(Member.class, id));
    }

    public Optional<Member> findByKakaoId(String kakaoId) {
        List<Member> members = em.createQuery("select m from Member m where m.kakaoId = :kakaoId", Member.class)
                .setParameter("kakaoId", kakaoId)
                .getResultList();
        if(members.isEmpty()) return Optional.empty();
        return Optional.of(members.get(0));
    }
}
