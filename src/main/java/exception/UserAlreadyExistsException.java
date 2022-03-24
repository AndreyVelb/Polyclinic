package exception;

public class UserAlreadyExistsException extends Exception{
    private final String message;

    public UserAlreadyExistsException() {
        this.message = "Пользователь с такими параметрами уже существует. Попробуйте еще раз введя другие данные...";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
