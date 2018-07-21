package org.ivanina.course.sca.cinema.config;

import org.ivanina.course.sca.cinema.dao.*;
import org.ivanina.course.sca.cinema.dao.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@PropertySource("classpath:jdbc.properties")
public class JdbcConfig {
    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void init() throws SQLException {
        Resource rc = new ClassPathResource("db/initDB.sql");
        ScriptUtils.executeSqlScript(dataSource.getConnection(), rc);

        rc = new ClassPathResource("db/initAuditoriums.sql");
        ScriptUtils.executeSqlScript(dataSource.getConnection(), rc);

        rc = new ClassPathResource("db/initUsers.sql");
        ScriptUtils.executeSqlScript(dataSource.getConnection(), rc);

        rc = new ClassPathResource("db/initEvents.sql");
        ScriptUtils.executeSqlScript(dataSource.getConnection(), rc);
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        return jdbcTemplate;
    }



    @Bean(name = "userDao")
    public UserDao userDao() {
        return new UserDaoImpl();
    }

    @Bean(name = "eventDao")
    public EventDao eventDao() {
        return new EventDaoImpl();
    }

    @Bean(name = "eventScheduleDao")
    public EventScheduleDao eventScheduleDao() {
        return new EventScheduleDaoImpl();
    }

    @Bean(name = "auditoriumDao")
    public AuditoriumDao auditoriumDao() {
        return new AuditoriumDaoImpl();
    }

    @Bean(name = "ticketDao")
    public TicketDao ticketDao() {
        return new TicketDaoImpl();
    }
}
