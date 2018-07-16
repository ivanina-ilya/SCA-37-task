package org.ivanina.course.sca.cinema.config;

import org.ivanina.course.sca.cinema.service.*;
import org.ivanina.course.sca.cinema.service.impl.*;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@ComponentScan({"org.ivanina.course.sca.cinema"})
public class SpringAppConfig {

    @Bean(name = "discounts")
    public PropertiesFactoryBean discounts() {
        PropertiesFactoryBean bean = new PropertiesFactoryBean();
        bean.setLocation(new ClassPathResource("discounts.properties"));
        return bean;
    }


    @Bean(name = "userService")
    public UserService userService() {
        return new UserServiceImpl();
    }

    @Bean(name = "auditoriumService")
    public AuditoriumService auditoriumService() {
        return new AuditoriumServiceImpl();
    }

    @Bean(name = "eventService")
    public EventService eventService() {
        return new EventServiceImpl();
    }

    @Bean(name = "discountService")
    public DiscountService discountService() {
        return new DiscountServiceImpl();
    }

    @Bean(name = "bookingService")
    public BookingService BookingService() {
        return new BookingServiceImpl();
    }

}
