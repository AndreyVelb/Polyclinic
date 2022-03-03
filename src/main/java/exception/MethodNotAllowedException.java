package exception;

public class MethodNotAllowedException extends Exception{
    private final String message;

    public MethodNotAllowedException() {
        this.message = "";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
