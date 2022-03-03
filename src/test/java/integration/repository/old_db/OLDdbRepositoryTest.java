package integration.repository.old_db;

import entity.olddb.BaseEntityOldDB;
import integration.repository.RepositoryTestInterface;

import java.io.Serializable;
import java.util.ArrayList;

public interface OLDdbRepositoryTest <K extends Serializable, E extends BaseEntityOldDB<K>> extends RepositoryTestInterface<K, E> {
    @Override
    void update();

    @Override
    void save();

    @Override
    void delete();

    @Override
    void findById();

    @Override
    void findAll();

    @Override
    E createEntity();

    @Override
    E returnsFirstInstanceFromDB();

    @Override
    boolean isDBEmpty();

    @Override
    boolean isDBNotEmpty();

    @Override
    ArrayList<E> findAllEntities();
}
