package org.ivanina.course.spring37.cinema.service;

import org.ivanina.course.sca.cinema.config.JdbcConfig;
import org.ivanina.course.sca.cinema.config.SpringAppConfig;
import org.ivanina.course.sca.cinema.config.WebAppConfig;
import org.ivanina.course.sca.cinema.dao.TicketDao;
import org.ivanina.course.sca.cinema.domain.*;
import org.ivanina.course.sca.cinema.service.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringAppConfig.class, JdbcConfig.class, WebAppConfig.class})
@WebAppConfiguration
public class BookingServiceTest {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    @Qualifier("discountService")
    private DiscountService discountService;

    @Autowired
    @Qualifier("eventService")
    private EventService eventService;

    @Autowired
    @Qualifier("auditoriumService")
    private AuditoriumService auditoriumService;

    @Autowired
    @Qualifier("bookingService")
    private BookingService bookingService;

    @Autowired
    @Qualifier("ticketDao")
    private TicketDao ticketDao;

    User user;
    Event event;
    Auditorium auditorium ;
    EventSchedule eventSchedule;

    @Before
    public void setup(){
        user = userService.get(1L);
        event = eventService.get(1L);
        auditorium = auditoriumService.get(1L);

        eventSchedule = new EventSchedule(event, auditorium, LocalDateTime.now().plusDays(1L));
        eventService.saveEventSchedule(eventSchedule);
    }

    @After
    public void after(){
        eventService.removeEventSchedule(eventSchedule);
    }

    @Test
    public void getTicketsPriceTest(){
        Set<Long> seats = new HashSet<>(Arrays.asList(15L,16L,17L) );
        assertEquals(event.getPrice(), bookingService.getTicketsPrice(user, eventSchedule, seats ));
    }

    @Test
    public void getTicketsPriceDiscountTest(){
        Set<Long> seats = new HashSet<>(Arrays.asList(15L,16L,17L,1L,2L,3L,4L,5L,6L,7L,8L) );
        BigDecimal difference =  event.getPrice().multiply( new BigDecimal(50) ).divide(new BigDecimal(100));
        assertEquals(
                event.getPrice().subtract(difference).setScale(2, RoundingMode.HALF_UP),
                bookingService.getTicketsPrice(user, eventSchedule, seats )
        );
    }

    @Test
    public void getPurchasedTicketTest(){
        Ticket ticket = new Ticket(null, user, eventSchedule, 15L, new BigDecimal(55.55));
        Ticket ticketBought = bookingService.bookTicket(user, eventSchedule, 1L );

        assertNotNull(ticketBought.getId());
        ticketDao.remove(ticketBought);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getPurchasedTicketDuplicateTest(){
        Ticket ticketBought = bookingService.bookTicket(user, eventSchedule, 1L );
        assertNotNull(ticketBought.getId());

        bookingService.bookTicket(user, eventSchedule, 1L );
    }

    @Test
    public void getAvailableSeatsTest() {
        Set<Long> availableSeats =  bookingService.getAvailableSeats(eventSchedule);
        bookingService.bookTicket(user, eventSchedule, 1L );

        assertEquals(
                availableSeats.size()-1,
                bookingService.getAvailableSeats(eventSchedule).size());
    }

    @Test
    public void isAvailableSeatsTest() {
        assertTrue( bookingService.isAvailableSeats(eventSchedule, 7L) );
        bookingService.bookTicket(user, eventSchedule, 7L );
        assertFalse(bookingService.isAvailableSeats(eventSchedule, 7L));
    }
}
