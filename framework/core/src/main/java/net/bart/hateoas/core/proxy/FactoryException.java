package net.bart.hateoas.core.proxy;

public class FactoryException extends RuntimeException {

    public FactoryException(final String message) {
        super(message);
    }

    public FactoryException(final Throwable cause) {
        super(cause);
    }

}
