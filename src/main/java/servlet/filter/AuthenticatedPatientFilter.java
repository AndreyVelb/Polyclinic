package servlet.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import util.UrlPath;

import java.io.IOException;
import java.util.Set;

@WebFilter({UrlPath.PATIENT_LOGOUT})
public class AuthenticatedPatientFilter extends AbstractFilter{
    public static final Set<String> AUTH_PATIENTS_PATHS = Set.of(UrlPath.PATIENT_LOGOUT);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String requestUri = httpRequest.getRequestURI();

        if (isPatientLoggedIn(httpRequest) && isRightPath(requestUri, AUTH_PATIENTS_PATHS)){
            chain.doFilter(httpRequest, httpResponse);
        }else {
            httpResponse.sendRedirect(UrlPath.DOCTOR_LOGIN);
        }
    }
}
