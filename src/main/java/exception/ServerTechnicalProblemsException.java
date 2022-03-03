package exception;

public class ServerTechnicalProblemsException extends Exception{
    private final String message;

    public ServerTechnicalProblemsException() {
        this.message = "Просим прощения. На сервере технические неполадки. Попробуйте еще раз...";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
