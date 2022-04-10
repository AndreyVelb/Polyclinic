package servlet.response;

import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class ExceptionResponse {
    private static final String TEXT_CONTENT_TYPE = "text/plain";

    public void send(PrintWriter writer, HttpServletResponse response, Exception exception, int code) {
        createResponse(writer, response, exception, code);
    }

    private void createResponse(PrintWriter writer, HttpServletResponse response, Exception exception, int code) {
        response.resetBuffer();
        response.setStatus(code);
        response.setContentType(TEXT_CONTENT_TYPE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        writer.print(exception.getMessage());
        writer.flush();
    }
}
