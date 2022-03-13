package servlet.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.dto.patient.PatientDto;
import util.UrlPath;

import java.io.IOException;

@WebFilter({UrlPath.PATIENT_PATH + "/*", UrlPath.PATIENT_REGISTRATION})
public class PatientFilter extends AbstractFilter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String requestUri = httpRequest.getRequestURI();

        if (!isPatientRightLoggedIn(httpRequest)){
            if (requestUri.startsWith(UrlPath.PATIENT_REGISTRATION)
                || requestUri.startsWith(UrlPath.PATIENT_LOGIN)){
                chain.doFilter(httpRequest, httpResponse);
            } else httpResponse.sendRedirect(UrlPath.PATIENT_REGISTRATION);
        } else {
            chain.doFilter(httpRequest, httpResponse);
        }

//        if (requestUri.startsWith(UrlPath.PATIENT_REGISTRATION)
//            && !isPatientRightLoggedIn(httpRequest)){
//            chain.doFilter(httpRequest, httpResponse);
//        }
//        if ()
//
//        if (!isPatientRightLoggedIn(httpRequest)){
//            chain.doFilter(httpRequest, httpResponse);
//        }else {
//            httpResponse.sendRedirect(UrlPath.PATIENT_PATH + "/" + getPatientId(httpRequest) + "/" + UrlPath.DOCTOR_SUBPATH_LOGOUT);
//        }
    }

    private boolean isPatientRightLoggedIn(HttpServletRequest httpRequest){
        PatientDto patientDto = (PatientDto) httpRequest.getSession().getAttribute("PATIENT");
        return patientDto != null && isIdTrue(httpRequest, patientDto.getId());
    }

//    private Long getPatientId(HttpServletRequest httpRequest){
//        String[] requestPathParts = httpRequest.getPathInfo().split("/");
//        return Long.parseLong(requestPathParts[2]);
//    }

    private boolean isIdTrue(HttpServletRequest httpRequest, Long idInSession){
        String[] requestPathParts = httpRequest.getPathInfo().split("/");
        if (requestPathParts.length == 2
            && requestPathParts[1].matches("join")){
            return true;
        }
        if (requestPathParts[2].matches("[1-90]+")){
            return Long.parseLong(requestPathParts[2]) == idInSession;
        }else return requestPathParts[2].matches("login");
    }
}
