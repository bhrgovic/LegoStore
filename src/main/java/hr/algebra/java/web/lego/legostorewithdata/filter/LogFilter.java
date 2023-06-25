package hr.algebra.java.web.lego.legostorewithdata.filter;

import hr.algebra.java.web.lego.legostorewithdata.domain.Log;
import jakarta.servlet.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LogFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(LogFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        Log log = new Log();
        logger.info("Hello from: " + request.getLocalAddr());
        log.setIpAddress(request.getLocalAddr());
        
        chain.doFilter(request, response);
    }

}