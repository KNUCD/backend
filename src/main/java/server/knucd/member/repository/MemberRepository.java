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

    public Optional<Member> findByKakaoId(Long kakaoId) {
        return em.createQuery("select m from Member m where m.kakaoId = :kakaoId", Member.class)
                .setParameter("kakaoId", kakaoId)
                .getResultList()
                .stream()
                .findFirst();
    }

    public Boolean existMemberByKakaoId(Long kakaoId) {
        return em.createQuery("select count(m) > 0 from Member m " +
                "where m.kakaoId =:kakaoId", Boolean.class)
                .setParameter("kakaoId", kakaoId)
                .getSingleResult();
    }
}
