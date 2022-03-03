package integration.repository;

import entity.BaseEntity;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

public interface RepositoryTestInterface <K extends Serializable, E extends BaseEntity<K>>{

    @Test
    void save ();

    @Test
    void delete();

    @Test
    void update();

    @Test
    void findById();

    @Test
    void findAll();

    E createEntity();

    E returnsFirstInstanceFromDB();

    boolean isDBEmpty();

    boolean isDBNotEmpty();

    ArrayList<E> findAllEntities();
}