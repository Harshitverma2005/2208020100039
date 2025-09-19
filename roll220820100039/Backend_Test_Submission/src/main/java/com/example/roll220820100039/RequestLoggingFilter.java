package com.example.roll220820100039;

import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

@Component
public class RequestLoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        System.out.println("[LOG] " + request.getMethod() + " " + request.getRequestURI());
        chain.doFilter(req, res);
    }
}
