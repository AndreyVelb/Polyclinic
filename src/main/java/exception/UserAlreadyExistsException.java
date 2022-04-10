package exception;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException() {
        super("Пользователь с такими параметрами уже существует. Попробуйте еще раз введя другие данные...");
    }
}
