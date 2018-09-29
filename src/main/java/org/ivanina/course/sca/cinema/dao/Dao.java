package org.ivanina.course.sca.cinema.dao;

import java.util.Set;

public interface Dao<T> {

    Set<T> getAll();

    Long getCount();

    T get(Long id);

    Long save(T entity);

    Boolean remove(T entity);

    Boolean remove(Long id);

    Long getNextIncrement();


}
