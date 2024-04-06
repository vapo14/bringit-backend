package cs.vapo.bringit.core.exceptions;

public class BadRequestException extends RuntimeException {

    public BadRequestException(final Throwable cause) {
        super(cause);
    }

    public BadRequestException(final String message) {
        super(message);
    }

    public BadRequestException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
