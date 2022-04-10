package exception;

public class AlreadyBookedException extends RuntimeException{
    public AlreadyBookedException() {
        super("Извините, данная запись уже забронирована. Попробуйте выбрать другую...");
    }
}
