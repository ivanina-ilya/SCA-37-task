package org.ivanina.course.sca.cinema.service;

import org.ivanina.course.sca.cinema.dao.Dao;
import org.ivanina.course.sca.cinema.domain.Auditorium;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface AuditoriumService extends Dao<Auditorium> {
    public @Nullable
    Auditorium getByName(@NonNull String name);
}
