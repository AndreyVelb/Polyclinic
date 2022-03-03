package service.mapper;

import java.io.IOException;

public interface Mapper <F, T>{

    T mapFrom(F object);

}
