package servlet.performer.patient;

import exception.AlreadyBookedException;
import exception.DtoValidationException;
import exception.MethodNotAllowedException;
import exception.NotAuthenticatedException;
import exception.UserAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
 *      /patient/{id}/logout
 */

@RequiredArgsConstructor
public class PatientLogoutPerformer implements Performer {
    private static final String path = UrlPath.PATIENT_PATH;
    private static final String logoutSubPath = UrlPath.PATIENT_SUBPATH_LOGOUT;
    private static final Set<String> performableMethods = Set.of(HttpMethod.POST);

    @Override
    @SneakyThrows
    public void performAndSendResponse(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) {
        if (request.getMethod().equals(HttpMethod.POST)){
            performPOST(request, response);
        }else throw new MethodNotAllowedException();
    }

    @SneakyThrows
    private void performPOST(HttpServletRequest request, HttpServletResponse response){
        try {
            request.getSession().invalidate();
            response.sendRedirect(UrlPath.PATIENT_LOGIN);
        } catch (UserAlreadyExistsException
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
            return requestPathParts.length == 4
                    && requestPathParts[2].matches("[1-90]+")
                    && requestPathParts[3].matches(logoutSubPath);      // 0-""/ 1-"patient"/ 2-{id}/ 3-"logout"
        }else return false;
    }
}
