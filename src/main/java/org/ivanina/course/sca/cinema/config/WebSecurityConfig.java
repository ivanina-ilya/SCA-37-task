package org.ivanina.course.sca.cinema.config;

import org.ivanina.course.sca.cinema.component.auth.AuthProvider;
import org.ivanina.course.sca.cinema.domain.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthProvider authProvider;


    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;


    @Override
    protected void configure(
            AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider( authProvider );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/event**").permitAll()
                    .antMatchers("/admin/**","/pdf/**").hasAuthority(UserRole.ADMIN +"")
                    .antMatchers("/user/**", "/booking/**").hasAnyAuthority(UserRole.REGISTERED +"", UserRole.ADMIN +"")
                    .and()
                .formLogin()
                    .permitAll()
                    .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // DO NOT RECOMMENDED !
                    .logoutSuccessUrl("/")
                    .permitAll()
                    .and()
                .rememberMe().rememberMeParameter("remember-me").tokenRepository(persistentTokenRepository())

                    /*.and()
                .csrf().disable()*/

                /*.and()
                .httpBasic()*/;
    }

    @Bean(name = "persistentTokenRepository")
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }



}
