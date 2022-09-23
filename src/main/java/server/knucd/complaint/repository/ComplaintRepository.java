package server.knucd.complaint.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import server.knucd.complaint.entity.Category;
import server.knucd.complaint.entity.Complaint;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ComplaintRepository {

    private final EntityManager em;

    public void save(Complaint complaint) {
        em.persist(complaint);
    }

    public Optional<Complaint> findById(Long id) {
        return Optional.ofNullable(em.find(Complaint.class, id));
    }

    public List<Complaint> findAll() {
        return em.createQuery("select c from Complaint c order by c.id desc", Complaint.class).getResultList();
    }

    public List<Complaint> findAll(int page, int size) {
        return em.createQuery("select c from Complaint c order by c.id desc ", Complaint.class)
                .setFirstResult((page-1)*size)
                .setMaxResults(size)
                .getResultList();
    }

    public List<Complaint> findAllByCategory(Category category) {
        return em.createQuery("select c from Complaint c " +
                "where c.category = :category " +
                "order by c.id desc",
                Complaint.class)
                .setParameter("category", category)
                .getResultList();
    }

    public List<Complaint> findAllByCategory(Category category, int page, int size) {
        return em.createQuery("select c from Complaint c " +
                "where c.category = :category " +
                "order by c.id desc",
                Complaint.class)
                .setParameter("category", category)
                .setMaxResults(size)
                .setFirstResult((page-1)*size)
                .getResultList();
    }

    public List<Complaint> findByRange(Double lat1, Double long1, Double lat2, Double long2) {
        return em.createQuery("select c from Complaint c " +
                "where c.location.latitude >= :lat1 and c.location.longitude >= :long1 " +
                "and c.location.latitude <= :lat2 and c.location.longitude <= :long2", Complaint.class)
                .setParameter("lat1", lat1)
                .setParameter("long1", long1)
                .setParameter("lat2", lat2)
                .setParameter("long2", long2)
                .getResultList();

    }

    public void delete(Complaint complaint) {
        em.remove(complaint);
    }
}
