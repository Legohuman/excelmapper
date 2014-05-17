package com.github.excelmapper.core.engine;

import com.github.excelmapper.core.utils.Validate;

/**
 * User: Dmitry Levin
 * Date: 11.03.14
 */
public class CellRectangle implements ResizableRectangle {

    private CellCoordinate topLeftCorner;

    private CellCoordinate bottomRightCorner;

    public CellRectangle(CellCoordinate corner1, CellCoordinate corner2) {
        createRectangle(corner1, corner2);
    }

    public CellRectangle(CellCoordinate coordinate) {
        this(coordinate, coordinate);
    }

    public CellRectangle(Rectangle rectangle) {
        Validate.notNull(rectangle, "Source rectangle can not be null");
        createRectangle(rectangle.getTopLeftCorner(), rectangle.getBottomRightCorner());
    }

    private void createRectangle(CellCoordinate corner1, CellCoordinate corner2) {
        Validate.notNull(corner1, "First corner rectangle can not be null");
        Validate.notNull(corner2, "Second corner rectangle can not be null");
        this.topLeftCorner = corner1.getTopLeftCorner(corner2);
        this.bottomRightCorner = corner1.getBottomRightCorner(corner2);
    }

    public CellCoordinate getTopLeftCorner() {
        return topLeftCorner;
    }

    public CellCoordinate getTopRightCorner() {
        return new CellCoordinate(bottomRightCorner.getColumn(), topLeftCorner.getRow());
    }

    public CellCoordinate getBottomLeftCorner() {
        return new CellCoordinate(topLeftCorner.getColumn(), bottomRightCorner.getRow());
    }

    public CellCoordinate getBottomRightCorner() {
        return bottomRightCorner;
    }

    @Override
    public int getRowCount() {
        return bottomRightCorner.deltaRow(topLeftCorner) + 1;
    }

    @Override
    public int getColumnCount() {
        return bottomRightCorner.deltaColumn(topLeftCorner) + 1;
    }

    public void moveBy(CellCoordinate delta) {
        Validate.notNull(delta, "Delta coordinate can not be null");
        topLeftCorner = topLeftCorner.plusCoordinate(delta);
        bottomRightCorner = bottomRightCorner.plusCoordinate(delta);
    }

    public void include(CellCoordinate coordinate) {
        Validate.notNull(coordinate, "Included coordinate can not be null");
        this.topLeftCorner = this.topLeftCorner.getTopLeftCorner(coordinate);
        this.bottomRightCorner = this.bottomRightCorner.getBottomRightCorner(coordinate);
    }

    public void include(CellRectangle rectangle) {
        Validate.notNull(rectangle, "Included rectangle can not be null");
        this.topLeftCorner = this.topLeftCorner.getTopLeftCorner(rectangle.getTopLeftCorner());
        this.bottomRightCorner = this.bottomRightCorner.getBottomRightCorner(rectangle.getBottomRightCorner());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CellRectangle rectangle = (CellRectangle)o;

        if (!bottomRightCorner.equals(rectangle.bottomRightCorner)) return false;
        if (!topLeftCorner.equals(rectangle.topLeftCorner)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = topLeftCorner.hashCode();
        result = 31 * result + bottomRightCorner.hashCode();
        return result;
    }
}
