package com.github.excelmapper.core.exceptions;

/**
 * User: Dmitry Levin
 * Date: 08.03.14
 */
public class ItemCreationException extends RuntimeException {
    public ItemCreationException() {
        super();
    }

    public ItemCreationException(String message) {
        super(message);
    }

    public ItemCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ItemCreationException(Throwable cause) {
        super(cause);
    }
}
