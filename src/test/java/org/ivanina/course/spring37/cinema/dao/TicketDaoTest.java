package org.ivanina.course.spring37.cinema.dao;

import org.ivanina.course.sca.cinema.config.JdbcConfig;
import org.ivanina.course.sca.cinema.config.SpringAppConfig;
import org.ivanina.course.sca.cinema.config.WebAppConfig;
import org.ivanina.course.sca.cinema.dao.*;
import org.ivanina.course.sca.cinema.domain.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AbstractTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.NavigableSet;
import java.util.Set;
import java.util.logging.Logger;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringAppConfig.class, JdbcConfig.class, WebAppConfig.class})
@WebAppConfiguration
public class TicketDaoTest {
    final static Logger logger = Logger.getLogger(TicketDaoTest.class.getName());


    @Autowired
    @Qualifier("ticketDao")
    private TicketDao ticketDao;

    @Autowired
    @Qualifier("userDao")
    private UserDao userDao;

    @Autowired
    @Qualifier("eventDao")
    private EventDao eventDao;

    @Autowired
    @Qualifier("eventScheduleDao")
    private EventScheduleDao eventScheduleDao;

    @Autowired
    @Qualifier("auditoriumDao")
    private AuditoriumDao auditoriumDao;

    private User user;
    private Event event;
    private Auditorium auditorium ;
    private EventSchedule eventSchedule;


    @Before
    public void before(){
        user = userDao.getAll().stream().findFirst().get();
        event = eventDao.getAll().stream().findFirst().get();
        auditorium = auditoriumDao.getAll().stream().findFirst().get();

        eventSchedule = new EventSchedule(event, auditorium, LocalDateTime.now().plusDays(1L));
        eventScheduleDao.save(eventSchedule);

    }

    @After
    public void after(){
        eventScheduleDao.remove(eventSchedule);
    }

    @Test
    public void addTicketTest(){
        Long cnt = ticketDao.getCount();
        if(cnt == null) cnt = 0L;

        Ticket ticket = new Ticket(null, user, eventSchedule, 5L,
                new BigDecimal(55.55).setScale(2, RoundingMode.HALF_UP));
        Long id = ticketDao.save(ticket);

        assertNotNull(id);
        assertNotEquals(new Long(0), id);

        assertEquals(cnt+1, ticketDao.getCount().longValue() );

        ticketDao.remove(ticket);

        assertEquals(cnt, ticketDao.getCount());
        assertNull(ticket.getId());
    }

    @Test
    public void getTicketsByUser(){

        NavigableSet<Ticket> tickets = ticketDao.getTicketsByUser(user.getId());
        int cnt = tickets == null ? 0 : tickets.size();

        Ticket ticket = new Ticket(null, user, eventSchedule, 15L, new BigDecimal(100.00));
        ticketDao.save(ticket);

        assertEquals(cnt+1, ticketDao.getTicketsByUser(user.getId()).size());
        ticketDao.remove(ticket);
    }

}
