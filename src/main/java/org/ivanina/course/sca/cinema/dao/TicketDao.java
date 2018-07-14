package org.ivanina.course.sca.cinema.dao;

import org.ivanina.course.sca.cinema.domain.EventSchedule;
import org.ivanina.course.sca.cinema.domain.Ticket;
import org.ivanina.course.sca.cinema.domain.User;

import java.util.NavigableSet;

public interface TicketDao extends Dao<Ticket> {
    NavigableSet<Ticket> getTicketsByUser(Long userId);

    NavigableSet<Ticket> getTicketsByUserForEvent(User user, EventSchedule eventSchedule);

    NavigableSet<Ticket> getTicketsByEvent(EventSchedule eventSchedule);
}
