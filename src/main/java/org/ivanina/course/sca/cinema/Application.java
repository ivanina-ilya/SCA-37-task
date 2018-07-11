package org.ivanina.course.sca.cinema;

import org.ivanina.course.sca.cinema.config.ApplicationSpringConfig;
import org.ivanina.course.sca.cinema.config.JdbcConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(ApplicationSpringConfig.class);
        ctx.register(JdbcConfig.class);
        ctx.refresh();
    }
}
