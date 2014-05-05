package ru.dlevin.excelmapper.engine;

import ru.dlevin.excelmapper.utils.Validate;

/**
 * User: Dmitry Levin
 * Date: 14.03.14
 */
public class Cursor implements Postionable {
    private CellCoordinate startCoordinate;
    private CellCoordinate currentCoordinate;
    private MovementDirection direction;

    public Cursor(CellCoordinate currentCoordinate, MovementDirection direction) {
        Validate.notNull(currentCoordinate, "Cursor coordinate can not be null");
        Validate.notNull(direction, "Cursor movement direction can not be null");
        this.currentCoordinate = currentCoordinate;
        this.startCoordinate = currentCoordinate;
        this.direction = direction;
    }

    public Cursor(CellCoordinate currentCoordinate) {
        this(currentCoordinate, MovementDirection.DOWN);
    }

    public MovementDirection getMovementDirection() {
        return direction;
    }

    public Cursor setMovementDirection(MovementDirection direction) {
        this.direction = direction;
        return this;
    }

    public CellCoordinate getCurrentCoordinate() {
        return currentCoordinate;
    }

    public Cursor setCurrentCoordinate(CellCoordinate currentCoordinate) {
        this.currentCoordinate = currentCoordinate;
        return this;
    }

    public Cursor moveBy(Rectangle rectangle) {
        if (MovementDirection.DOWN.equals(direction)) {
            currentCoordinate = currentCoordinate.plusRow(rectangle.getRowCount());
        } else if (MovementDirection.RIGHT.equals(direction)) {
            currentCoordinate = currentCoordinate.plusColumn(rectangle.getColumnCount());
        } else if (MovementDirection.UP.equals(direction)) {
            currentCoordinate = currentCoordinate.minusRow(rectangle.getRowCount());
        } else if (MovementDirection.LEFT.equals(direction)) {
            currentCoordinate = currentCoordinate.minusColumn(rectangle.getColumnCount());
        }
        return this;
    }

    public Cursor plusColumn(int column) {
        currentCoordinate = currentCoordinate.plusColumn(column);
        return this;
    }

    public Cursor plusRow(int row) {
        currentCoordinate = currentCoordinate.plusRow(row);
        return this;
    }

    public Cursor minusColumn(int column) {
        currentCoordinate = currentCoordinate.minusColumn(column);
        return this;
    }

    public Cursor minusRow(int row) {
        currentCoordinate = currentCoordinate.minusRow(row);
        return this;
    }

    public Cursor nextColumn() {
        currentCoordinate = currentCoordinate.nextColumn();
        return this;
    }

    public Cursor nextRow() {
        currentCoordinate = currentCoordinate.nextRow();
        return this;
    }

    public Cursor prevColumn() {
        currentCoordinate = currentCoordinate.prevColumn();
        return this;
    }

    public Cursor prevRow() {
        currentCoordinate = currentCoordinate.prevRow();
        return this;
    }

    public Cursor plusCoordinate(CellCoordinate coordinate) {
        currentCoordinate = currentCoordinate.plusCoordinate(coordinate);
        return this;
    }

    public Cursor minusCoordinate(CellCoordinate coordinate) {
        currentCoordinate = currentCoordinate.minusCoordinate(coordinate);
        return this;
    }

    public Cursor plusCoordinate(int column, int row) {
        currentCoordinate = currentCoordinate.plusCoordinate(column, row);
        return this;
    }

    public Cursor minusCoordinate(int column, int row) {
        currentCoordinate = currentCoordinate.minusCoordinate(column, row);
        return this;
    }

    public Cursor resetColumn() {
        currentCoordinate = new CellCoordinate(startCoordinate.getColumn(), currentCoordinate.getRow());
        return this;
    }

    public Cursor resetRow() {
        currentCoordinate = new CellCoordinate(currentCoordinate.getColumn(), startCoordinate.getRow());
        return this;
    }

    public Cursor resetCoordinate() {
        currentCoordinate = new CellCoordinate(startCoordinate);
        return this;
    }
}
