package org.ivanina.course.spring37.cinema.service;

import org.ivanina.course.sca.cinema.config.JdbcConfig;
import org.ivanina.course.sca.cinema.config.SpringAppConfig;
import org.ivanina.course.sca.cinema.config.WebAppConfig;
import org.ivanina.course.sca.cinema.domain.Event;
import org.ivanina.course.sca.cinema.domain.EventRating;
import org.ivanina.course.sca.cinema.domain.EventSchedule;
import org.ivanina.course.sca.cinema.domain.User;
import org.ivanina.course.sca.cinema.service.AuditoriumService;
import org.ivanina.course.sca.cinema.service.Discount;
import org.ivanina.course.sca.cinema.service.DiscountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringAppConfig.class, JdbcConfig.class, WebAppConfig.class})
@WebAppConfiguration
public class DiscountServiceTest {
    @Autowired
    @Qualifier("discountService")
    private DiscountService discountService;

    @Autowired
    @Qualifier("auditoriumService")
    private AuditoriumService auditoriumService;

    private User user;
    private Event event;
    private LocalDateTime dateTime;
    private EventSchedule eventSchedule;



    @Before
    public void setup(){
        dateTime = LocalDateTime.now().plusDays(1L);
        user = new User("John","test", "test@test.com");
        event = new Event("Test Event");
        event.setPrice( new BigDecimal(100.0 ));
        event.setRating(EventRating.HIGH);
        eventSchedule = new EventSchedule(event,auditoriumService.getByName("Gold"),dateTime);
    }

    @Test
    public void testDiscountNoDiscount(){
        Discount discount = discountService.getDiscount(user,eventSchedule,5);
        if(discount.isByWinner()) assertEquals(100,discount.getPercent());
        else assertEquals(0,discount.getPercent());
    }

    @Test
    public void testDiscountByBirthday(){
        user.setBirthday( dateTime.toLocalDate() );
        Discount discount = discountService.getDiscount(user,eventSchedule,5);
        if(discount.isByWinner()) assertEquals(100,discount.getPercent());
        else assertEquals(10,discount.getPercent());
        user.setBirthday( dateTime.toLocalDate().minusYears(10L) );
    }

    @Test
    public void testDiscountByCoutn(){
        Discount discount = discountService.getDiscount(user,eventSchedule,15);
        if(discount.isByWinner()) assertEquals(100,discount.getPercent());
        else assertEquals(50,discount.getPercent());
    }

    @Test
    public void testDiscountByMultyDiscount(){
        user.setBirthday( dateTime.toLocalDate() );
        Discount discount = discountService.getDiscount(user,eventSchedule,15);
        if(discount.isByWinner()) assertEquals(100,discount.getPercent());
        else assertEquals(50,discount.getPercent());
        user.setBirthday( dateTime.toLocalDate().minusYears(10L) );
    }
}
