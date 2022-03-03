package exception;

public class NotAuthenticatedException extends Exception{
    private final String message;

    public NotAuthenticatedException() {
        this.message = "Вы не прошли аутентификацию. Попробуйте еще раз...";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
