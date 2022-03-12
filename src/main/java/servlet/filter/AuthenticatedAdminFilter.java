package servlet.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.dto.doctor.DoctorDto;
import service.dto.patient.PatientDto;
import util.UrlPath;

import java.io.IOException;
import java.util.Set;

@WebFilter({UrlPath.ADMIN_PATH + "/*"})
public class AuthenticatedAdminFilter extends AbstractFilter{

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        if (isAdminLoggedIn(httpRequest)){
            chain.doFilter(httpRequest, httpResponse);
        }else {
            var previousPage = httpRequest.getHeader("referer");
            httpResponse.sendRedirect(previousPage != null ? previousPage : UrlPath.DOCTOR_LOGIN);
        }
    }

    private boolean isAdminLoggedIn(HttpServletRequest httpRequest){
        DoctorDto doctorDto = (DoctorDto) httpRequest.getSession().getAttribute("ADMIN");
        if (doctorDto != null){
            return isIdTrue(httpRequest, doctorDto.getId());
        }else return false;
    }

    private boolean isIdTrue(HttpServletRequest httpRequest, Long idInSession){
        String[] requestPathParts = httpRequest.getPathInfo().split("/");
        return Long.parseLong(requestPathParts[2]) == idInSession;
    }
}