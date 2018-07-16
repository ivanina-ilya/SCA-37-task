package org.ivanina.course.spring37.cinema.service;

import org.ivanina.course.sca.cinema.config.JdbcConfig;
import org.ivanina.course.sca.cinema.config.SpringAppConfig;
import org.ivanina.course.sca.cinema.config.WebAppConfig;
import org.ivanina.course.sca.cinema.domain.Event;
import org.ivanina.course.sca.cinema.domain.EventRating;
import org.ivanina.course.sca.cinema.domain.EventSchedule;
import org.ivanina.course.sca.cinema.domain.User;
import org.ivanina.course.sca.cinema.service.AuditoriumService;
import org.ivanina.course.sca.cinema.service.DiscountService;
import org.ivanina.course.sca.cinema.service.EventService;
import org.junit.After;
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


    @Test
    public void getDiscountTest(){
        LocalDateTime dateTime = LocalDateTime.now().plusDays(1L);
        User user = new User("John","test", "test@test.com");
        Event event = new Event("Test Event");
        event.setPrice( new BigDecimal(100.0 ));
        event.setRating(EventRating.HIGH);

        EventSchedule eventSchedule = new EventSchedule(event,auditoriumService.getByName("Gold"),dateTime);

        byte discount = 0;
        discount = discountService.getDiscount(user,eventSchedule,5);
        assertEquals(0,discount);

        user.setBirthday( dateTime.toLocalDate() );
        discount = discountService.getDiscount(user,eventSchedule,5);
        assertEquals(10,discount);

        discount = discountService.getDiscount(user,eventSchedule,15);
        assertEquals(50,discount);
    }
}
