package exception;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

public class AlreadyBookedException extends Exception{
    private final String message;

    public AlreadyBookedException() {
        this.message = "Извините, данная запись уже забронирована. Попробуйте выбрать другую...";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
