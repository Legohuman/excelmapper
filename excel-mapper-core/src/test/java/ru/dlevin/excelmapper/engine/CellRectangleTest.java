package ru.dlevin.excelmapper.engine;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * User: Dmitry Levin
 * Date: 11.03.14
 */
public class CellRectangleTest {

    @Test
    public void testCreateSimpleRectangle() {
        CellRectangle rect = new CellRectangle(CellCoordinate.ZERO, new CellCoordinate(3, 2));
        assertCorrectRectangle4Corners(rect,
            CellCoordinate.ZERO,
            new CellCoordinate(3, 0),
            new CellCoordinate(0, 2),
            new CellCoordinate(3, 2));
    }

    @Test
    public void testCreateRectangleFromCoordinate() {
        CellRectangle rect = new CellRectangle(new CellCoordinate(1, 1));
        assertEquals(new CellCoordinate(1, 1), rect.getTopLeftCorner());
        assertEquals(new CellCoordinate(1, 1), rect.getTopRightCorner());
        assertEquals(new CellCoordinate(1, 1), rect.getBottomLeftCorner());
        assertEquals(new CellCoordinate(1, 1), rect.getBottomRightCorner());
        assertEquals(1, rect.getColumnCount());
        assertEquals(1, rect.getRowCount());
    }

    @Test
    public void testEquals() {
        CellRectangle rect1 = new CellRectangle(new CellCoordinate(1, 1));
        CellRectangle rect2 = new CellRectangle(new CellCoordinate(1, 1), new CellCoordinate(1, 1));
        Set<Rectangle> rectangles = new HashSet<Rectangle>();
        rectangles.add(rect1);
        assertEquals(1, rectangles.size());
        assertTrue(rectangles.contains(rect1));

        rectangles.add(rect1);
        assertEquals(1, rectangles.size());
        assertTrue(rectangles.contains(rect1));
        assertTrue(rectangles.contains(rect2));
    }

    @Test
    public void testCreateRevertedCornersRectangle() {
        CellRectangle rect = new CellRectangle(new CellCoordinate(3, 2), CellCoordinate.ZERO);
        assertCorrectRectangle4Corners(rect,
            CellCoordinate.ZERO,
            new CellCoordinate(3, 0),
            new CellCoordinate(0, 2),
            new CellCoordinate(3, 2));
    }

    @Test
    public void testCreateNegativeCornersRectangle() {
        CellRectangle rect = new CellRectangle(new CellCoordinate(-3, -2), CellCoordinate.ZERO);
        assertCorrectRectangle4Corners(rect,
            new CellCoordinate(-3, -2),
            new CellCoordinate(0, -2),
            new CellCoordinate(-3, 0),
            CellCoordinate.ZERO);
    }

    @Test
    public void testCreateTheBiggestRectangle() {
        CellRectangle rect = new CellRectangle(new CellCoordinate(Integer.MIN_VALUE, Integer.MIN_VALUE),
            new CellCoordinate(Integer.MAX_VALUE, Integer.MAX_VALUE));
        assertCorrectRectangle4Corners(rect,
            new CellCoordinate(Integer.MIN_VALUE, Integer.MIN_VALUE),
            new CellCoordinate(Integer.MAX_VALUE, Integer.MIN_VALUE),
            new CellCoordinate(Integer.MIN_VALUE, Integer.MAX_VALUE),
            new CellCoordinate(Integer.MAX_VALUE, Integer.MAX_VALUE));
    }

    @Test
    public void testIncludeCoordinateOutsideRectangle() {
        CellRectangle rect = new CellRectangle(CellCoordinate.ZERO, new CellCoordinate(3, 2));
        rect.include(new CellCoordinate(5, 4));
        rect.include(new CellCoordinate(-2, -2));
        assertCorrectRectangle2Corners(rect, new CellCoordinate(-2, -2), new CellCoordinate(5, 4));
    }

    @Test
    public void testIncludeCoordinateInsideRectangle() {
        CellRectangle rect = new CellRectangle(CellCoordinate.ZERO, new CellCoordinate(3, 2));
        rect.include(new CellCoordinate(1, 1));
        rect.include(new CellCoordinate(3, 2));
        rect.include(new CellCoordinate(0, 2));
        assertCorrectRectangle2Corners(rect, CellCoordinate.ZERO, new CellCoordinate(3, 2));
    }

