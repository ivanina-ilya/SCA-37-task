package org.ivanina.course.sca.cinema.config;

import org.ivanina.course.sca.cinema.component.auth.AuthProvider;
import org.ivanina.course.sca.cinema.domain.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthProvider authProvider;
    

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
                    .antMatchers("/admin/**","/pdf/**").hasRole(UserRole.ADMIN+"")
                    .antMatchers("/user/**", "/booking/**").hasAnyRole(UserRole.REGISTERED+"", UserRole.ADMIN+"")
                    .and()
                .formLogin()
                    .permitAll()
                    .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // DO NOT RECOMMENDED !
                    .logoutSuccessUrl("/")
                    .permitAll()
                /*.and()
                .httpBasic()*/;
    }


}
