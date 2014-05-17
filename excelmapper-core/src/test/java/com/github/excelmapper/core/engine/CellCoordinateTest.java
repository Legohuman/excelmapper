package com.github.excelmapper.core.engine;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: Dmitry Levin
 * Date: 03.04.14
 */
public class CellCoordinateTest {

    @Test
    public void testGetters() {
        CellCoordinate coordinate = new CellCoordinate(1, 1);
        assertEquals(1, coordinate.getColumn());
        assertEquals(1, coordinate.getRow());
    }

    @Test
    public void testPlusCoordinate() {
        CellCoordinate coordinate = new CellCoordinate(1, 1);
        CellCoordinate newCoord = coordinate.plusCoordinate(2, 5);
        assertEquals(3, newCoord.getColumn());
        assertEquals(6, newCoord.getRow());
        assertEquals(1, coordinate.getColumn());
        assertEquals(1, coordinate.getRow());
    }

    @Test(expected = ArithmeticException.class)
    public void testPlusCoordinateOverflowRow() {
        CellCoordinate coordinate = new CellCoordinate(1, 1);
        CellCoordinate newCoord = coordinate.plusCoordinate(1, Integer.MAX_VALUE);
    }

    @Test(expected = ArithmeticException.class)
    public void testPlusCoordinateOverflowColumn() {
        CellCoordinate coordinate = new CellCoordinate(1, 1);
        CellCoordinate newCoord = coordinate.plusCoordinate(Integer.MAX_VALUE, 1);
    }

    @Test(expected = ArithmeticException.class)
    public void testPlusCoordinateUnderflowRow() {
        CellCoordinate coordinate = new CellCoordinate(1, -1);
        CellCoordinate newCoord = coordinate.plusCoordinate(1, Integer.MIN_VALUE);
    }

    @Test(expected = ArithmeticException.class)
    public void testPlusCoordinateUnderflowColumn() {
        CellCoordinate coordinate = new CellCoordinate(-1, 1);
        CellCoordinate newCoord = coordinate.plusCoordinate(Integer.MIN_VALUE, 1);
    }

    @Test
    public void testMinusCoordinateIntArgs() {
        CellCoordinate coordinate = new CellCoordinate(1, 1);
        CellCoordinate newCoord = coordinate.minusCoordinate(2, 5);
        assertEquals(-1, newCoord.getColumn());
        assertEquals(-4, newCoord.getRow());
        assertEquals(1, coordinate.getColumn());
        assertEquals(1, coordinate.getRow());
    }

    @Test
    public void testMinusCoordinateCoordArg() {
        CellCoordinate coord1 = new CellCoordinate(1, 1);
        CellCoordinate coord2 = new CellCoordinate(2, 4);
        CellCoordinate diff1_2 = coord1.minusCoordinate(coord2);
        CellCoordinate diff2_1 = coord2.minusCoordinate(coord1);
        assertEquals(-1, diff1_2.getColumn());
        assertEquals(-3, diff1_2.getRow());
        assertEquals(1, diff2_1.getColumn());
        assertEquals(3, diff2_1.getRow());
        assertEquals(1, coord1.getColumn());
        assertEquals(1, coord1.getRow());
        assertEquals(2, coord2.getColumn());
        assertEquals(4, coord2.getRow());
    }

    @Test(expected = ArithmeticException.class)
    public void testMinusCoordinateOverflowRow() {
        CellCoordinate coordinate = new CellCoordinate(1, 1);
        CellCoordinate newCoord = coordinate.minusCoordinate(1, Integer.MIN_VALUE + 1);
    }

    @Test(expected = ArithmeticException.class)
    public void testMinusCoordinateOverflowColumn() {
        CellCoordinate coordinate = new CellCoordinate(1, 1);
        CellCoordinate newCoord = coordinate.minusCoordinate(Integer.MIN_VALUE + 1, 1);
    }

    @Test(expected = ArithmeticException.class)
    public void testMinusCoordinateUnderflowRow() {
        CellCoordinate coordinate = new CellCoordinate(1, -2);
        CellCoordinate newCoord = coordinate.minusCoordinate(1, Integer.MAX_VALUE);
    }

