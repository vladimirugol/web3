package com.vladimirugol.filters;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

public class DebugCookieFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper((HttpServletResponse) response) {
            @Override
            public void addCookie(Cookie cookie) {
                System.out.println(">>> SET-COOKIE: " + cookie.getName() + " = [" + cookie.getValue() + "]");
                super.addCookie(cookie);
            }
        };

        chain.doFilter(request, wrapper);
    }
}
