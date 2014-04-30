package ru.dlevin.excelmapper.engine;

import ru.dlevin.excelmapper.utils.Validate;

/**
 * User: Dmitry Levin
 * Date: 14.03.14
 */
public class Cursor {
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

    public MovementDirection getDirection() {
        return direction;
    }

    public void setDirection(MovementDirection direction) {
        this.direction = direction;
    }

    public CellCoordinate getCurrentCoordinate() {
        return currentCoordinate;
    }

    public void setCurrentCoordinate(CellCoordinate currentCoordinate) {
        this.currentCoordinate = currentCoordinate;
    }

    public void moveBy(Rectangle rectangle) {
        if (MovementDirection.DOWN.equals(direction)) {
            currentCoordinate = currentCoordinate.plusRow(rectangle.getRowCount());
        } else if (MovementDirection.RIGHT.equals(direction)) {
            currentCoordinate = currentCoordinate.plusColumn(rectangle.getColumnCount());
        } else if (MovementDirection.UP.equals(direction)) {
            currentCoordinate = currentCoordinate.minusRow(rectangle.getRowCount());
        } else if (MovementDirection.LEFT.equals(direction)) {
            currentCoordinate = currentCoordinate.minusColumn(rectangle.getColumnCount());
        }
    }

    public void plusColumn(int column) {
        currentCoordinate = currentCoordinate.plusColumn(column);
    }

    public void plusRow(int row) {
        currentCoordinate = currentCoordinate.plusRow(row);
    }

    public void minusColumn(int column) {
        currentCoordinate = currentCoordinate.minusColumn(column);
    }

    public void minusRow(int row) {
        currentCoordinate = currentCoordinate.minusRow(row);
    }

    public void nextColumn() {
        currentCoordinate = currentCoordinate.nextColumn();
    }

    public void nextRow() {
        currentCoordinate = currentCoordinate.nextRow();
    }

    public void prevColumn() {
        currentCoordinate = currentCoordinate.prevColumn();
    }

    public void prevRow() {
        currentCoordinate = currentCoordinate.prevRow();
    }

    public void plusCoordinate(CellCoordinate coordinate) {
        currentCoordinate = currentCoordinate.plusCoordinate(coordinate);
    }

    public void minusCoordinate(CellCoordinate coordinate) {
        currentCoordinate = currentCoordinate.minusCoordinate(coordinate);
    }

    public void plusCoordinate(int column, int row) {
        currentCoordinate = currentCoordinate.plusCoordinate(column, row);
    }

    public void minusCoordinate(int column, int row) {
        currentCoordinate = currentCoordinate.minusCoordinate(column, row);
    }

    public void resetColumn() {
        currentCoordinate = new CellCoordinate(startCoordinate.getColumn(), currentCoordinate.getRow());
    }

    public void resetRow() {
        currentCoordinate = new CellCoordinate(currentCoordinate.getColumn(), startCoordinate.getRow());
    }

    public void resetCoordinate() {
        currentCoordinate = new CellCoordinate(startCoordinate);
    }
}
