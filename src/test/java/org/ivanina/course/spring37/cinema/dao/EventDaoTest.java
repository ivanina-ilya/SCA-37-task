package org.ivanina.course.spring37.cinema.dao;

import org.ivanina.course.sca.cinema.config.JdbcConfig;
import org.ivanina.course.sca.cinema.config.SpringAppConfig;
import org.ivanina.course.sca.cinema.config.WebAppConfig;
import org.ivanina.course.sca.cinema.dao.AuditoriumDao;
import org.ivanina.course.sca.cinema.dao.EventDao;
import org.ivanina.course.sca.cinema.domain.Event;
import org.ivanina.course.sca.cinema.domain.EventRating;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringAppConfig.class, JdbcConfig.class, WebAppConfig.class})
@WebAppConfiguration
public class EventDaoTest {

    @Autowired
    @Qualifier("eventDao")
    private EventDao eventDao;

    @Autowired
    @Qualifier("auditoriumDao")
    private AuditoriumDao auditoriumDao;


    @Test
    public void getEventByNameTest(){
        Event event = eventDao.getByName("Harry Potter and The Philosopher's Stone");
        assertNotNull(event);
    }

    @Test
    public void saveGetDeleteEventTest(){
        Set<Event> existEvents = eventDao.getAll();
        Event event = new Event("New Test Event");
        event.setDuration(1000L);
        event.setRating(EventRating.LOW);
        event.setPrice(new BigDecimal(10.10));
        eventDao.save(event);
        assertNotNull(event.getId());
        Long id = event.getId();
        assertEquals(1, eventDao.getAll().size() - (existEvents == null ? 0 : existEvents.size()) );
        Event newEvent = eventDao.get(id);
        assertNotNull(newEvent);
        eventDao.remove(id);
        assertNull( eventDao.get(id) );
    }

}
