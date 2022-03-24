package servlet.performer.doctor;

import exception.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jdk.jfr.Registered;
import lombok.SneakyThrows;
import servlet.performer.Performer;
import util.HttpMethod;
import util.UrlPath;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Set;

/**
 *      /doctor/{id}/logout
 */

@Registered
public class DoctorLogoutPerformer implements Performer{
    private static final String path = UrlPath.DOCTOR_PATH;
    private static final String logoutSubPath = UrlPath.DOCTOR_SUBPATH_LOGOUT;
    private static final Set<String> performableMethods = Set.of(HttpMethod.POST);

    @Override
    @SneakyThrows
    public void performAndSendResponse(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) {
        if (request.getMethod().equals(HttpMethod.POST)){
            performPOST(writer, request, response);
        }else throw new MethodNotAllowedException();
    }

    @SneakyThrows
    private void performPOST(PrintWriter writer, HttpServletRequest request, HttpServletResponse response){
        request.getSession().invalidate();
        response.sendRedirect(UrlPath.DOCTOR_LOGIN);
    }

    @Override
    public boolean isMethodCanBePerformed(HttpServletRequest request) {
        for (String method : performableMethods) {
            if (method.equals(request.getMethod())){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isAppropriatePath(HttpServletRequest request) {
        String requestPath = request.getRequestURI();
        if(requestPath.startsWith(path)){
            String[] requestPathParts = request.getPathInfo().split("/");
            if(requestPathParts.length == 4
                    && requestPathParts[2].matches("[1-90]+")
                    && requestPathParts[3].matches(logoutSubPath)){     // 0-""/ 1-"doctor"/ 2-{id}/ 3-"logout"
                return true;
            }else return false;
        }else return false;
    }
}
