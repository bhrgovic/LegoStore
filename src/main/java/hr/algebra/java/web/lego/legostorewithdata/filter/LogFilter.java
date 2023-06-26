package hr.algebra.java.web.lego.legostorewithdata.filter;

import hr.algebra.java.web.lego.legostorewithdata.domain.Log;
import hr.algebra.java.web.lego.legostorewithdata.repository.LogRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Time;
import java.time.LocalDateTime;

@Component
public class LogFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(LogFilter.class);
    private LogRepository logRepository;

    public LogFilter(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        if (requestURI.contains("/legoStore/login.html")) {
            // Execute the filter only on login
            String message="Login attempt at "+ request.getLocalAddr() + " at " + LocalDateTime.now();

            logRepository.createLog(message);
        }

        chain.doFilter(request, response);
    }

}