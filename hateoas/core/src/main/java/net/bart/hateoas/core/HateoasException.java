package net.bart.hateoas.core;

public class HateoasException extends RuntimeException {

    public HateoasException(final String message) {
        super(message);
    }

    public HateoasException(final Throwable cause) {
        super(cause);
    }

    public HateoasException(final String msg, final Throwable cause) {
        super(msg, cause);
    }

}
