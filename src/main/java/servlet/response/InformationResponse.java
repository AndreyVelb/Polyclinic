package servlet.response;

import jakarta.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class InformationResponse implements ResponseInterface<String>{
    private static final String TEXT_CONTENT_TYPE = "text/plain";

    @Override
    public void send(PrintWriter writer, HttpServletResponse response, String message, int code) {

    }

    private void createResponse(PrintWriter writer, HttpServletResponse response, Exception exception, int code) {
        response.resetBuffer();
        response.setStatus(code);
        response.setContentType(TEXT_CONTENT_TYPE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(code);
        writer.write(exception.getMessage());
        writer.flush();
    }
}
