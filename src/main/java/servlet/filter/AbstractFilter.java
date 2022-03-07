package servlet.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import service.dto.doctor.DoctorDto;
import service.dto.patient.PatientDto;

import java.io.IOException;
import java.util.Set;

public abstract class AbstractFilter implements Filter {

    @Override
    public abstract void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException;

    protected boolean isRightPath(String uri, Set<String> setOfPaths){
        return setOfPaths.stream().anyMatch(uri::startsWith);
    }
}
