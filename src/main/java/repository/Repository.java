package repository;

import entity.BaseEntity;

import org.hibernate.Session;
import org.postgresql.util.PSQLException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public interface Repository <K extends Serializable, E extends BaseEntity<K>>{

    E save (E entity, Session session) throws PSQLException;

    void delete(K id, Session session);

    void update(E entity, Session session);

    Optional<E> findById(K id, Session session);

    ArrayList<E> findAll(Session session);
}
