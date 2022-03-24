package servlet.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.dto.doctor.DoctorDto;
import util.UrlPath;

import java.io.IOException;

@WebFilter(UrlPath.DOCTOR_PATH + "/*")
public class DoctorFilter implements Filter{

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String requestUri = httpRequest.getRequestURI();

        if (!isDoctorLoggedIn(httpRequest)
            && !isAdminLoggedIn(httpRequest)){
            if (requestUri.startsWith(UrlPath.DOCTOR_LOGIN)){
                chain.doFilter(httpRequest, httpResponse);
            } else httpResponse.sendRedirect(UrlPath.DOCTOR_LOGIN);
        } else {
            chain.doFilter(httpRequest, httpResponse);
        }
    }

    private boolean isDoctorLoggedIn(HttpServletRequest httpRequest){
        DoctorDto doctorDto = (DoctorDto) httpRequest.getSession().getAttribute("DOCTOR");
        return doctorDto != null && isIdTrue(httpRequest, doctorDto.getId());
    }

    private boolean isAdminLoggedIn(HttpServletRequest httpRequest){
        DoctorDto doctorDto = (DoctorDto) httpRequest.getSession().getAttribute("ADMIN");
        return doctorDto != null && isIdTrue(httpRequest, doctorDto.getId());
    }

    private boolean isIdTrue(HttpServletRequest httpRequest, Long idInSession){
        String[] requestPathParts = httpRequest.getPathInfo().split("/");
        if (requestPathParts[2].matches("[1-90]+")){
            return Long.parseLong(requestPathParts[2]) == idInSession;
        }else return requestPathParts[2].matches("login");
    }
}
