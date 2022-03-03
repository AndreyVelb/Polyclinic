package exception;

public class ExistsException extends Exception{
    private final String message;

    public ExistsException() {
        this.message = "Пользователь с таким именем уже существует";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
