package exception;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

public class DtoValidationException extends Exception{
    private final String message;

    public DtoValidationException(ConstraintViolationException violationException) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("При вводе информации были допущены следующие ошибки: \n");
        for (ConstraintViolation<?> violation : violationException.getConstraintViolations()) {
            buffer.append(violation + "\n");
        }
        this.message = buffer.toString();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
