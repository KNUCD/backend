package server.knucd.member.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import server.knucd.member.entity.Member;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }
}
