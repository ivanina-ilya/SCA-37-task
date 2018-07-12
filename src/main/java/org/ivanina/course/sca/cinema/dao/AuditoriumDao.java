package org.ivanina.course.sca.cinema.dao;

import org.ivanina.course.sca.cinema.domain.Auditorium;

public interface AuditoriumDao extends Dao<Auditorium> {
    Auditorium getByName(String name);
}
