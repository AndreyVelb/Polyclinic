package exception;

public class AlreadyExistsException extends Exception{
    private final String message;

    public AlreadyExistsException() {
        this.message = "Пользователь с такими параметрами уже существует. Попробуйте еще раз введя другие данные...";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
