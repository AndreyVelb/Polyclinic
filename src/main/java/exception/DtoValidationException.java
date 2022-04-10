package exception;

public class DtoValidationException extends RuntimeException{
    public DtoValidationException(String validationResult) {
        super("При вводе информации были допущены следующие ошибки: \n" + validationResult);
    }
}