    @Test(expected = ArithmeticException.class)
    public void testMinusCoordinateUnderflowColumn() {
        CellCoordinate coordinate = new CellCoordinate(-2, 1);
        CellCoordinate newCoord = coordinate.minusCoordinate(Integer.MAX_VALUE, 1);
    }

    @Test
    public void testNextRow() {
        CellCoordinate coordinate = new CellCoordinate(1, 1);
        CellCoordinate newCoord = coordinate.nextRow();
        assertEquals(1, newCoord.getColumn());
        assertEquals(2, newCoord.getRow());
        assertEquals(1, coordinate.getColumn());
        assertEquals(1, coordinate.getRow());
    }

    @Test
    public void testNextColumn() {
        CellCoordinate coordinate = new CellCoordinate(1, 1);
        CellCoordinate newCoord = coordinate.nextColumn();
        assertEquals(2, newCoord.getColumn());
        assertEquals(1, newCoord.getRow());
        assertEquals(1, coordinate.getColumn());
        assertEquals(1, coordinate.getRow());
    }

    @Test
    public void testPrevRow() {
        CellCoordinate coordinate = new CellCoordinate(1, 1);
        CellCoordinate newCoord = coordinate.prevRow();
        assertEquals(1, newCoord.getColumn());
        assertEquals(0, newCoord.getRow());
        assertEquals(1, coordinate.getColumn());
        assertEquals(1, coordinate.getRow());
    }

    @Test
    public void testPrevColumn() {
        CellCoordinate coordinate = new CellCoordinate(1, 1);
        CellCoordinate newCoord = coordinate.prevColumn();
        assertEquals(0, newCoord.getColumn());
        assertEquals(1, newCoord.getRow());
        assertEquals(1, coordinate.getColumn());
        assertEquals(1, coordinate.getRow());
    }

    @Test
    public void testPlusRow() {
        CellCoordinate coordinate = new CellCoordinate(1, 1);
        CellCoordinate newCoord = coordinate.plusRow(5);
        assertEquals(1, newCoord.getColumn());
        assertEquals(6, newCoord.getRow());
        assertEquals(1, coordinate.getColumn());
        assertEquals(1, coordinate.getRow());
    }

    @Test
    public void testPlusColumn() {
        CellCoordinate coordinate = new CellCoordinate(1, 1);
        CellCoordinate newCoord = coordinate.plusColumn(5);
        assertEquals(6, newCoord.getColumn());
        assertEquals(1, newCoord.getRow());
        assertEquals(1, coordinate.getColumn());
        assertEquals(1, coordinate.getRow());
    }

    @Test
    public void testMinusRow() {
        CellCoordinate coordinate = new CellCoordinate(1, 1);
        CellCoordinate newCoord = coordinate.minusRow(5);
        assertEquals(1, newCoord.getColumn());
        assertEquals(-4, newCoord.getRow());
        assertEquals(1, coordinate.getColumn());
        assertEquals(1, coordinate.getRow());
    }

    @Test
    public void testMinusColumn() {
        CellCoordinate coordinate = new CellCoordinate(1, 1);
        CellCoordinate newCoord = coordinate.minusColumn(5);
        assertEquals(-4, newCoord.getColumn());
        assertEquals(1, newCoord.getRow());
        assertEquals(1, coordinate.getColumn());
        assertEquals(1, coordinate.getRow());
    }

    @Test
    public void testTranspose() {
        CellCoordinate coordinate = new CellCoordinate(1, 2);
        CellCoordinate newCoord = coordinate.transpose();
        assertEquals(2, newCoord.getColumn());
        assertEquals(1, newCoord.getRow());
        assertEquals(1, coordinate.getColumn());
        assertEquals(2, coordinate.getRow());
    }


    @Test
    public void testInvertRow() {
        CellCoordinate coordinate = new CellCoordinate(1, 2);
        CellCoordinate newCoord = coordinate.invertRow();
        assertEquals(1, newCoord.getColumn());
        assertEquals(-2, newCoord.getRow());
        assertEquals(1, coordinate.getColumn());
        assertEquals(2, coordinate.getRow());
    }

    @Test
    public void testInvertColumn() {
        CellCoordinate coordinate = new CellCoordinate(1, 2);
        CellCoordinate newCoord = coordinate.invertColumn();
        assertEquals(-1, newCoord.getColumn());
        assertEquals(2, newCoord.getRow());
        assertEquals(1, coordinate.getColumn());
        assertEquals(2, coordinate.getRow());
    }

