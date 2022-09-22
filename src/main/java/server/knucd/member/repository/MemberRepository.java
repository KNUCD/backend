package server.knucd.member.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import server.knucd.member.entity.Member;

import javax.persistence.EntityManager;
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
}
