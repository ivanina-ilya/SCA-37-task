package org.ivanina.course.spring37.cinema.dao;

import org.ivanina.course.sca.cinema.config.JdbcConfig;
import org.ivanina.course.sca.cinema.config.SpringAppConfig;
import org.ivanina.course.sca.cinema.config.WebAppConfig;
import org.ivanina.course.sca.cinema.dao.EventScheduleDao;
import org.ivanina.course.sca.cinema.domain.EventSchedule;
import org.ivanina.course.sca.cinema.service.EventService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringAppConfig.class, JdbcConfig.class, WebAppConfig.class})
@WebAppConfiguration
public class EventScheduleDaoTest {

    @Autowired
    @Qualifier("eventScheduleDao")
    private EventScheduleDao eventScheduleDao;

    @Autowired
    @Qualifier("eventService")
    private EventService eventService;

    @Test
    public void getAllScheduleTest() {
        Set<EventSchedule> existSet = eventScheduleDao.getAll();
        assertNotNull(existSet);
    }

    @Test
    public void addDeleteEventScheduleTest(){
        Set<EventSchedule> existSet = eventScheduleDao.getAll();
        EventSchedule eventSchedule = eventService.createEventSchedule(1L, 1L, LocalDateTime.now() );
        eventScheduleDao.save(eventSchedule);
        Set<EventSchedule> newSet = eventScheduleDao.getAll();
        assertEquals(1,newSet.size() - existSet.size());
        eventScheduleDao.remove(eventSchedule);
    }

    @Test
    public void getAvailableEventScheduleTest(){
        Set<EventSchedule> existSet = eventScheduleDao.getAvailableEventSchedule();
        EventSchedule eventSchedule = eventService.createEventSchedule(1L, 1L, LocalDateTime.now().plusDays(1L) );
        eventScheduleDao.save(eventSchedule);
        Set<EventSchedule> newSet = eventScheduleDao.getAvailableEventSchedule();
        assertEquals(1,newSet.size() - (existSet == null ? 0 : existSet.size()) );
        eventScheduleDao.remove(eventSchedule);
    }
}
