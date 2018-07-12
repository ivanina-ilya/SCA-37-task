package org.ivanina.course.sca.cinema.dao;

import org.ivanina.course.sca.cinema.domain.EventSchedule;
import org.ivanina.course.sca.cinema.domain.Ticket;

import java.time.LocalDateTime;
import java.util.NavigableSet;

public interface TicketDao extends Dao<Ticket> {
    NavigableSet<Ticket> getTicketsByUser(Long userId);

    NavigableSet<Ticket> getTicketsByEvent(Long eventId, LocalDateTime dateTime);

    NavigableSet<Ticket> getTicketsByEvent(EventSchedule eventSchedule);
}
