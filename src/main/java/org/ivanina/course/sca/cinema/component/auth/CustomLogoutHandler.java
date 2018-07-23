package org.ivanina.course.sca.cinema.component.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

@Component("customLogoutHandler")
public class CustomLogoutHandler implements LogoutHandler {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        logger.warning("-- In LogoutHandler --");
    }
}
