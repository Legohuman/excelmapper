package ru.dlevin.excelmapper.exceptions;

/**
 * User: Dmitry Levin
 * Date: 24.03.14
 */
public class ConversionException extends RuntimeException {
    public ConversionException() {
        super();
    }

    public ConversionException(String message) {
        super(message);
    }

    public ConversionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConversionException(Throwable cause) {
        super(cause);
    }
}
