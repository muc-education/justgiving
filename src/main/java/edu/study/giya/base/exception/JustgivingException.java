package edu.study.giya.base.exception;

/**
 * Exception thrown when an active transaction cannot be commited,
 * and it's rolled back.
 *
 * @author miguelff
 */
public class JustgivingException extends RuntimeException {

    private static final long serialVersionUID = -440347087527708668L;

    public JustgivingException() {
    }

    public JustgivingException(String message) {
        super(message);
    }

    public JustgivingException(Throwable cause) {
        super(cause);
    }

    public JustgivingException(String message, Throwable cause) {
        super(message, cause);
    }

}
