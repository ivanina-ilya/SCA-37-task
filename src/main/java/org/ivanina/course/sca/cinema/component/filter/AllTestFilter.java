package org.ivanina.course.sca.cinema.component.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.logging.Logger;

@Component("allTestFilter")
public class AllTestFilter implements Filter {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.warning("-- In AllTestFilter --");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
