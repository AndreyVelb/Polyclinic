package servlet.converter.response;

import java.io.IOException;

public interface ResponseConverter <F, T>{

    T convert(F javaObject) throws IOException;

}
