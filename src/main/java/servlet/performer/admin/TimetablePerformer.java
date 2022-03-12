package servlet.performer.admin;

import exception.MethodNotAllowedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import service.admin.NextWeekTimetableService;
import service.admin.TimetableService;
import service.dto.admin.DoctorsAppointmentForAdminDto;
import servlet.converter.response.DocAppForAdminDtoListConverter;
import servlet.performer.Performer;
import servlet.response.JsonResponse;
import util.HttpMethod;
import util.UrlPath;

import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;

/**
 *      /admin/{id}/timetable
 */

@RequiredArgsConstructor
public class TimetablePerformer implements Performer {
    private static final String path = UrlPath.ADMIN_PATH;
    private static final String timetableSubPath = UrlPath.ADMIN_SUBPATH_TIMETABLE;
    private static final Set<String> performableMethods = Set.of(HttpMethod.POST);

    private final TimetableService service;

    private final DocAppForAdminDtoListConverter dtoListConverter;

    @Override
    @SneakyThrows
    public void performAndSendResponse(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) {
        if (request.getMethod().equals(HttpMethod.GET)) {
            performGET(writer, request, response);
        } else throw new MethodNotAllowedException();
    }

    @SneakyThrows
    private void performGET(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) {
        List<DoctorsAppointmentForAdminDto> timetableAsDto = service.getAllTimetable();
        String timetableAsJson = dtoListConverter.convert(timetableAsDto);
        new JsonResponse().send(writer, response, timetableAsJson, SC_OK);
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
                    && requestPathParts[3].matches(timetableSubPath);     // 0-""/ 1-"admin"/ 2-{id}/ 3-"timetable"
        }else return false;
    }
}
