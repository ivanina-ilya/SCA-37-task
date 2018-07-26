package org.ivanina.course.sca.cinema.dao.impl;

import org.ivanina.course.sca.cinema.dao.AuditoriumDao;
import org.ivanina.course.sca.cinema.dao.DaoAbstract;
import org.ivanina.course.sca.cinema.domain.Auditorium;
import org.ivanina.course.sca.cinema.domain.EventSchedule;
import org.ivanina.course.sca.cinema.utils.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

public class AuditoriumDaoImpl extends DaoAbstract<Auditorium> implements AuditoriumDao {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public AuditoriumDaoImpl(String table) {
        super(table);
    }

    @Override
    public Auditorium getByName(String name) {
        return getEntityByQuery("SELECT * FROM " + table + " WHERE name=?", new Object[]{name});
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
                ServiceUtil.statementSetLongOrNull(statement, 2, entity.getSeats());
                ServiceUtil.statementSetStringOrNull(statement, 3, entity.vipSeatsToString());
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

    @Override
    public Auditorium mapRow2(ResultSet resultSet, Auditorium entity) throws SQLException {
        if (resultSet == null) return null;
        if (entity == null) entity = new Auditorium("");

        entity.setId(resultSet.getLong("id"));
        entity.setName(resultSet.getString("name"));
        entity.setSeats(resultSet.getLong("seats"));
        entity.setVipSeats(resultSet.getString("vipSeats") != null ?
                Auditorium.vipSeatsParse(resultSet.getString("vipSeats")) :
                null);
        return entity;
    }



}