    @Test
    public void testInvertCoordinate() {
        CellCoordinate coordinate = new CellCoordinate(1, 2);
        CellCoordinate newCoord = coordinate.invertCoordinate();
        assertEquals(-1, newCoord.getColumn());
        assertEquals(-2, newCoord.getRow());
        assertEquals(1, coordinate.getColumn());
        assertEquals(2, coordinate.getRow());
    }

    @Test
    public void testDeltaRow() {
        CellCoordinate coord1 = new CellCoordinate(1, 2);
        CellCoordinate coord2 = new CellCoordinate(1, 12);
        assertEquals(10, coord1.deltaRow(coord2));
        assertEquals(10, coord2.deltaRow(coord1));
    }

    @Test
    public void testDeltaColumn() {
        CellCoordinate coord1 = new CellCoordinate(1, 2);
        CellCoordinate coord2 = new CellCoordinate(12, 2);
        assertEquals(11, coord1.deltaColumn(coord2));
        assertEquals(11, coord2.deltaColumn(coord1));
    }

    @Test
    public void testDeltaCoordinateIntArgs() {
        CellCoordinate coord1 = new CellCoordinate(1, 12);
        CellCoordinate coord2 = new CellCoordinate(12, 2);
        assertEquals(new CellCoordinate(11, 10), coord1.deltaCoordinate(12, 2));
        assertEquals(new CellCoordinate(11, 10), coord2.deltaCoordinate(1, 12));
    }

    @Test
    public void testDeltaCoordinateCoordArg() {
        CellCoordinate coord1 = new CellCoordinate(1, 12);
        CellCoordinate coord2 = new CellCoordinate(12, 2);
        assertEquals(new CellCoordinate(11, 10), coord1.deltaCoordinate(coord2));
        assertEquals(new CellCoordinate(11, 10), coord2.deltaCoordinate(coord1));
    }

    @Test(expected = ArithmeticException.class)
    public void testDeltaCoordinateOverflow() {
        CellCoordinate coord1 = new CellCoordinate(1, 0);
        CellCoordinate coord2 = new CellCoordinate(Integer.MIN_VALUE, 1);
        coord1.deltaCoordinate(coord2);
    }

    @Test(expected = ArithmeticException.class)
    public void testDeltaCoordinateUnderflow() {
        CellCoordinate coord1 = new CellCoordinate(-2, 0);
        CellCoordinate coord2 = new CellCoordinate(Integer.MAX_VALUE, 1);
        coord1.deltaCoordinate(coord2);
    }

    @Test
    public void testGetTopLeftCorner() {
        CellCoordinate coord1 = new CellCoordinate(1, 12);
        CellCoordinate coord2 = new CellCoordinate(12, 2);
        assertEquals(new CellCoordinate(1, 2), coord1.getTopLeftCorner(coord2));
        assertEquals(new CellCoordinate(1, 2), coord2.getTopLeftCorner(coord1));
    }

    @Test
    public void testGetTopRightCorner() {
        CellCoordinate coord1 = new CellCoordinate(1, 12);
        CellCoordinate coord2 = new CellCoordinate(12, 2);
        assertEquals(new CellCoordinate(12, 2), coord1.getTopRightCorner(coord2));
        assertEquals(new CellCoordinate(12, 2), coord2.getTopRightCorner(coord1));
    }

    @Test
    public void testGetBottomLeftCorner() {
        CellCoordinate coord1 = new CellCoordinate(1, 12);
        CellCoordinate coord2 = new CellCoordinate(12, 2);
        assertEquals(new CellCoordinate(1, 12), coord1.getBottomLeftCorner(coord2));
        assertEquals(new CellCoordinate(1, 12), coord2.getBottomLeftCorner(coord1));
    }

    @Test
    public void testGetBottomRightCorner() {
        CellCoordinate coord1 = new CellCoordinate(1, 12);
        CellCoordinate coord2 = new CellCoordinate(12, 2);
        assertEquals(new CellCoordinate(12, 12), coord1.getBottomRightCorner(coord2));
        assertEquals(new CellCoordinate(12, 12), coord2.getBottomRightCorner(coord1));
    }

    @Test
    public void testToString() {
        new CellCoordinate(1, 1).toString();
    }
}
