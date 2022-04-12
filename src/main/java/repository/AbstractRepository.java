package repository;

import entity.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractRepository<K extends Serializable, E extends BaseEntity<K>> implements Repository<K, E> {
    private final Class<E> clazz;

    @Override
    public Long save(E entity, Session session) {
        return (Long) session.save(entity);

    }

    @Override
    public void delete(K id, Session session) {
        E exemplarForDelete = session.find(clazz, id);
        if (exemplarForDelete != null) {
            session.delete(exemplarForDelete);
            session.flush();
        }
    }

    @Override
    public void update(E entity, Session session) {
        session.update(entity);
    }

    @Override
    public Optional<E> findById(K id, Session session) {
        return Optional.ofNullable(session.find(clazz, id));
    }

    @Override
    public ArrayList<E> findAll(Session session) {
        CriteriaQuery<E> criteriaQuery = session.getCriteriaBuilder().createQuery(clazz);
        criteriaQuery.from(clazz);
        return (ArrayList<E>) session.createQuery(criteriaQuery).getResultList();
    }
}
