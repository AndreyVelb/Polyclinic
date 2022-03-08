package servlet.performer.admin;

import entity.Doctor;
import exception.MethodNotAllowedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import service.admin.DoctorRegistrationService;
import service.dto.doctor.DoctorDto;
import service.dto.patient.PatientDto;
import service.patient.PatientRegistrationService;
import servlet.performer.Performer;
import servlet.response.DoctorRegistrationResponse;
import servlet.response.PatientRegistrationResponse;
import util.HttpMethod;
import util.UrlPath;

import java.io.PrintWriter;
import java.util.Set;

import static jakarta.servlet.http.HttpServletResponse.SC_CREATED;

/**
 *      /admin/{id}/doc-registration
 */

@RequiredArgsConstructor
public class DoctorRegistrationPerformer implements Performer {
    private static final String path = UrlPath.ADMIN_PATH;
    private static final String registrationSubPath = UrlPath.ADMIN_SUBPATH_DOC_REGISTRATION;
    private static final Set<String> performableMethods = Set.of(HttpMethod.POST);

    private final DoctorRegistrationService service;

    @Override
    @SneakyThrows
    public void performAndSendResponse(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) {
        if (request.getMethod().equals(HttpMethod.POST)) {
            performPOST(writer, request, response);
        } else throw new MethodNotAllowedException();
    }

    @SneakyThrows
    private void performPOST(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) {
        DoctorDto doctorDto = service.registration(request);
        new DoctorRegistrationResponse().send(writer, response, doctorDto, SC_CREATED);
    }

    @Override
    public boolean isMethodCanBePerformed(HttpServletRequest request) {
        for (String method : performableMethods) {
            if (method.equals(request.getMethod())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isAppropriatePath(HttpServletRequest request) {
        String requestPath = request.getRequestURI();
        if (requestPath.startsWith(path)) {
            String[] requestPathParts = request.getPathInfo().split("/");
            return requestPathParts.length == 4
                    && requestPathParts[2].matches("[1-90]+")
                    && requestPathParts[3].matches(registrationSubPath);     // 0-""/ 1-"admin"/ 2-{id}/ 3-"doc-registration"
        }else return false;
    }
}