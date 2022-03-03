package integration.repository.newdb;

import entity.newdb.BaseEntityNewDB;
import integration.repository.RepositoryTestInterface;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.ArrayList;


public interface NEWdbRepositoryTest <K extends Serializable, E extends BaseEntityNewDB<K>> extends RepositoryTestInterface<K, E> {
    @Test
    @Override
    void update();

    @Test
    @Override
    E createEntity();

    @Test
    @Override
    void save();

    @Test
    @Override
    void delete();

    @Test
    @Override
    void findById();

    @Test
    @Override
    void findAll();

    @Override
    E returnsFirstInstanceFromDB();

    @Override
    boolean isDBEmpty();

    @Override
    boolean isDBNotEmpty();

    @Override
    ArrayList<E> findAllEntities();
}
