package org.ivanina.course.sca.cinema.dao.impl;

import org.ivanina.course.sca.cinema.dao.AuditoriumDao;
import org.ivanina.course.sca.cinema.dao.EventDao;
import org.ivanina.course.sca.cinema.dao.EventScheduleDao;
import org.ivanina.course.sca.cinema.domain.EventSchedule;
import org.ivanina.course.sca.cinema.utils.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

public class EventScheduleDaoImpl implements EventScheduleDao {
    private String table = "EventSchedule";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("eventDao")
    private EventDao eventDao;

    @Autowired
    @Qualifier("auditoriumDao")
    private AuditoriumDao auditoriumDao;

    @Override
    public Set<EventSchedule> getAll() {
        return getSet("SELECT * FROM  " + table, new Object[]{});
    }

    @Override
    public Long getCount() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM "+ table, new Object[]{}, Long.class) ;
    }

    @Override
    public EventSchedule get(Long id) {
        try {
            EventSchedule eventSchedule = jdbcTemplate.queryForObject(
                    "SELECT * FROM " + table + " WHERE id=?",
                    new Object[]{id},
                    new RowMapper<EventSchedule>() {
                        @Nullable
                        @Override
                        public EventSchedule mapRow(ResultSet resultSet, int i) throws SQLException {
                            return mapRow2(resultSet);
                        }
                    }
            );
            return eventSchedule;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Set<EventSchedule> getEventScheduleByEvent(Long eventId) {
        return getSet("SELECT * FROM  " + table + " WHERE event_id=? ", new Object[]{eventId});
    }

    @Override
    public Set<EventSchedule> getAvailableEventSchedule() {
        return getSet("SELECT * FROM  " + table + " WHERE startDateTime > NOW() ", new Object[]{});
    }

    @Override
    public Long save(EventSchedule entity) {
        int rows = 0;
        if (entity.getId() == null) {

            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            rows = jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO " + table + " (event_id, auditorium_id, startDateTime) VALUES (?,?,?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                statement.setLong(1, entity.getEvent().getId());
                statement.setLong(2, entity.getAuditorium().getId());
                ServiceUtil.statementSetDateTimeOrNull(statement, 3, entity.getStartDateTime());

                return statement;
            }, holder);
            entity.setId(holder.getKey().longValue());
        } else {
            rows = jdbcTemplate.update(
                    "UPDATE " + table + " SET event_id=?, auditorium_id=?,startDateTime=? WHERE id=?",
                    entity.getEvent().getId(),
                    entity.getAuditorium() != null ? entity.getAuditorium().getId() : null,
                    entity.getStartDateTime());
        }
        return rows == 0 ? null : entity.getId();
    }

    @Override
    public Boolean remove(EventSchedule entity) {
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

    public EventSchedule mapRow2(ResultSet resultSet) throws SQLException {
        return mapRow(resultSet, null);
    }

    public EventSchedule mapRow(ResultSet resultSet, EventSchedule eventSchedule) throws SQLException {
        if (resultSet == null) return null;
        if (eventSchedule == null) eventSchedule = new EventSchedule(null, null, null);
        eventSchedule.setAuditorium( auditoriumDao.get( resultSet.getLong("auditorium_id") ) );
        eventSchedule.setEvent( eventDao.get(resultSet.getLong("event_id")) );
        eventSchedule.setStartDateTime(ServiceUtil.localDateTimeParse(resultSet.getString("startDateTime")));
        eventSchedule.setId(resultSet.getLong("id"));

        ServiceUtil.localDateTimeParse(resultSet.getString("startDateTime"));

        return eventSchedule;
    }

    private Set<EventSchedule> getSet(String sql, Object[] args){
        return new HashSet<>(jdbcTemplate.query(sql,
                args,
                new RowMapper<EventSchedule>() {
                    @Nullable
                    @Override
                    public EventSchedule mapRow(ResultSet resultSet, int i) throws SQLException {
                        return mapRow2(resultSet);
                    }
                }));
    }

}
