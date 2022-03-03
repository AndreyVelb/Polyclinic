package repository;

import entity.BaseEntity;

import org.postgresql.util.PSQLException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public interface Repository <K extends Serializable, E extends BaseEntity<K>>{
    E save (E entity) throws PSQLException;

    void delete(K id);

    void update(E entity);

    Optional<E> findById(K id);

    ArrayList<E> findAll();
}
