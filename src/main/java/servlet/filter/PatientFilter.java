package servlet.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.dto.patient.PatientDto;
import util.SessionRole;
import util.UrlPath;

import java.io.IOException;

@WebFilter({UrlPath.PATIENT_PATH + "/*", UrlPath.PATIENT_REGISTRATION})
public class PatientFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String requestUri = httpRequest.getRequestURI();

        if (!isPatientLoggedIn(httpRequest)) {
            if (requestUri.startsWith(UrlPath.PATIENT_REGISTRATION)
                    || requestUri.startsWith(UrlPath.PATIENT_LOGIN)) {
                chain.doFilter(httpRequest, httpResponse);
            } else httpResponse.sendRedirect(UrlPath.PATIENT_REGISTRATION);
        } else {
            chain.doFilter(httpRequest, httpResponse);
        }
    }

    private boolean isPatientLoggedIn(HttpServletRequest httpRequest) {
        PatientDto patientDto = (PatientDto) httpRequest.getSession().getAttribute(SessionRole.getPATIENT());
        return patientDto != null && isIdTrue(httpRequest, patientDto.getId());
    }

    private boolean isIdTrue(HttpServletRequest httpRequest, Long idInSession) {
        String[] requestPathParts = httpRequest.getPathInfo().split("/");
        if (requestPathParts.length == 2
                && requestPathParts[1].matches("join")) {
            return true;
        }
        if (requestPathParts[2].matches("[1-90]+")) {
            return Long.parseLong(requestPathParts[2]) == idInSession;
        } else return requestPathParts[2].matches("login");
    }
}
