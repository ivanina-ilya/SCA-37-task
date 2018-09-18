package org.ivanina.course.sca.cinema.dao.impl;

import org.ivanina.course.sca.cinema.dao.DaoAbstract;
import org.ivanina.course.sca.cinema.dao.UserDao;
import org.ivanina.course.sca.cinema.domain.User;
import org.ivanina.course.sca.cinema.utils.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDaoImpl extends DaoAbstract<User> implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserDaoImpl(String table) {
        super(table);
    }

    @Override
    public User getByEmail(String email) {
        return getEntityByQuery("SELECT * FROM " + table + " WHERE email=?", new Object[]{email});
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
                        "INSERT INTO " + table + " (firstName, lastName, email, birthday, roles, passwordHash) VALUES (?,?,?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                ServiceUtil.statementSetStringOrNull(statement, 1, entity.getFirstName());
                ServiceUtil.statementSetStringOrNull(statement, 2, entity.getLastName());
                ServiceUtil.statementSetStringOrNull(statement, 3, entity.getEmail());
                ServiceUtil.statementSetDateOrNull(statement, 4, birthdayDate);
                ServiceUtil.statementSetStringOrNull(statement, 5, entity.rolesToString());
                ServiceUtil.statementSetStringOrNull(statement, 6, entity.getPasswordHash());
                return statement;
            }, holder);
            entity.setId(holder.getKey().longValue());
        } else {
            rows = jdbcTemplate.update(
                    "UPDATE " + table + " SET firstName=?, lastName=?,email=?, birthday=?, ROLES=?, PASSWORDHASH=? WHERE id=?",
                    entity.getFirstName(), entity.getLastName(), entity.getEmail(),
                    birthdayDate,
                    entity.rolesToString(),
                    entity.getPasswordHash(),
                    entity.getId());
        }
        return rows == 0 ? null : entity.getId();
    }

    @Override
    public User mapRow2(ResultSet resultSet, User entity) throws SQLException {
        if (resultSet == null) return null;
        if (entity == null) entity = new User("", "");
        entity.setFirstName(resultSet.getString("firstName"));
        entity.setLastName(resultSet.getString("lastName"));
        entity.setEmail(resultSet.getString("email"));
        java.sql.Date birthdayDate = resultSet.getDate("birthday");
        if (birthdayDate != null)
            entity.setBirthday(birthdayDate.toLocalDate());
        entity.setRoles(resultSet.getString("roles"));
        entity.setPasswordHash(resultSet.getString("passwordHash"));
        entity.setId(resultSet.getLong("id"));
        return entity;
    }
}
