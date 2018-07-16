package org.ivanina.course.sca.cinema.service.impl;

import org.ivanina.course.sca.cinema.dao.AuditoriumDao;
import org.ivanina.course.sca.cinema.domain.Auditorium;
import org.ivanina.course.sca.cinema.service.AuditoriumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.Nullable;

import java.util.Set;

public class AuditoriumServiceImpl implements AuditoriumService {
    @Autowired
    @Qualifier("auditoriumDao")
    private AuditoriumDao auditoriumDao;

    @Nullable
    @Override
    public Auditorium getByName(String name) {
        return auditoriumDao.getByName(name);
    }

    @Override
    public Set<Auditorium> getAll() {
        return auditoriumDao.getAll();
    }

    @Override
    public Long getCount() {
        return auditoriumDao.getCount();
    }

    @Override
    public Auditorium get(Long id) {
        return auditoriumDao.get(id);
    }

    @Override
    public Long save(Auditorium entity) {
        return auditoriumDao.save(entity);
    }

    @Override
    public Boolean remove(Auditorium entity) {
        return auditoriumDao.remove(entity);
    }

    @Override
    public Boolean remove(Long id) {
        return auditoriumDao.remove(id);
    }

    @Override
    public Long getNextIncrement() {
        return null;
    }
}
