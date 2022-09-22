package server.knucd.expression.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import server.knucd.expression.entity.Expression;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ExpressionRepository {

    private final EntityManager em;

    public void save(Expression expression) {
        em.persist(expression);
    }

    public Long countGreatByComplaintId(Long complaintId) {
        return em.createQuery("select count(e) from Expression e " +
                "where e.complaint.id = :complaintId and e.type = 'GREAT'", Long.class)
                .setParameter("complaintId", complaintId)
                .getSingleResult();
    }

    public Long countLikeByComplaintId(Long complaintId) {
        return em.createQuery("select count(e) from Expression e " +
                        "where e.complaint.id = :complaintId and e.type = 'LIKE'", Long.class)
                .setParameter("complaintId", complaintId)
                .getSingleResult();
    }

    public Long countSadByComplaintId(Long complaintId) {
        return em.createQuery("select count(e) from Expression e " +
                        "where e.complaint.id = :complaintId and e.type = 'SAD'", Long.class)
                .setParameter("complaintId", complaintId)
                .getSingleResult();
    }

    public Optional<Expression> findMyExpressionByComplaintId(Long complaintId, Long memberId) {
        List<Expression> expressions = em.createQuery("select e from Expression e " +
                "where e.complaint.id = :complaintId and e.follower.id = :memberId", Expression.class)
                .setParameter("complaintId", complaintId)
                .setParameter("memberId", memberId)
                .getResultList();
        if(expressions.isEmpty()) return Optional.empty();
        return Optional.of(expressions.get(0));
    }

    public List<Expression> findAllByComplaintId(Long complaintId) {
        return em.createQuery("select e from Expression e where e.complaint.id = :complaintId", Expression.class)
                .setParameter("complaintId", complaintId)
                .getResultList();
    }

    public void delete(Expression expression) {
        em.remove(expression);
    }
}
