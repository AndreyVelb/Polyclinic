package servlet.performer;

import exception.AlreadyExistsException;
import exception.NotAuthenticatedException;
import exception.PageNotFoundException;
import exception.ServerTechnicalProblemsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public interface Performer {

    void performAndSendResponse(PrintWriter writer, HttpServletRequest request, HttpServletResponse response) throws IOException, AlreadyExistsException,
            ServerTechnicalProblemsException, NotAuthenticatedException, PageNotFoundException;

    boolean isMethodCanBePerformed(HttpServletRequest request);

    boolean isAppropriatePath(HttpServletRequest request);

}