    @Test
    public void testIncludeIntersectingRectangle() {
        CellRectangle rect = new CellRectangle(CellCoordinate.ZERO, new CellCoordinate(3, 2));
        rect.include(new CellRectangle(new CellCoordinate(1, 1), new CellCoordinate(5, 4)));
        assertCorrectRectangle2Corners(rect, CellCoordinate.ZERO, new CellCoordinate(5, 4));
    }

    @Test
    public void testIncludeInnerRectangle() {
        CellRectangle rect = new CellRectangle(CellCoordinate.ZERO, new CellCoordinate(3, 2));
        rect.include(new CellRectangle(new CellCoordinate(1, 1), new CellCoordinate(1, 2)));
        assertCorrectRectangle2Corners(rect, CellCoordinate.ZERO, new CellCoordinate(3, 2));
    }

    @Test
    public void testIncludeOuterRectangle() {
        CellRectangle rect = new CellRectangle(CellCoordinate.ZERO, new CellCoordinate(3, 2));
        rect.include(new CellRectangle(new CellCoordinate(-2, -2), new CellCoordinate(5, 4)));
        assertCorrectRectangle2Corners(rect, new CellCoordinate(-2, -2), new CellCoordinate(5, 4));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFirstCornerIsNull() {
        new CellRectangle(null, new CellCoordinate(3, 2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSecondCornerIsNull() {
        new CellRectangle(new CellCoordinate(3, 2), null);
    }

    @Test
    public void testMoveByPositiveCoordinate() {
        CellRectangle rect = new CellRectangle(CellCoordinate.ZERO, new CellCoordinate(3, 2));
        rect.moveBy(new CellCoordinate(1, 1));
        assertCorrectRectangle2Corners(rect, new CellCoordinate(1, 1), new CellCoordinate(4, 3));
    }

    @Test
    public void testMoveByZeroCoordinate() {
        CellRectangle rect = new CellRectangle(CellCoordinate.ZERO, new CellCoordinate(3, 2));
        rect.moveBy(CellCoordinate.ZERO);
        assertCorrectRectangle2Corners(rect, CellCoordinate.ZERO, new CellCoordinate(3, 2));
    }

    @Test
    public void testMoveByNegativeCoordinate() {
        CellRectangle rect = new CellRectangle(CellCoordinate.ZERO, new CellCoordinate(3, 2));
        rect.moveBy(new CellCoordinate(-1, -2));
        assertCorrectRectangle2Corners(rect, new CellCoordinate(-1, -2), new CellCoordinate(2, 0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMoveByNullCoordinate() {
        CellRectangle rect = new CellRectangle(CellCoordinate.ZERO, new CellCoordinate(3, 2));
        rect.moveBy(null);
    }

    @Test
    public void testGetRowCount() {
        CellRectangle rect = new CellRectangle(CellCoordinate.ZERO, new CellCoordinate(3, 0));
        assertEquals(1, rect.getRowCount());
    }

    @Test
    public void testGetColumnCount() {
        CellRectangle rect = new CellRectangle(CellCoordinate.ZERO, new CellCoordinate(3, 0));
        assertEquals(4, rect.getColumnCount());
    }

    private void assertCorrectRectangle4Corners(CellRectangle rect, CellCoordinate topLeftExpected, CellCoordinate topRightExpected,
        CellCoordinate bottomLeftExpected, CellCoordinate bottomRightExpected) {
        assertNotNull(rect.getTopLeftCorner());
        assertNotNull(rect.getTopRightCorner());
        assertNotNull(rect.getBottomLeftCorner());
        assertNotNull(rect.getBottomRightCorner());
        assertEquals(topLeftExpected, rect.getTopLeftCorner());
        assertEquals(topRightExpected, rect.getTopRightCorner());
        assertEquals(bottomLeftExpected, rect.getBottomLeftCorner());
        assertEquals(bottomRightExpected, rect.getBottomRightCorner());
    }

    private void assertCorrectRectangle2Corners(CellRectangle rect, CellCoordinate topLeftExpected, CellCoordinate bottomRightExpected) {
        assertNotNull(rect.getTopLeftCorner());
        assertNotNull(rect.getBottomRightCorner());
        assertEquals(topLeftExpected, rect.getTopLeftCorner());
        assertEquals(bottomRightExpected, rect.getBottomRightCorner());
    }
}
