package servlet.performer;

import exception.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public interface Performer {

    void performAndSendResponse(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) throws IOException, UserAlreadyExistsException, DtoValidationException,
            ServerTechnicalProblemsException, NotAuthenticatedException, PageNotFoundException, AlreadyBookedException;

    boolean isMethodCanBePerformed(HttpServletRequest request);

    boolean isAppropriatePath(HttpServletRequest request);

}
