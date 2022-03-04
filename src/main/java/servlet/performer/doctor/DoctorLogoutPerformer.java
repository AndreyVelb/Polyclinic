package servlet.performer.doctor;

import exception.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import servlet.performer.Performer;
import service.mapper.util.HttpMethod;
import service.mapper.util.UrlPath;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class DoctorLogoutPerformer implements Performer{
    private static final String path = UrlPath.DOCTOR_CHOOSE_PATIENT;
    private static final ArrayList<String> performableMethods = new ArrayList<>();

    public DoctorLogoutPerformer() {
        performableMethods.add(HttpMethod.POST);
    }

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
        return path.equals(requestPath);
    }
}
