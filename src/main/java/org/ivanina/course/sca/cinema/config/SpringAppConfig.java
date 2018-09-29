package org.ivanina.course.sca.cinema.config;

import org.ivanina.course.sca.cinema.model.pdf.EventsPDFView;
import org.ivanina.course.sca.cinema.model.pdf.UsersPDFView;
import org.ivanina.course.sca.cinema.service.*;
import org.ivanina.course.sca.cinema.service.impl.*;
import org.ivanina.course.sca.cinema.utils.Serializer;
import org.ivanina.course.sca.cinema.utils.XmlSerializer;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@ComponentScan({
        "org.ivanina.course.sca.cinema.controller",
        "org.ivanina.course.sca.cinema.component"
})
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
    public BookingService bookingService() {
        return new BookingServiceImpl();
    }

    @Bean(name = "serializer")
    public Serializer xmlSerializer() {
        return new XmlSerializer();
    }

    @Bean(name = "usersPDFView")
    public UsersPDFView usersPDFView() {
        return new UsersPDFView();
    }

    @Bean(name = "eventsPDFView")
    public EventsPDFView eventsPDFView() {
        return new EventsPDFView();
    }

}
