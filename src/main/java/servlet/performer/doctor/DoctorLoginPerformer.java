package servlet.performer.doctor;

import entity.DoctorSpeciality;
import exception.MethodNotAllowedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import service.doctor.DoctorLoginService;
import exception.NotAuthenticatedException;
import service.dto.doctor.DoctorDto;
import servlet.performer.Performer;
import util.HttpMethod;
import util.UrlPath;

import java.io.PrintWriter;
import java.util.Optional;
import java.util.Set;

/**
 *      /doctor/login
 */

@RequiredArgsConstructor
public class DoctorLoginPerformer implements Performer {
    private static final String path = UrlPath.DOCTOR_LOGIN;
    private static final Set<String> performableMethods = Set.of(HttpMethod.POST);

    private final DoctorLoginService service;

    @Override
    @SneakyThrows
    public void performAndSendResponse(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) {
        if (request.getMethod().equals(HttpMethod.POST)){
            performPOST(writer, request, response);
        }else throw new MethodNotAllowedException();
    }

    @SneakyThrows
    private void performPOST(PrintWriter writer, HttpServletRequest request, HttpServletResponse response){
        Optional<DoctorDto> mayBeDoctorDto = service.authenticate(request);
        if(mayBeDoctorDto.isPresent() && mayBeDoctorDto.get().getSpeciality() == DoctorSpeciality.CHIEF_DOCTOR) {
            adminLogin(request, response, mayBeDoctorDto.get());
            return;
        }

        if (mayBeDoctorDto.isPresent()){
            doctorLogin(request, response, mayBeDoctorDto.get());
        } else {
            throw new NotAuthenticatedException();
        }
    }

    @SneakyThrows
    private void adminLogin(HttpServletRequest request, HttpServletResponse response, DoctorDto doctorDto) {
        var session = request.getSession();
        DoctorDto sessionDoctorDto = (DoctorDto) session.getAttribute("ADMIN");
        if (sessionDoctorDto == null) {
            session.setAttribute("ADMIN", doctorDto);
        }
        response.sendRedirect(UrlPath.ADMIN_PATH + "/" + doctorDto.getId());
    }

    @SneakyThrows
    private void doctorLogin(HttpServletRequest request, HttpServletResponse response, DoctorDto doctorDto){
        var session = request.getSession();
        DoctorDto sessionDoctorDto = (DoctorDto) session.getAttribute("DOCTOR");
        if (sessionDoctorDto == null){
            session.setAttribute("DOCTOR", doctorDto);
        }
        response.sendRedirect(UrlPath.DOCTOR_PATH + "/" + doctorDto.getId());
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
