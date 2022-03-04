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
    private final Session session;

    @Override
    public E save (E entity){
        session.save(entity);
        return entity;
    }

    @Override
    public void delete(K id) {
        E exemplarForDelete = session.find(clazz, id);
        if (exemplarForDelete != null){
            session.delete(exemplarForDelete);
            session.flush();
        }
    }

    @Override
    public void update(E entity) {
        session.update(entity);
    }

    @Override
    public Optional<E> findById(K id) {
        return Optional.ofNullable(session.find(clazz, id));
    }

    @Override
    public ArrayList<E> findAll() {
        CriteriaQuery<E> criteriaQuery = session.getCriteriaBuilder().createQuery(clazz);
        criteriaQuery.from(clazz);
        return (ArrayList<E>) session.createQuery(criteriaQuery).getResultList();
    }
}
