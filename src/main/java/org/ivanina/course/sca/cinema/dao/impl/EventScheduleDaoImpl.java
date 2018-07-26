package org.ivanina.course.sca.cinema.dao.impl;

import org.ivanina.course.sca.cinema.dao.AuditoriumDao;
import org.ivanina.course.sca.cinema.dao.DaoAbstract;
import org.ivanina.course.sca.cinema.dao.EventDao;
import org.ivanina.course.sca.cinema.dao.EventScheduleDao;
import org.ivanina.course.sca.cinema.domain.EventSchedule;
import org.ivanina.course.sca.cinema.utils.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

public class EventScheduleDaoImpl extends DaoAbstract<EventSchedule> implements EventScheduleDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("eventDao")
    private EventDao eventDao;

    @Autowired
    @Qualifier("auditoriumDao")
    private AuditoriumDao auditoriumDao;

    public EventScheduleDaoImpl(String table) {
        super(table);
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
    public EventSchedule mapRow2(ResultSet resultSet, EventSchedule entity) throws SQLException {
        if (resultSet == null) return null;
        if (entity == null) entity = new EventSchedule(null, null, null);
        entity.setAuditorium( auditoriumDao.get( resultSet.getLong("auditorium_id") ) );
        entity.setEvent( eventDao.get(resultSet.getLong("event_id")) );
        entity.setStartDateTime(ServiceUtil.localDateTimeParse(resultSet.getString("startDateTime")));
        entity.setId(resultSet.getLong("id"));

        ServiceUtil.localDateTimeParse(resultSet.getString("startDateTime"));

        return entity;
    }




}
