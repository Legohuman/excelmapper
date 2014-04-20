package ru.dlevin.excelmapper.engine;

import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * User: Dmitry Levin
 * Date: 16.03.14
 */
public class CompositeRectangle implements Collection<Rectangle>, Rectangle {

    private Set<Rectangle> rectangles;

    public CompositeRectangle() {
        this.rectangles = new HashSet<Rectangle>();
    }

    public CompositeRectangle(Set<Rectangle> rectangles) {
        this.rectangles = new HashSet<Rectangle>(rectangles);
    }

    @Override
    public int size() {
        return rectangles.size();
    }

    @Override
    public boolean isEmpty() {
        return rectangles.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return rectangles.contains(o);
    }

    @Override
    public Iterator<Rectangle> iterator() {
        return rectangles.iterator();
    }

    @Override
    public Object[] toArray() {
        return rectangles.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return rectangles.toArray(a);
    }

    @Override
    public boolean add(Rectangle rectangle) {
        return rectangles.add(rectangle);
    }

    @Override
    public boolean remove(Object o) {
        return rectangles.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return rectangles.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Rectangle> c) {
        return rectangles.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return rectangles.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return rectangles.retainAll(c);
    }

    @Override
    public void clear() {
        rectangles.clear();
    }

    @Override
    @Nullable
    public CellCoordinate getTopLeftCorner() {
        if (isEmpty()) return null;
        CellCoordinate coordinate = CellCoordinate.BOTTOM_RIGHT;
        for (Rectangle rectangle : rectangles) {
            coordinate = coordinate.getTopLeftCorner(rectangle.getTopLeftCorner());
        }
        return coordinate;
    }

    @Override
    @Nullable
    public CellCoordinate getTopRightCorner() {
        if (isEmpty()) return null;
        CellCoordinate coordinate = CellCoordinate.BOTTOM_LEFT;
        for (Rectangle rectangle : rectangles) {
            coordinate = coordinate.getTopRightCorner(rectangle.getTopRightCorner());
        }
        return coordinate;
    }

    @Override
    @Nullable
    public CellCoordinate getBottomLeftCorner() {
        if (isEmpty()) return null;
        CellCoordinate coordinate = CellCoordinate.TOP_RIGHT;
        for (Rectangle rectangle : rectangles) {
            coordinate = coordinate.getBottomLeftCorner(rectangle.getBottomLeftCorner());
        }
        return coordinate;
    }

    @Override
    @Nullable
    public CellCoordinate getBottomRightCorner() {
        if (isEmpty()) return null;
        CellCoordinate coordinate = CellCoordinate.TOP_LEFT;
        for (Rectangle rectangle : rectangles) {
            coordinate = coordinate.getBottomRightCorner(rectangle.getBottomRightCorner());
        }
        return coordinate;
    }

    @Override
    public int getRowCount() {
        return (getBottomLeftCorner() == null || getTopLeftCorner() == null) ? 0 : getBottomLeftCorner().deltaRow(getTopLeftCorner()) + 1;
    }

    @Override
    public int getColumnCount() {
        return (getBottomRightCorner() == null || getBottomLeftCorner() == null) ? 0 :
            getBottomRightCorner().deltaColumn(getBottomLeftCorner()) + 1;
    }
}
