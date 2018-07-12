package org.ivanina.course.sca.cinema.service.impl;

import org.ivanina.course.sca.cinema.dao.EventDao;
import org.ivanina.course.sca.cinema.domain.Event;
import org.ivanina.course.sca.cinema.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class EventServiceImpl implements EventService {

    @Autowired
    @Qualifier("eventDao")
    private EventDao eventDao;


    @Nullable
    @Override
    public Event getByName(String name) {
        return eventDao.getByName(name);
    }


    @Override
    public Set<Event> getAll() {
        return eventDao.getAll();
    }

    @Override
    public Set<Event> getAllFull() {
        return getAll().stream()
                .map(event -> get(event.getId()))
                .collect(Collectors.toSet());
    }

    @Override
    public Event get(Long id) {
        return eventDao.get(id);
    }

    @Override
    public Long save(Event entity) {
        return eventDao.save(entity);
    }

    @Override
    public Boolean remove(Event entity) {
        return eventDao.remove(entity);
    }

    @Override
    public Boolean remove(Long id) {
        return eventDao.remove(id);
    }

    @Override
    public Long getNextIncrement() {
        return null;
    }
}
