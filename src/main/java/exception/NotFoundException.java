package exception;

public class NotFoundException extends Exception{
    private final String message;

    public NotFoundException() {
        this.message = "К сожалению по вашему запросу ничего не найдено";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
