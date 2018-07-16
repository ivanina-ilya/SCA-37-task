package org.ivanina.course.sca.cinema.dao.impl;

import org.ivanina.course.sca.cinema.dao.UserDao;
import org.ivanina.course.sca.cinema.domain.User;
import org.ivanina.course.sca.cinema.service.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.lang.Nullable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class UserDaoImpl implements UserDao {
    private String table = "users";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Set<User> getAll() {
        return new HashSet<>(jdbcTemplate.query("SELECT * FROM  " + table,
                new RowMapper<User>() {
                    @Nullable
                    @Override
                    public User mapRow(ResultSet resultSet, int i) throws SQLException {
                        return UserDaoImpl.mapRow(resultSet);
                    }
                }));
    }

    @Override
    public Long getCount() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM "+ table, new Object[]{}, Long.class) ;
    }

    @Override
    public User get(Long id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM " + table + " WHERE id=?",
                    new Object[]{id},
                    new RowMapper<User>() {
                        @Nullable
                        @Override
                        public User mapRow(ResultSet resultSet, int i) throws SQLException {
                            return UserDaoImpl.mapRow(resultSet);
                        }
                    }
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public User getByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM " + table + " WHERE email=?",
                    new Object[]{email},
                    new RowMapper<User>() {
                        @Nullable
                        @Override
                        public User mapRow(ResultSet resultSet, int i) throws SQLException {
                            return UserDaoImpl.mapRow(resultSet);
                        }
                    }
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Long save(User entity) {
        int rows = 0;
        java.sql.Date birthdayDate = entity.getBirthday() != null ?
                java.sql.Date.valueOf(entity.getBirthday()) : null;
        if (entity.getId() == null) {

            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            rows = jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO " + table + " (firstName, lastName, email, birthday) VALUES (?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                Util.statementSetStringOrNull(statement, 1, entity.getFirstName());
                Util.statementSetStringOrNull(statement, 2, entity.getLastName());
                Util.statementSetStringOrNull(statement, 3, entity.getEmail());
                Util.statementSetDateOrNull(statement, 4, birthdayDate);
                return statement;
            }, holder);
            entity.setId(holder.getKey().longValue());
        } else {
            rows = jdbcTemplate.update("UPDATE " + table + " SET firstName=?, lastName=?,email=?, birthday=? WHERE id=?",
                    entity.getFirstName(), entity.getLastName(), entity.getEmail(),
                    birthdayDate,
                    entity.getId());
        }
        return rows == 0 ? null : entity.getId();
    }

    @Override
    public Boolean remove(User entity) {
        if (entity.getId() == null) throw new IllegalArgumentException(
                String.format("Does not exist ID for User with email %s", entity.getEmail()));
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

    @Override
    public Long getNextIncrement() {
        return null;
    }

    public static User mapRow(ResultSet resultSet) throws SQLException {
        return mapRow(resultSet, new User("", ""));
    }

    public static User mapRow(ResultSet resultSet, User user) throws SQLException {
        if (resultSet == null) return null;
        if (user == null) user = new User("", "");
        user.setFirstName(resultSet.getString("firstName"));
        user.setLastName(resultSet.getString("lastName"));
        user.setEmail(resultSet.getString("email"));
        java.sql.Date birthdayDate = resultSet.getDate("birthday");
        if (birthdayDate != null)
            user.setBirthday(birthdayDate.toLocalDate());
        user.setId(resultSet.getLong("id"));
        return user;
    }
}
