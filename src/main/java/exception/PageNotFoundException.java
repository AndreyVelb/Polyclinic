package exception;

public class PageNotFoundException extends Exception{
    private final String message;

    public PageNotFoundException() {
        this.message = "";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
