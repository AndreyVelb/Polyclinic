package servlet.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.dto.patient.PatientDto;
import util.UrlPath;

import java.io.IOException;
import java.util.Set;
//
//@WebFilter(UrlPath.PATIENT_PATH + "/*")
//public class AuthenticatedPatientFilter extends AbstractFilter{
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
//        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
//
//
//        if (isPatientLoggedIn(httpRequest)){
//            chain.doFilter(httpRequest, httpResponse);
//        }else {
//            httpResponse.sendRedirect(UrlPath.PATIENT_LOGIN);
//        }
//    }
//
//    private boolean isPatientLoggedIn(HttpServletRequest httpRequest){
//        PatientDto patientDto = (PatientDto) httpRequest.getSession().getAttribute("PATIENT");
//        if (patientDto != null){
//            return isIdTrue(httpRequest, patientDto.getId());
//        }else return false;
//    }
//
//    private boolean isIdTrue(HttpServletRequest httpRequest, Long idInSession){
//        String[] requestPathParts = httpRequest.getPathInfo().split("/");
//        return Long.parseLong(requestPathParts[2]) == idInSession;
//    }
//}
