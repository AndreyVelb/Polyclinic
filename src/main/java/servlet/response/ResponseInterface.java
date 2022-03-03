package servlet.response;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public interface ResponseInterface<T> {

    void send(PrintWriter writer, HttpServletResponse response, T responseType, int code) throws IOException;

}
