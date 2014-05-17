package com.github.excelmapper.core.engine;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * User: Dmitry Levin
 * Date: 07.04.14
 */
public class CompositeRectangleTest {

    @Test
    public void testCreateEmptyCompositeRectangle() {
        CellRectangle rect1 = new CellRectangle(CellCoordinate.ZERO, new CellCoordinate(2, 5));
        CompositeRectangle compRect = new CompositeRectangle();
        assertEquals(null, compRect.getTopLeftCorner());
        assertEquals(null, compRect.getTopRightCorner());
        assertEquals(null, compRect.getBottomLeftCorner());
        assertEquals(null, compRect.getBottomRightCorner());
        assertEquals(0, compRect.getColumnCount());
        assertEquals(0, compRect.getRowCount());
        assertEquals(0, compRect.size());
        assertTrue(compRect.isEmpty());
        assertFalse(compRect.contains(rect1));
        assertFalse(compRect.containsAll(Arrays.asList(rect1)));
    }

    @Test
    public void testAddRectangle() {
        CellRectangle rect1 = new CellRectangle(CellCoordinate.ZERO, new CellCoordinate(2, 5));
        CompositeRectangle compRect = new CompositeRectangle();
        compRect.add(rect1);
        assertEquals(CellCoordinate.ZERO, compRect.getTopLeftCorner());
        assertEquals(new CellCoordinate(2, 0), compRect.getTopRightCorner());
        assertEquals(new CellCoordinate(0, 5), compRect.getBottomLeftCorner());
        assertEquals(new CellCoordinate(2, 5), compRect.getBottomRightCorner());
        assertEquals(3, compRect.getColumnCount());
        assertEquals(6, compRect.getRowCount());
        assertEquals(1, compRect.size());
        assertFalse(compRect.isEmpty());
        assertTrue(compRect.contains(rect1));
        assertTrue(compRect.containsAll(Arrays.asList(rect1)));
    }

    @Test
    public void testRemoveRectangle() {
        CellRectangle rect1 = new CellRectangle(CellCoordinate.ZERO, new CellCoordinate(2, 5));
        CellRectangle rect2 = new CellRectangle(new CellCoordinate(1, 1), new CellCoordinate(3, 6));
        CompositeRectangle compRect = new CompositeRectangle();
        compRect.add(rect1);
        compRect.add(rect2);
        compRect.remove(rect1);
        assertEquals(new CellCoordinate(1, 1), compRect.getTopLeftCorner());
        assertEquals(new CellCoordinate(3, 1), compRect.getTopRightCorner());
        assertEquals(new CellCoordinate(1, 6), compRect.getBottomLeftCorner());
        assertEquals(new CellCoordinate(3, 6), compRect.getBottomRightCorner());
        assertEquals(3, compRect.getColumnCount());
        assertEquals(6, compRect.getRowCount());
        assertEquals(1, compRect.size());
        assertFalse(compRect.isEmpty());
        assertFalse(compRect.contains(rect1));
        assertTrue(compRect.contains(rect2));
        assertFalse(compRect.containsAll(Arrays.asList(rect1)));
        assertTrue(compRect.containsAll(Arrays.asList(rect2)));
    }

    @Test
    public void testRemoveAllRectangle() {
        CellRectangle rect1 = new CellRectangle(CellCoordinate.ZERO, new CellCoordinate(2, 5));
        CellRectangle rect2 = new CellRectangle(new CellCoordinate(1, 1), new CellCoordinate(3, 6));
        CompositeRectangle compRect = new CompositeRectangle();
        compRect.add(rect1);
        compRect.add(rect2);
        compRect.removeAll(Arrays.asList(rect1));
        assertEquals(new CellCoordinate(1, 1), compRect.getTopLeftCorner());
        assertEquals(new CellCoordinate(3, 1), compRect.getTopRightCorner());
        assertEquals(new CellCoordinate(1, 6), compRect.getBottomLeftCorner());
        assertEquals(new CellCoordinate(3, 6), compRect.getBottomRightCorner());
        assertEquals(3, compRect.getColumnCount());
        assertEquals(6, compRect.getRowCount());
        assertEquals(1, compRect.size());
        assertFalse(compRect.isEmpty());
        assertFalse(compRect.contains(rect1));
        assertTrue(compRect.contains(rect2));
        assertFalse(compRect.containsAll(Arrays.asList(rect1)));
        assertTrue(compRect.containsAll(Arrays.asList(rect2)));
    }

