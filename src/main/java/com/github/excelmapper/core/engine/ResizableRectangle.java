package com.github.excelmapper.core.engine;

/**
 * User: Dmitry Levin
 * Date: 14.03.14
 */
public interface ResizableRectangle extends Rectangle {

    void include(CellCoordinate coordinate);

    void include(CellRectangle rectangle);

    void moveBy(CellCoordinate delta);
}
