package org.ivanina.course.sca.cinema.dao.impl;

import org.ivanina.course.sca.cinema.dao.AuditoriumDao;
import org.ivanina.course.sca.cinema.domain.Auditorium;
import org.ivanina.course.sca.cinema.domain.EventSchedule;
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

public class AuditoriumDaoImpl implements AuditoriumDao {
    private String table = "Auditoriums";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Auditorium getByName(String name) {
        try {
            return getSet("SELECT * FROM " + table + " WHERE name=?",
                    new Object[]{name})
                    .stream().findFirst().orElse(null);
            /*return jdbcTemplate.queryForObject(
                    "SELECT * FROM " + table + " WHERE name=?",
                    new Object[]{name},
                    new RowMapper<Auditorium>() {
                        @Nullable
                        @Override
                        public Auditorium mapRow(ResultSet resultSet, int i) throws SQLException {
                            return mapRow(resultSet);
                        }
                    }
            );*/
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Auditorium get(Long auditoriumId) {
        try {
            return getSet("SELECT * FROM " + table + " WHERE id=?",
                    new Object[]{auditoriumId}).stream().findFirst().get();
            /*return jdbcTemplate.queryForObject(
                    "SELECT * FROM " + table + " WHERE id=?",
                    new Object[]{auditoriumId},
                    new RowMapper<Auditorium>() {
                        @Nullable
                        @Override
                        public Auditorium mapRow(ResultSet resultSet, int i) throws SQLException {
                            return mapRow(resultSet);
                        }
                    }
            );*/
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    @Override
    public Set<Auditorium> getAll() {
        return getSet("SELECT * FROM  " + table, new Object[]{});
        /*return new HashSet<>(jdbcTemplate.query("SELECT * FROM  " + table,
                new RowMapper<Auditorium>() {
                    @Nullable
                    @Override
                    public Auditorium mapRow(ResultSet resultSet, int i) throws SQLException {
                        return mapRow(resultSet);
                    }
                }));*/
    }

    @Override
    public Long getCount() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM "+ table, new Object[]{}, Long.class) ;
    }

    @Override
    public Long save(Auditorium entity) {
        int rows = 0;
        if (entity.getId() == null) {

            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            rows = jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO " + table + " (name, seats, vipSeats) VALUES (?,?,?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                statement.setString(1, entity.getName());
                Util.statementSetLongOrNull(statement, 2, entity.getSeats());
                Util.statementSetStringOrNull(statement, 3, entity.vipSeatsToString());
                return statement;
            }, holder);
            entity.setId(holder.getKey().longValue());
        } else {
            rows = jdbcTemplate.update(
                    "UPDATE " + table + " SET name=?, seats=?, vipSeats=? WHERE id=?",
                    entity.getName(),
                    entity.getSeats(),
                    entity.vipSeatsToString(),
                    entity.getId());
        }
        return rows == 0 ? null : entity.getId();
    }


    @Override
    public Boolean remove(Auditorium entity) {
        if (entity.getId() == null) throw new IllegalArgumentException(
                String.format("Does not exist ID for Auditorium with name %s", entity.getName()));
        return remove(entity.getId());
    }

    @Override
    public Boolean remove(Long id) {
        return jdbcTemplate.update("DELETE FROM " + table + " WHERE id = ? ", id) != 0;
    }

    @Override
    public Long getNextIncrement() {
        return null;
    }


    @Override
    public Set<Long> getReservedSeats(EventSchedule eventSchedule) {
        return new HashSet<>(jdbcTemplate.query("SELECT SEAT FROM TICKETS WHERE EVENTSCHEDULE_ID=?",
                new Object[]{eventSchedule.getId()},
                new RowMapper<Long>() {
                    @Nullable
                    @Override
                    public Long mapRow(ResultSet resultSet, int i) throws SQLException {
                        return resultSet.getLong("SEAT");
                    }
                }));
    }


    public Auditorium mapRow2(ResultSet resultSet) throws SQLException {
        return mapRow(resultSet, null);
    }

    public Auditorium mapRow(ResultSet resultSet, Auditorium auditorium) throws SQLException {
        if (resultSet == null) return null;
        if (auditorium == null) auditorium = new Auditorium("");

        auditorium.setId(resultSet.getLong("id"));
        auditorium.setName(resultSet.getString("name"));
        auditorium.setSeats(resultSet.getLong("seats"));
        auditorium.setVipSeats(resultSet.getString("vipSeats") != null ?
                Auditorium.vipSeatsParse(resultSet.getString("vipSeats")) :
                null);
        return auditorium;
    }

    private Set<Auditorium> getSet(String sql, Object[] args){
        return new HashSet<>(jdbcTemplate.query(sql,
                args,
                new RowMapper<Auditorium>() {
                    @Nullable
                    @Override
                    public Auditorium mapRow(ResultSet resultSet, int i) throws SQLException {
                        return mapRow2(resultSet);
                    }
                }));
    }


}
