package servlet.performer.admin;

import exception.AlreadyBookedException;
import exception.DtoValidationException;
import exception.MethodNotAllowedException;
import exception.NotAuthenticatedException;
import exception.UserAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import service.admin.NextWeekTimetableService;
import servlet.performer.Performer;
import servlet.response.ExceptionResponse;
import util.HttpMethod;
import util.UrlPath;

import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.util.Set;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static jakarta.servlet.http.HttpServletResponse.SC_CONFLICT;
import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

/**
 *      /admin/{id}/next-week-timetable
 */

@RequiredArgsConstructor
public class NextWeekTimetablePerformer implements Performer {
    private static final String path = UrlPath.ADMIN_PATH;
    private static final String timetableOnNextWeekSubPath = UrlPath.ADMIN_SUBPATH_CREATE_TIMETABLE;
    private static final Set<String> performableMethods = Set.of(HttpMethod.POST);

    private final NextWeekTimetableService service;

    @Override
    @SneakyThrows
    public void performAndSendResponse(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) {
        if (request.getMethod().equals(HttpMethod.POST)) {
            performPOST(request, response);
        } else throw new MethodNotAllowedException();
    }

    @SneakyThrows
    private void performPOST(HttpServletRequest request, HttpServletResponse response) {
        try {
            service.createDoctorsAppointmentsOnNextWeek();
            response.sendRedirect(UrlPath.ADMIN_PATH + "/" + getAdminId(request) + "/" + UrlPath.ADMIN_SUBPATH_TIMETABLE);
        }
        catch (UserAlreadyExistsException
                | ConstraintViolationException
                | DtoValidationException exception) {
            new ExceptionResponse().send(response.getWriter(), response, exception, SC_BAD_REQUEST);
        } catch (NotAuthenticatedException exception) {
            new ExceptionResponse().send(response.getWriter(), response, exception, SC_UNAUTHORIZED);
        } catch (AlreadyBookedException exception) {
            new ExceptionResponse().send(response.getWriter(), response, exception, SC_CONFLICT);
        }
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
                    && requestPathParts[3].matches(timetableOnNextWeekSubPath);     // 0-""/ 1-"admin"/ 2-{id}/ 3-"next-week-timetable"
        }else return false;
    }

    private Long getAdminId(HttpServletRequest request){
        String[] requestPathParts = request.getPathInfo().split("/");
        return Long.parseLong(requestPathParts[2]);
    }
}
