package org.example.dao;

import java.util.List;

public interface IDAO<T, D> {

    T read(D d);
    List<T> readAll();
    T create(T t);
    T update(D d, T t);
    void delete(D d);
    boolean validatePrimaryKey(D d);

}
