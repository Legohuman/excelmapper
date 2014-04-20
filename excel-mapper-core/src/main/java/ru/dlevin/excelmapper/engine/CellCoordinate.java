package ru.dlevin.excelmapper.engine;

import ru.dlevin.excelmapper.utils.MathUtils;

/**
 * User: Dmitry Levin
 * Date: 06.03.14
 */
public class CellCoordinate implements Coordinate {
    public static final CellCoordinate ZERO = new CellCoordinate(0, 0);
    public static final CellCoordinate TOP_LEFT = new CellCoordinate(Integer.MIN_VALUE, Integer.MIN_VALUE);
    public static final CellCoordinate TOP_RIGHT = new CellCoordinate(Integer.MAX_VALUE, Integer.MIN_VALUE);
    public static final CellCoordinate BOTTOM_LEFT = new CellCoordinate(Integer.MIN_VALUE, Integer.MAX_VALUE);
    public static final CellCoordinate BOTTOM_RIGHT = new CellCoordinate(Integer.MAX_VALUE, Integer.MAX_VALUE);

    private final int row;
    private final int column;

    public CellCoordinate(int column, int row) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public CellCoordinate nextRow() {
        return plusCoordinate(0, 1);
    }

    public CellCoordinate nextColumn() {
        return plusCoordinate(1, 0);
    }

    public CellCoordinate prevRow() {
        return plusCoordinate(0, -1);
    }

    public CellCoordinate prevColumn() {
        return plusCoordinate(-1, 0);
    }

    public CellCoordinate plusRow(int row) {
        return plusCoordinate(0, row);
    }

    public CellCoordinate plusColumn(int column) {
        return plusCoordinate(column, 0);
    }

    public CellCoordinate plusCoordinate(CellCoordinate coordinate) {
        return plusCoordinate(coordinate.getColumn(), coordinate.getRow());
    }

    public CellCoordinate plusCoordinate(int column, int row) {
        return new CellCoordinate(MathUtils.safeAdd(this.column, column), MathUtils.safeAdd(this.row, row));
    }

    public CellCoordinate minusRow(int row) {
        return plusCoordinate(0, -row);
    }

    public CellCoordinate minusColumn(int column) {
        return plusCoordinate(-column, 0);
    }

    public CellCoordinate minusCoordinate(CellCoordinate coordinate) {
        return minusCoordinate(coordinate.getColumn(), coordinate.getRow());
    }

    public CellCoordinate minusCoordinate(int column, int row) {
        return plusCoordinate(-column, -row);
    }

    public CellCoordinate transpose() {
        return new CellCoordinate(this.row, this.column);
    }

    public CellCoordinate invertRow() {
        return new CellCoordinate(column, -row);
    }

    public CellCoordinate invertColumn() {
        return new CellCoordinate(-column, row);
    }

    public CellCoordinate invertCoordinate() {
        return new CellCoordinate(-column, -row);
    }

    public int deltaRow(CellCoordinate coordinate) {
        return Math.abs(MathUtils.safeSubtract(this.row, coordinate.getRow()));
    }

    public int deltaColumn(CellCoordinate coordinate) {
        return Math.abs(MathUtils.safeSubtract(this.column, coordinate.getColumn()));
    }

    public CellCoordinate deltaCoordinate(CellCoordinate coordinate) {
        return new CellCoordinate(deltaColumn(coordinate), deltaRow(coordinate));
    }

    public CellCoordinate deltaCoordinate(int column, int row) {
        return deltaCoordinate(new CellCoordinate(column, row));
    }

    public CellCoordinate getTopLeftCorner(CellCoordinate coordinate) {
        return new CellCoordinate(Math.min(this.getColumn(), coordinate.getColumn()), Math.min(this.getRow(), coordinate.getRow()));
    }

    public CellCoordinate getTopRightCorner(CellCoordinate coordinate) {
        return new CellCoordinate(Math.max(this.getColumn(), coordinate.getColumn()), Math.min(this.getRow(), coordinate.getRow()));
    }

    public CellCoordinate getBottomLeftCorner(CellCoordinate coordinate) {
        return new CellCoordinate(Math.min(this.getColumn(), coordinate.getColumn()), Math.max(this.getRow(), coordinate.getRow()));
    }

    public CellCoordinate getBottomRightCorner(CellCoordinate coordinate) {
        return new CellCoordinate(Math.max(this.getColumn(), coordinate.getColumn()), Math.max(this.getRow(), coordinate.getRow()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CellCoordinate that = (CellCoordinate)o;

        if (column != that.column) return false;
        if (row != that.row) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + column;
        return result;
    }

    @Override
    public String toString() {
        return "CellCoordinate{" +
            "column=" + column +
            ", row=" + row +
            '}';
    }
}
