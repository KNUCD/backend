package server.knucd.expression.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import server.knucd.expression.entity.Expression;
import server.knucd.expression.entity.ExpressionType;
import server.knucd.utils.redis.RedisUtil;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ExpressionRepository {

    private final EntityManager em;

    /**
     * key : complaint:expression:{complaintId}
     * value : Map<memberId : expressionType>
     * */
    private final RedisUtil redis;
    private final static String keyPrefix = "complaint:expression:";

    public void DBtoRedis(Long complaintId) {
        List<Expression> expressions = em.createQuery("select e from Expression e where e.complaint.id = :complaintId", Expression.class)
                .setParameter("complaintId", complaintId)
                .getResultList();
        if(expressions.isEmpty()) return;
        String key = keyPrefix + complaintId;
        for(Expression expression : expressions) redis.hashPut(key, expression.getFollower().getId(), expression.getType());
    }
    public void save(Expression expression, Long complaintId, Long memberId) {
        String key = keyPrefix + complaintId;

        if(!redis.exists(key)) DBtoRedis(complaintId);
        ExpressionType addingType = (ExpressionType) redis.hashGet(key, memberId);
        if(addingType != null) {
            Expression findExp = em.createQuery("select e from Expression e where e.complaint.id = :complaintId and e.follower.id = :memberId", Expression.class)
                    .setParameter("complaintId", complaintId)
                    .setParameter("memberId", memberId)
                    .getSingleResult();
            if(addingType.equals(expression.getType())) {
                redis.hashDel(key, memberId);
                em.remove(findExp);
            }
            else {
                redis.hashPut(key, memberId, expression.getType());
                redis.expire(key, 3600*24*100);
                findExp.updateType(expression.getType());
            }
        }
        else {
            redis.hashPut(key, memberId, expression.getType());
            redis.expire(key, 3600*24*100);
            em.persist(expression);
        }
    }

    public Long countExpressionByComplaintId(Long complaintId, ExpressionType type) {
        String key = keyPrefix + complaintId;
        if(!redis.exists(key)) DBtoRedis(complaintId);
        Map<Object, Object> map = redis.hashEntries(key);
        Long great = 0L;
        for(Object memberId : map.keySet()) {
            if(map.get(memberId).equals(type)) great++;
        }
        return great;
    }

    public ExpressionType findMyExpressionByComplaintId(Long complaintId, Long memberId) {
        String key = keyPrefix + complaintId;
        if(!redis.exists(key)) DBtoRedis(complaintId);
        return (ExpressionType) redis.hashGet(key, memberId);
    }

    public void deleteAllByComplaintId(Long complaintId) {
        /*
        * 캐시에 있으면 캐시 삭제
        * 디비 삭제
        * */
        String key = keyPrefix + complaintId;
        if(redis.exists(key)) redis.del(key);
        List<Expression> expressions = em.createQuery("select e from Expression e where e.complaint.id = :complaintId", Expression.class)
                .setParameter("complaintId", complaintId)
                .getResultList();
        for(Expression expression : expressions) em.remove(expression);
    }

    public void delete(Expression expression) {
        em.remove(expression);
    }
}
