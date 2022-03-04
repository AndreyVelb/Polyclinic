package servlet.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.mapper.util.UrlPath;

import java.io.IOException;
import java.util.Set;

@WebFilter({UrlPath.DOCTOR_LOGIN})
public class NoAuthDoctorFilter extends AbstractFilter{
    public static final Set<String> NO_AUTH_DOCTORS_PATHS = Set.of(UrlPath.DOCTOR_LOGIN);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String requestUri = httpRequest.getRequestURI();

        if (!isDoctorLoggedIn(httpRequest) && isRightPath(requestUri, NO_AUTH_DOCTORS_PATHS)) {
            chain.doFilter(httpRequest, httpResponse);
        } else {
            httpResponse.sendRedirect(UrlPath.DOCTOR_LOGOUT);
        }
    }

}
