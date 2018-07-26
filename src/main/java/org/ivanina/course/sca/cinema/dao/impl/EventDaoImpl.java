package org.ivanina.course.sca.cinema.dao.impl;

import org.ivanina.course.sca.cinema.dao.DaoAbstract;
import org.ivanina.course.sca.cinema.dao.EventDao;
import org.ivanina.course.sca.cinema.domain.Event;
import org.ivanina.course.sca.cinema.domain.EventRating;
import org.ivanina.course.sca.cinema.utils.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EventDaoImpl extends DaoAbstract<Event> implements EventDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public EventDaoImpl(String table) {
        super(table);
    }

    @Override
    public Event getByName(String name) {
        return getEntityByQuery("SELECT * FROM " + table + " WHERE name=?", new Object[]{name});
    }

    @Override
    public Long save(Event entity) {
        int rows = 0;
        if (entity.getId() == null) {

            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            rows = jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO " + table + " (NAME, RATING, PRICE, DURATION) VALUES (?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                statement.setString(1, entity.getName());
                ServiceUtil.statementSetStringOrNull(statement, 2, entity.getRating() != null ? entity.getRating().name() : null);
                ServiceUtil.statementSetBigDecimalOrNull(statement, 3, entity.getPrice());
                ServiceUtil.statementSetLongOrNull(statement, 4, entity.getDuration());
                return statement;
            }, holder);
            entity.setId(holder.getKey().longValue());
        } else {
            rows = jdbcTemplate.update(
                    "UPDATE " + table + " SET NAME=?, RATING=?,PRICE=?, DURATION=? WHERE id=?",
                    entity.getName(),
                    entity.getRating() != null ? entity.getRating().name() : null,
                    entity.getPrice(),
                    entity.getDuration(),
                    entity.getId());
        }
        return rows == 0 ? null : entity.getId();
    }


    @Override
    public Event mapRow2(ResultSet resultSet, Event entity) throws SQLException {
        if (resultSet == null) return null;
        if (entity == null) entity = new Event("");
        entity.setId(resultSet.getLong("id"));
        entity.setName(resultSet.getString("name"));
        String eventRating = resultSet.getString("rating");
        entity.setRating(eventRating == null ? null : EventRating.valueOf(eventRating));
        entity.setDuration(resultSet.getLong("duration"));
        entity.setPrice(resultSet.getBigDecimal("price"));

        return entity;
    }

}
