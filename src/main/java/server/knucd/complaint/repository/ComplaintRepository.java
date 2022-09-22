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

    public void delete(Complaint complaint) {
        em.remove(complaint);
    }
}
