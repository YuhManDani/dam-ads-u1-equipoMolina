package app.exceptions;

public class SocioNoExisteException extends RuntimeException {
    public SocioNoExisteException(String message) {
        super(message);
    }
}
