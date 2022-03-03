package servlet.response;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class JsonResponse implements ResponseInterface<String>{
    public static final String JSON_CONTENT_TYPE = "application/json";

    @Override
    public void send(PrintWriter writer, HttpServletResponse response, String json, int code) throws IOException {
        createResponse(writer, response, json, code);
    }

    private void createResponse (PrintWriter writer, HttpServletResponse response, String json, int code) throws IOException {
        response.resetBuffer();
        response.setStatus(code);
        response.setContentType(JSON_CONTENT_TYPE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        writer.print(json);
        writer.flush();
    }
}
