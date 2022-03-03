package entity.newdb;

import entity.BaseEntity;

import java.io.Serializable;

public interface BaseEntityNewDB <K extends Serializable> extends BaseEntity<K>{
    @Override
    void setId(K id);

    @Override
    K getId();
}
