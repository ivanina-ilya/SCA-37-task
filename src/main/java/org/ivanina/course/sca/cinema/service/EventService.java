package org.ivanina.course.sca.cinema.service;

import org.ivanina.course.sca.cinema.dao.Dao;
import org.ivanina.course.sca.cinema.domain.Event;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Set;

public interface EventService extends Dao<Event> {
    @Nullable
    Event getByName(@NonNull String name);

    Set<Event> getAllFull();
}
