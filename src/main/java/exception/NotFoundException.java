package exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException() {
        super("К сожалению по вашему запросу ничего не найдено");
    }
}