    @Test
    public void testRetainAllRectangle() {
        CellRectangle rect1 = new CellRectangle(CellCoordinate.ZERO, new CellCoordinate(2, 5));
        CellRectangle rect2 = new CellRectangle(new CellCoordinate(1, 1), new CellCoordinate(3, 6));
        CompositeRectangle compRect = new CompositeRectangle();
        compRect.add(rect1);
        compRect.add(rect2);
        compRect.retainAll(Arrays.asList(rect2));
        assertEquals(new CellCoordinate(1, 1), compRect.getTopLeftCorner());
        assertEquals(new CellCoordinate(3, 1), compRect.getTopRightCorner());
        assertEquals(new CellCoordinate(1, 6), compRect.getBottomLeftCorner());
        assertEquals(new CellCoordinate(3, 6), compRect.getBottomRightCorner());
        assertEquals(3, compRect.getColumnCount());
        assertEquals(6, compRect.getRowCount());
        assertEquals(1, compRect.size());
        assertFalse(compRect.isEmpty());
        assertFalse(compRect.contains(rect1));
        assertTrue(compRect.contains(rect2));
        assertFalse(compRect.containsAll(Arrays.asList(rect1)));
        assertTrue(compRect.containsAll(Arrays.asList(rect2)));
    }

    @Test
    public void testDynamicExpandingInnerRectanglesSizeChange() {
        CellRectangle rect1 = new CellRectangle(CellCoordinate.ZERO, new CellCoordinate(2, 5));
        CellRectangle rect2 = new CellRectangle(new CellCoordinate(0, 6), new CellCoordinate(0, 8));
        CompositeRectangle compRect = new CompositeRectangle(new HashSet<Rectangle>(Arrays.asList(rect1, rect2)));
        assertEquals(CellCoordinate.ZERO, compRect.getTopLeftCorner());
        assertEquals(new CellCoordinate(2, 0), compRect.getTopRightCorner());
        assertEquals(new CellCoordinate(0, 8), compRect.getBottomLeftCorner());
        assertEquals(new CellCoordinate(2, 8), compRect.getBottomRightCorner());
        assertEquals(3, compRect.getColumnCount());
        assertEquals(9, compRect.getRowCount());
        rect2.include(new CellCoordinate(1, 10));
        rect1.include(new CellCoordinate(3, 2));
        assertEquals(CellCoordinate.ZERO, compRect.getTopLeftCorner());
        assertEquals(new CellCoordinate(3, 0), compRect.getTopRightCorner());
        assertEquals(new CellCoordinate(0, 10), compRect.getBottomLeftCorner());
        assertEquals(new CellCoordinate(3, 10), compRect.getBottomRightCorner());
        assertEquals(4, compRect.getColumnCount());
        assertEquals(11, compRect.getRowCount());
    }

    @Test
    public void testClearRectangles() {
        CellRectangle rect1 = new CellRectangle(CellCoordinate.ZERO, new CellCoordinate(2, 5));
        CellRectangle rect2 = new CellRectangle(new CellCoordinate(0, 6), new CellCoordinate(0, 8));
        CompositeRectangle compRect = new CompositeRectangle(new HashSet<Rectangle>(Arrays.asList(rect1, rect2)));
        assertEquals(CellCoordinate.ZERO, compRect.getTopLeftCorner());
        assertEquals(new CellCoordinate(2, 0), compRect.getTopRightCorner());
        assertEquals(new CellCoordinate(0, 8), compRect.getBottomLeftCorner());
        assertEquals(new CellCoordinate(2, 8), compRect.getBottomRightCorner());
        assertEquals(3, compRect.getColumnCount());
        assertEquals(9, compRect.getRowCount());
        compRect.clear();
        assertNull(compRect.getTopLeftCorner());
        assertNull(compRect.getTopRightCorner());
        assertNull(compRect.getBottomLeftCorner());
        assertNull(compRect.getBottomRightCorner());
        assertEquals(0, compRect.getColumnCount());
        assertEquals(0, compRect.getRowCount());
    }
}
