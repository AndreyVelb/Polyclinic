package exception;

public class ServerTechnicalProblemsException extends RuntimeException{
    public ServerTechnicalProblemsException() {
        super("Просим прощения. На сервере технические неполадки. Попробуйте еще раз...");
    }
}
