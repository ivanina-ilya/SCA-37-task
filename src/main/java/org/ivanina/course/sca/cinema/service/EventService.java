package org.ivanina.course.sca.cinema.service;

import org.ivanina.course.sca.cinema.dao.Dao;
import org.ivanina.course.sca.cinema.domain.Event;
import org.ivanina.course.sca.cinema.domain.EventSchedule;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


public interface EventService extends Dao<Event> {
    @Nullable
    Event getByName(@NonNull String name);

    Set<Event> getAllFull();

    EventSchedule createEventSchedule(Long eventId, Long auditoriumId, LocalDateTime dateTime);

    Long saveEventSchedule(EventSchedule eventSchedule);

    Boolean removeEventSchedule(EventSchedule eventSchedule);

    Set<LocalDateTime> getAvailableEventDate(Event event);

    Set<EventSchedule> getAvailableEventSchedule();

    Set<Event> getAvailableEvents();

    Set<Event> getArchiveEvents();

    EventSchedule getEventSchedule(Long id);

    void updateOrInsertEvents(@NonNull List<Event> events);
}
