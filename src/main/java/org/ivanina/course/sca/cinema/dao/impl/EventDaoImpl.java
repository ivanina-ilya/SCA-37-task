package org.ivanina.course.sca.cinema.dao.impl;

import org.ivanina.course.sca.cinema.dao.EventDao;
import org.ivanina.course.sca.cinema.domain.Event;
import org.ivanina.course.sca.cinema.domain.EventRating;
import org.ivanina.course.sca.cinema.utils.ServiceUtil;
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

public class EventDaoImpl  implements EventDao {
    private String table = "events";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Event getByName(String name) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM " + table + " WHERE name=?",
                    new Object[]{name},
                    new RowMapper<Event>() {
                        @Nullable
                        @Override
                        public Event mapRow(ResultSet resultSet, int i) throws SQLException {
                            return EventDaoImpl.mapRow(resultSet);
                        }
                    }
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Set<Event> getAll() {
        return new HashSet<>(jdbcTemplate.query("SELECT * FROM  " + table,
                new RowMapper<Event>() {
                    @Nullable
                    @Override
                    public Event mapRow(ResultSet resultSet, int i) throws SQLException {
                        return EventDaoImpl.mapRow(resultSet);
                    }
                }));
    }

    @Override
    public Long getCount() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM "+ table, new Object[]{}, Long.class) ;
    }


    @Override
    public Event get(Long id) {
        try {
            Event event = jdbcTemplate.queryForObject(
                    "SELECT * FROM " + table + " WHERE id=?",
                    new Object[]{id},
                    new RowMapper<Event>() {
                        @Nullable
                        @Override
                        public Event mapRow(ResultSet resultSet, int i) throws SQLException {
                            return EventDaoImpl.mapRow(resultSet);
                        }
                    }
            );
            return event;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
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
    public Boolean remove(Event entity) {
        if (entity.getId() == null) throw new IllegalArgumentException(
                String.format("Does not exist ID for Event with name %s", entity.getName()));
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


    public static Event mapRow(ResultSet resultSet) throws SQLException {
        return mapRow(resultSet, new Event(""));
    }

    public static Event mapRow(ResultSet resultSet, Event event) throws SQLException {
        if (resultSet == null) return null;
        if (event == null) event = new Event("");
        event.setId(resultSet.getLong("id"));
        event.setName(resultSet.getString("name"));
        String eventRating = resultSet.getString("rating");
        event.setRating(eventRating == null ? null : EventRating.valueOf(eventRating));
        event.setDuration(resultSet.getLong("duration"));
        event.setPrice(resultSet.getBigDecimal("price"));

        return event;
    }
}
