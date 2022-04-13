package servlet.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.dto.doctor.DoctorDto;
import util.SessionRole;
import util.UrlPath;

import java.io.IOException;

@WebFilter(UrlPath.ADMIN_PATH + "/*")
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        if (isAdminLoggedIn(httpRequest)) {
            chain.doFilter(httpRequest, httpResponse);
        } else {
            var previousPage = httpRequest.getHeader("referer");
            httpResponse.sendRedirect(previousPage != null ? previousPage : UrlPath.DOCTOR_LOGIN);
        }
    }

    private boolean isAdminLoggedIn(HttpServletRequest httpRequest) {
        DoctorDto doctorDto = (DoctorDto) httpRequest.getSession().getAttribute(SessionRole.getADMIN());
        return doctorDto != null && isIdTrue(httpRequest, doctorDto.getId());
    }

    private boolean isIdTrue(HttpServletRequest httpRequest, Long idInSession) {
        String[] requestPathParts = httpRequest.getPathInfo().split("/");
        if (requestPathParts[2].matches("[1-90]+")) {
            return Long.parseLong(requestPathParts[2]) == idInSession;
        } else return false;
    }
}