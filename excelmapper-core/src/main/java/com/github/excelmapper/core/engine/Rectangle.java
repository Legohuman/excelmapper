package com.github.excelmapper.core.engine;

/**
 * User: Dmitry Levin
 * Date: 11.03.14
 */
public interface Rectangle {

    CellCoordinate getTopLeftCorner();

    CellCoordinate getTopRightCorner();

    CellCoordinate getBottomLeftCorner();

    CellCoordinate getBottomRightCorner();

    int getRowCount();

    int getColumnCount();
}
