package servlet.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.dto.patient.PatientDto;
import util.UrlPath;

import java.io.IOException;
import java.util.Set;

@WebFilter({UrlPath.ADMIN_PATH, UrlPath.ADMIN_LOGOUT, UrlPath.ADMIN_DOCTORS,
            UrlPath.ADMIN_DOCTOR_REGISTRATION, UrlPath.ADMIN_CREATE_SCHEDULE_ON_WEEK,
            UrlPath.ADMIN_PATH_WITH_INFO})
public class AuthenticatedAdminFilter extends AbstractFilter{
    private static final Set<String> AUTH_ADMIN_PATHS = Set.of(UrlPath.ADMIN_PATH, UrlPath.ADMIN_LOGOUT,
                                                               UrlPath.ADMIN_DOCTORS, UrlPath.ADMIN_DOCTOR_REGISTRATION,
                                                               UrlPath.ADMIN_CREATE_SCHEDULE_ON_WEEK,
                                                               UrlPath.ADMIN_PATH_WITH_INFO);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String requestUri = httpRequest.getRequestURI();

        if (isAdminLoggedIn(httpRequest) && isRightPath(requestUri, AUTH_ADMIN_PATHS)){
            chain.doFilter(httpRequest, httpResponse);
        }else {
            var previousPage = httpRequest.getHeader("referer");
            httpResponse.sendRedirect(previousPage != null ? previousPage : UrlPath.DOCTOR_LOGIN);
        }
    }

    private boolean isAdminLoggedIn(HttpServletRequest httpRequest){
        PatientDto patientDto = (PatientDto) httpRequest.getSession().getAttribute("ADMIN");
        return patientDto != null;
    }
}
