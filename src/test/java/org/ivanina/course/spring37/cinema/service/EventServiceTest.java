package org.ivanina.course.spring37.cinema.service;

import org.ivanina.course.sca.cinema.config.JdbcConfig;
import org.ivanina.course.sca.cinema.config.SpringAppConfig;
import org.ivanina.course.sca.cinema.config.WebAppConfig;
import org.ivanina.course.sca.cinema.domain.Event;
import org.ivanina.course.sca.cinema.service.EventService;
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
public class EventServiceTest {
    @Autowired
    @Qualifier("eventService")
    private EventService eventService;


    @Test
    public void getByNameTest(){
        Set<Event> eventList = eventService.getAll();
        Event event = eventList.stream().findFirst().get();

        Event eventByName = eventService.getByName( event.getName() );

        assertEquals(event.getName(),eventByName.getName());
        assertNotNull(eventByName.getId());
    }

    @Test
    public void addEventTest(){
        Event event = null;
        String name = "Test New Event";

        event = eventService.getByName(name);
        assertNull(event);

        event = new Event(name);
        Long id = eventService.save(event);

        event = eventService.getByName(name);
        assertNotNull(event);
        assertEquals(event.getName(),name);
        assertEquals(event.getId(),id);

    }

    @Test
    public void getSetBasePriceTest(){
        Event event = new Event("Harry Potter 2");
        event.setPrice(new BigDecimal(100.00));
        assertEquals(event.getPrice(), new BigDecimal(100.00));
    }


}
