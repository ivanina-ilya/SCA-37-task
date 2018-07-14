package org.ivanina.course.sca.cinema.dao;

import org.ivanina.course.sca.cinema.domain.Event;

public interface EventDao extends Dao<Event> {
    Event getByName(String name);

}

