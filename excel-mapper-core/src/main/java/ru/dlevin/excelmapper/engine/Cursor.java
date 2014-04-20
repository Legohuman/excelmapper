package ru.dlevin.excelmapper.engine;

import ru.dlevin.excelmapper.utils.Validate;

/**
 * User: Dmitry Levin
 * Date: 14.03.14
 */
public class Cursor {
    private CellCoordinate currentCoordinate;
    private MovementDirection direction;

    public Cursor(CellCoordinate currentCoordinate, MovementDirection direction) {
        Validate.notNull(currentCoordinate, "Cursor coordinate can not be null");
        Validate.notNull(direction, "Cursor movement direction can not be null");
        this.currentCoordinate = currentCoordinate;
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
}
