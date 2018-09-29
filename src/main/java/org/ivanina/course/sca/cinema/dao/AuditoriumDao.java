package org.ivanina.course.sca.cinema.dao;

import org.ivanina.course.sca.cinema.domain.Auditorium;
import org.ivanina.course.sca.cinema.domain.EventSchedule;

import java.util.Set;

public interface AuditoriumDao extends Dao<Auditorium> {
    Auditorium getByName(String name);

    Set<Long> getReservedSeats(EventSchedule eventSchedule);
}
