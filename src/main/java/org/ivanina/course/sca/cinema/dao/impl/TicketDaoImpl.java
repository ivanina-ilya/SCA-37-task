package org.ivanina.course.sca.cinema.dao.impl;

import org.ivanina.course.sca.cinema.dao.EventDao;
import org.ivanina.course.sca.cinema.dao.EventScheduleDao;
import org.ivanina.course.sca.cinema.dao.TicketDao;
import org.ivanina.course.sca.cinema.dao.UserDao;
import org.ivanina.course.sca.cinema.domain.EventSchedule;
import org.ivanina.course.sca.cinema.domain.Ticket;
import org.ivanina.course.sca.cinema.domain.User;
import org.ivanina.course.sca.cinema.utils.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class TicketDaoImpl implements TicketDao {
    private String table = "Tickets";
    private String tablePreSave = "TICKETS_PREBOOK";
    private String tableUser = "Users";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("eventDao")
    private EventDao eventDao;

    @Autowired
    @Qualifier("eventScheduleDao")
    private EventScheduleDao eventScheduleDao;

    @Autowired
    @Qualifier("userDao")
    private UserDao userDao;

    @Override
    public NavigableSet<Ticket> getTicketsByUser(Long userId) {
        try {
            return new TreeSet<Ticket>(
                    jdbcTemplate.queryForList(
                            "SELECT * FROM  " + table + " WHERE user_id=?",
                            new Object[]{userId}).stream()
                            .map(row -> getTicket((Map) row))
                            .collect(Collectors.toSet()
                            ));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    @Override
    public NavigableSet<Ticket> getTicketsByUserForEvent(User user, EventSchedule eventSchedule) {
        try {
            return new TreeSet<Ticket>(
                    jdbcTemplate.queryForList(
                            "SELECT t.* FROM  " + table + " t " +
                                    " RIGHT JOIN "+tableUser+" u ON u.id=t.user_id " +
                                    " WHERE eventSchedule_id=? AND u.id=? ",
                            new Object[]{
                                    eventSchedule.getId(),
                                    user.getId()
                            }).stream()
                            .map(row -> getTicket((Map) row))
                            .collect(Collectors.toSet()
                            ));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    @Override
    public NavigableSet<Ticket> getTicketsByEvent(EventSchedule eventSchedule) {
        try {
            return new TreeSet<Ticket>(
                    jdbcTemplate.queryForList(
                            "SELECT * FROM  " + table + " WHERE eventSchedule_id=?",
                            new Object[]{
                                    eventSchedule.getId()
                            }).stream()
                            .map(row -> getTicket((Map) row))
                            .collect(Collectors.toSet()
                            ));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Set<Ticket> getAll() {
        return new HashSet<>(jdbcTemplate.query("SELECT * FROM  " + table,
                new RowMapper<Ticket>() {
                    @Nullable
                    @Override
                    public Ticket mapRow(ResultSet resultSet, int i) throws SQLException {
                        if (resultSet == null) return null;
                        return getTicket(resultSet);
                    }
                }));
    }

    @Override
    public Long getCount() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM "+ table, new Object[]{}, Long.class) ;
    }

    @Override
    public Ticket get(Long id) {
        return get(id, table);
    }

    @Override
    public Ticket getPreBookingTicket(Long id) {
        return get(id, tablePreSave);
    }

    public Ticket get(Long id, String table) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM " + table + " WHERE id=?",
                    new Object[]{id},
                    new RowMapper<Ticket>() {
                        @Nullable
                        @Override
                        public Ticket mapRow(ResultSet resultSet, int i) throws SQLException {
                            return getTicket(resultSet);
                        }
                    }
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    @Override
    public Long save(Ticket entity) {
        return save(entity, table);
    }

    @Override
    public Long preBookSave(Ticket ticket) {
        return save(ticket, tablePreSave);
    }

    private Long save(Ticket entity, String table) {
        int rows = 0;
        if (entity.getId() == null) {

            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            rows = jdbcTemplate.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO " + table + " (user_id, eventSchedule_id, seat, price, added) VALUES (?,?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                ServiceUtil.statementSetLongOrNull(statement, 1, entity.getUser() != null ? entity.getUser().getId() : null);
                ServiceUtil.statementSetLongOrNull(statement, 2, entity.getEventSchedule().getId());
                ServiceUtil.statementSetLongOrNull(statement, 3, entity.getSeat());
                ServiceUtil.statementSetBigDecimalOrNull(statement, 4, entity.getPrice());
                ServiceUtil.statementSetDateTimeOrNull(statement, 5, LocalDateTime.now());
                return statement;
            }, holder);
            entity.setId(holder.getKey().longValue());
        } else {
            rows = jdbcTemplate.update("UPDATE " + table + " SET user_id=?, eventSchedule_id=?, seat=?, price=? WHERE id=?",
                    entity.getUser() != null ? entity.getUser().getId() : null,
                    entity.getEventSchedule().getId(),
                    entity.getSeat(),
                    entity.getPrice(),
                    entity.getId());
        }
        return rows == 0 ? null : entity.getId();
    }

    @Override
    public Boolean remove(Ticket entity) {
        if (entity.getId() == null) throw new IllegalArgumentException(
                String.format("Does not exist ID for Ticket (User ID: %d, Event ID: %d, Date: %s",
                        entity.getUser() == null ? null : entity.getUser().getId(),
                        entity.getEventSchedule() == null ? null : entity.getEventSchedule().getEvent() == null ? null :
                                entity.getEventSchedule().getEvent(),
                        entity.getEventSchedule() == null ? null : entity.getEventSchedule().getStartDateTime() ) );
        if (remove(entity.getId())) {
            entity.setId(null);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean remove(Long id) {
        return remove(id, table);
    }

    private Boolean remove(Long id, String table){
        int rows = jdbcTemplate.update("DELETE FROM " + table + " WHERE id = ? ", id);
        return rows == 0 ? false : true;
    }

    @Override
    public Boolean removePreBookingTicket(Ticket ticket) {
        return remove(ticket.getId(), tablePreSave);
    }

    @Override
    public Long getNextIncrement() {
        return null;
    }


    private Ticket getTicket(ResultSet resultSet) throws SQLException {
        if (resultSet == null) return null;
        User user = userDao.get(resultSet.getLong("user_id"));
        EventSchedule eventSchedule = eventScheduleDao.get(resultSet.getLong("eventSchedule_id"));
        return new Ticket(
                resultSet.getLong("id"),
                user,
                eventSchedule,
                resultSet.getLong("seat"),
                resultSet.getBigDecimal("price")
        );
    }

    private Ticket getTicket(Map row) {
        if (row == null) return null;
        User user = userDao.get(Long.parseLong(row.get("user_id").toString()));
        EventSchedule eventSchedule = eventScheduleDao.get(Long.parseLong(row.get("eventSchedule_id").toString()));
        return new Ticket(
                Long.parseLong(row.get("id").toString()),
                user,
                eventSchedule,
                row.get("seat") != null ? Long.parseLong(row.get("seat").toString()) : null,
                row.get("price") != null ? new BigDecimal(row.get("price").toString()) : null
        );
    }
}
