package servlet.performer.admin;

import exception.MethodNotAllowedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import servlet.performer.Performer;
import util.HttpMethod;
import util.UrlPath;
import java.io.PrintWriter;
import java.util.Set;

/**
 *      /admin/{id}/logout
 */

@RequiredArgsConstructor
public class AdminLogoutPerformer implements Performer {
    private static final String path = UrlPath.ADMIN_PATH;
    private static final String logoutSubPath = UrlPath.ADMIN_SUBPATH_LOGOUT;
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
            return requestPathParts.length == 4 && requestPathParts[3].matches(logoutSubPath);      // 0-""/ 1-"admin"/ 2-"{some id}"/ 3-"logout"
        }else return false;
    }
}
