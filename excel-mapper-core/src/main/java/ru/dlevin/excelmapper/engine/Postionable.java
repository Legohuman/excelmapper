package ru.dlevin.excelmapper.engine;

/**
 * User: Dmitry Levin
 * Date: 01.05.14
 */
public interface Postionable {

    MovementDirection getMovementDirection();

    Postionable setMovementDirection(MovementDirection direction);

    CellCoordinate getCurrentCoordinate();

    Postionable setCurrentCoordinate(CellCoordinate currentCoordinate);

    Postionable moveBy(Rectangle rectangle);

    Postionable plusColumn(int column);

    Postionable plusRow(int row);

    Postionable minusColumn(int column);

    Postionable minusRow(int row);

    Postionable nextColumn();

    Postionable nextRow();

    Postionable prevColumn();

    Postionable prevRow();

    Postionable plusCoordinate(CellCoordinate coordinate);

    Postionable minusCoordinate(CellCoordinate coordinate);

    Postionable plusCoordinate(int column, int row);

    Postionable minusCoordinate(int column, int row);

    Postionable resetColumn();

    Postionable resetRow();

    Postionable resetCoordinate();
}
