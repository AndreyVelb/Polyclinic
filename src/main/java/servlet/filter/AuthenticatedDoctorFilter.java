package servlet.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.UrlPath;

import java.io.IOException;
import java.util.Set;

@WebFilter({UrlPath.DOCTOR_LOGOUT, UrlPath.DOCTOR_SEARCH_PATIENT, UrlPath.DOCTOR_CHOOSE_PATIENT})
public class AuthenticatedDoctorFilter extends AbstractFilter {
    public static final Set<String> AUTH_DOCTORS_PATHS = Set.of(UrlPath.DOCTOR_LOGOUT, UrlPath.DOCTOR_SEARCH_PATIENT, UrlPath.DOCTOR_CHOOSE_PATIENT);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String requestUri = httpRequest.getRequestURI();

        if (isDoctorLoggedIn(httpRequest) && isRightPath(requestUri, AUTH_DOCTORS_PATHS)){
            chain.doFilter(httpRequest, httpResponse);
        }else {
            var previousPage = httpRequest.getHeader("referer");
            httpResponse.sendRedirect(previousPage != null ? previousPage : UrlPath.DOCTOR_LOGIN);
        }
    }
}
