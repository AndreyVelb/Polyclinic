package exception;

public class ServerTechnicalProblemsException extends RuntimeException{
    public ServerTechnicalProblemsException(String message) {
        super(message);
    }
}
