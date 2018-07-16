package org.ivanina.course.sca.cinema.dao;

import org.ivanina.course.sca.cinema.domain.EventSchedule;

import java.util.Set;

public interface EventScheduleDao extends Dao<EventSchedule> {
    Set<EventSchedule> getEventScheduleByEvent(Long eventId);

    Set<EventSchedule> getAvailableEventSchedule();
}
