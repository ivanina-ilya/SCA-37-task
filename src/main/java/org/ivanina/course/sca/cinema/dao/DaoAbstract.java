package org.ivanina.course.sca.cinema.dao;

import org.ivanina.course.sca.cinema.domain.DomainObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public abstract class DaoAbstract<T extends DomainObject> implements Dao<T> {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    protected final String table;


    public DaoAbstract(String table) {
        this.table = table;
    }

    @Override
    public Long getCount() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM " + table, new Object[]{}, Long.class);
    }

    @Override
    public Set<T> getAll() {
        return new HashSet<>(jdbcTemplate.query("SELECT * FROM  " + table,
                new RowMapper<T>() {
                    @Nullable
                    @Override
                    public T mapRow(ResultSet resultSet, int i) throws SQLException {
                        return mapRow2(resultSet);
                    }
                }));
    }

    @Override
    public T get(Long id) {
        return getEntityByQuery("SELECT * FROM " + table + " WHERE id=?", new Object[]{id});
    }

    public T getEntityByQuery(String sql, Object[] args) {
        try {
            T entity = jdbcTemplate.queryForObject(
                    sql,
                    args,
                    new RowMapper<T>() {
                        @Nullable
                        @Override
                        public T mapRow(ResultSet resultSet, int i) throws SQLException {
                            return mapRow2(resultSet);
                        }
                    }
            );
            return entity;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    @Override
    public Boolean remove(T entity) {
        if (entity.getId() == null) throw new IllegalArgumentException("Entity's ID cannot be NULL");
        if (remove(entity.getId())) {
            entity.setId(null);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean remove(Long id) {
        int rows = jdbcTemplate.update("DELETE FROM " + table + " WHERE id = ? ", id);
        return rows == 0 ? false : true;
    }

    public abstract T mapRow2(ResultSet resultSet, T entity) throws SQLException;

    public T mapRow2(ResultSet resultSet) throws SQLException {
        return mapRow2(resultSet, null);
    }

    public Set<T> getSet(String sql, Object[] args) {
        return new HashSet<>(jdbcTemplate.query(
                sql,
                args,
                new RowMapper<T>() {
                    @Nullable
                    @Override
                    public T mapRow(ResultSet resultSet, int i) throws SQLException {
                        return mapRow2(resultSet);
                    }
                }));
    }

    @Override
    public Long getNextIncrement() {
        return null;
    }

}
