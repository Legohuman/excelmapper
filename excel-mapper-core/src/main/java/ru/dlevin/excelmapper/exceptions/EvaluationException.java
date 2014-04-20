package ru.dlevin.excelmapper.exceptions;

/**
 * User: Dmitry Levin
 * Date: 07.03.14
 */
public class EvaluationException extends RuntimeException {

    public EvaluationException() {
    }

    public EvaluationException(String message) {
        super(message);
    }

    public EvaluationException(String message, Throwable cause) {
        super(message, cause);
    }

    public EvaluationException(Throwable cause) {
        super(cause);
    }
}
