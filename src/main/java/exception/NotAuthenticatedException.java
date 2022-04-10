package exception;

public class NotAuthenticatedException extends RuntimeException{
    public NotAuthenticatedException() {
        super("Вы не прошли аутентификацию. Попробуйте еще раз...");
    }
}
