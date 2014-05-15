package ru.dlevin.excelmapper.engine;

/**
 * User: Dmitry Levin
 * Date: 05.05.14
 */
public class ProcessMessage {
    private ProcessMessageType messageType;

    private CellCoordinate coordinate;

    private Object context;

    private ValueReference valueReference;

    private Exception exception;

    private String description;

    public ProcessMessage() {
    }

    public ProcessMessage(ProcessMessageType messageType, CellCoordinate coordinate, Object context, ValueReference valueReference,
        Exception exception,
        String description) {
        this.messageType = messageType;
        this.coordinate = coordinate;
        this.context = context;
        this.valueReference = valueReference;
        this.exception = exception;
        this.description = description;
    }

    public ProcessMessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(ProcessMessageType messageType) {
        this.messageType = messageType;
    }

    public CellCoordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(CellCoordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Object getContext() {
        return context;
    }

    public void setContext(Object context) {
        this.context = context;
    }

    public ValueReference getValueReference() {
        return valueReference;
    }

    public void setValueReference(ValueReference valueReference) {
        this.valueReference = valueReference;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return messageType +
            " at " + coordinate +
            ", context: " + context +
            ", valueReference: " + valueReference +
            ", exception: " + exception +
            ", description: " + description;
    }
}
