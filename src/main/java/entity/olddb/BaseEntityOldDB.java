package entity.olddb;

import entity.BaseEntity;

import java.io.Serializable;

public interface BaseEntityOldDB<K extends Serializable> extends BaseEntity<K> {
    @Override
    void setId(K id);

    @Override
    K getId();
}
