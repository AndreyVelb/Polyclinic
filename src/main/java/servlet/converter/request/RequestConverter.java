package servlet.converter.request;

import java.io.IOException;

public interface RequestConverter<F, T>{

    T convert(F jsonRequest) throws IOException;

}
