package ru.dlevin.excelmapper.engine;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.util.Collection;
import java.util.Set;

import static org.junit.Assert.*;
import static ru.dlevin.excelmapper.engine.References.property;
import static ru.dlevin.excelmapper.engine.References.value;

/**
 * User: Dmitry Levin
 * Date: 15.03.14
 */
public class CellGroupTest {

    @Test
    public void testCornerCoordinates() {
        CellGroup group = new CellGroup();
        assertNull(group.getTopLeftCorner());
        assertNull(group.getTopRightCorner());
        assertNull(group.getBottomLeftCorner());
        assertNull(group.getBottomRightCorner());
        assertEquals(0, group.getColumnCount());
        group.addCell(new CellCoordinate(2, 2), new CellDefinition(new StaticValueReference<Integer>(1)));
        assertEquals(new CellCoordinate(2, 2), group.getTopLeftCorner());
        assertEquals(new CellCoordinate(2, 2), group.getTopRightCorner());
        assertEquals(new CellCoordinate(2, 2), group.getBottomLeftCorner());
        assertEquals(new CellCoordinate(2, 2), group.getBottomRightCorner());
        assertEquals(1, group.getColumnCount());
        group.addCell(new CellDefinition(new StaticValueReference<Integer>(1)));
        assertEquals(new CellCoordinate(2, 2), group.getTopLeftCorner());
        assertEquals(new CellCoordinate(3, 2), group.getTopRightCorner());
        assertEquals(new CellCoordinate(2, 2), group.getBottomLeftCorner());
        assertEquals(new CellCoordinate(3, 2), group.getBottomRightCorner());
        assertEquals(2, group.getColumnCount());
    }

    @Test
    public void testAddCellWithColSpan() {
        CellGroup group = new CellGroup();
        group.addCells(CellDefinitions.fromReferences(property("name"), property("post")));
        group.addCell(new CellDefinition(value("---"), 2, 1));
        group.addCell(property("age"));

        assertEquals(CellCoordinate.ZERO, group.getTopLeftCorner());
        assertEquals(new CellCoordinate(4, 0), group.getTopRightCorner());
        assertEquals(CellCoordinate.ZERO, group.getBottomLeftCorner());
        assertEquals(new CellCoordinate(4, 0), group.getBottomRightCorner());
        assertEquals(1, group.getRowCount());
        assertEquals(5, group.getColumnCount());
    }

    @Test
    public void testAddCellWithValueReference() {
        CellGroup group = new CellGroup();
        assertEquals(0, group.cellsCount());
        assertTrue(group.hasNoCells());
        StaticValueReference<Integer> ref1 = new StaticValueReference<Integer>(1);
        StaticValueReference<Integer> ref2 = new StaticValueReference<Integer>(2);
        group.addCell(new CellCoordinate(1, 1), ref1);
        group.addCell(new CellCoordinate(2, 1), ref2);

        assertEquals(MovementDirection.RIGHT, group.getCursorMovementDirection());
        assertEquals(new CellCoordinate(1, 1), group.getTopLeftCorner());
        assertEquals(new CellCoordinate(2, 1), group.getTopRightCorner());
        assertEquals(new CellCoordinate(1, 1), group.getBottomLeftCorner());
        assertEquals(new CellCoordinate(2, 1), group.getBottomRightCorner());
        assertEquals(1, group.getRowCount());
        assertEquals(2, group.getColumnCount());
        assertEquals(2, group.cellsCount());
        assertFalse(group.hasNoCells());
    }

    @Test
    public void testAddCells() {
        CellGroup group = new CellGroup();
        assertEquals(0, group.cellsCount());
        assertTrue(group.hasNoCells());
        CellDefinition def1 = new CellDefinition(new StaticValueReference<Integer>(1));
        CellDefinition def2 = new CellDefinition(new StaticValueReference<Integer>(2));
        group.addCells(new CellCoordinate(1, 1), new CellDefinitions(def1, def2));

        assertEquals(MovementDirection.RIGHT, group.getCursorMovementDirection());
        assertEquals(new CellCoordinate(1, 1), group.getTopLeftCorner());
        assertEquals(new CellCoordinate(2, 1), group.getTopRightCorner());
        assertEquals(new CellCoordinate(1, 1), group.getBottomLeftCorner());
        assertEquals(new CellCoordinate(2, 1), group.getBottomRightCorner());
        assertEquals(1, group.getRowCount());
        assertEquals(2, group.getColumnCount());
        assertEquals(2, group.cellsCount());
        assertFalse(group.hasNoCells());
    }


    @Test
    public void testCellStyleApplication() {
        Workbook wb = new XSSFWorkbook();
        CellStyle cellStyle = wb.createCellStyle();
        StaticCellStyleReference styleRef = new StaticCellStyleReference(cellStyle);
        CellGroup group = new CellGroup();
        group.setCellStyleReference(styleRef);
        CellDefinition def = new CellDefinition(new StaticValueReference<Integer>(1));
        assertEquals(CellStyleReference.UNDEFINED, def.getCellStyleReference());
        group.addCell(def);
        assertEquals(styleRef, def.getCellStyleReference());
    }

    @Test
    public void testCursorPositionMovementDirectionRight() {
        CellGroup group = new CellGroup();
        group.setCursorMovementDirection(MovementDirection.RIGHT);
        assertEquals(MovementDirection.RIGHT, group.getCursorMovementDirection());

        CellDefinition def1 = new CellDefinition(new StaticValueReference<Integer>(1));
        group.addCell(def1);
        CellDefinition addedCellDef1 = group.getCellDefinition(CellCoordinate.ZERO);
        assertEquals(def1, addedCellDef1);

        CellDefinition def2 = new CellDefinition(new StaticValueReference<Integer>(1));
        group.addCell(def2);
        CellDefinition addedCellDef2 = group.getCellDefinition(new CellCoordinate(1, 0));
        assertEquals(def2, addedCellDef2);

        CellDefinition def3 = new CellDefinition(new StaticValueReference<Integer>(1));
        group.addCell(new CellCoordinate(0, 1), def3);
        CellDefinition addedCellDef3 = group.getCellDefinition(new CellCoordinate(0, 1));
        assertEquals(def3, addedCellDef3);

        CellDefinition def4 = new CellDefinition(new StaticValueReference<Integer>(1));
        group.addCell(def4);
        CellDefinition addedCellDef4 = group.getCellDefinition(new CellCoordinate(1, 1));
        assertEquals(def4, addedCellDef4);

        assertEquals(CellCoordinate.ZERO, group.getTopLeftCorner());
        assertEquals(new CellCoordinate(1, 0), group.getTopRightCorner());
        assertEquals(new CellCoordinate(0, 1), group.getBottomLeftCorner());
        assertEquals(new CellCoordinate(1, 1), group.getBottomRightCorner());
        assertEquals(2, group.getColumnCount());
        assertEquals(2, group.getRowCount());
    }

    @Test
    public void testCursorPositionMovementDirectionDown() {
        CellGroup group = new CellGroup();
        group.setCursorMovementDirection(MovementDirection.DOWN);
        assertEquals(MovementDirection.DOWN, group.getCursorMovementDirection());

        CellDefinition def1 = new CellDefinition(new StaticValueReference<Integer>(1));
        group.addCell(def1);
        CellDefinition addedCellDef1 = group.getCellDefinition(CellCoordinate.ZERO);
        assertEquals(def1, addedCellDef1);

        CellDefinition def2 = new CellDefinition(new StaticValueReference<Integer>(1));
        group.addCell(def2);
        CellDefinition addedCellDef2 = group.getCellDefinition(new CellCoordinate(0, 1));
        assertEquals(def2, addedCellDef2);

        CellDefinition def3 = new CellDefinition(new StaticValueReference<Integer>(1));
        group.addCell(new CellCoordinate(1, 0), def3);
        CellDefinition addedCellDef3 = group.getCellDefinition(new CellCoordinate(1, 0));
        assertEquals(def3, addedCellDef3);

        CellDefinition def4 = new CellDefinition(new StaticValueReference<Integer>(1));
        group.addCell(def4);
        CellDefinition addedCellDef4 = group.getCellDefinition(new CellCoordinate(1, 1));
        assertEquals(def4, addedCellDef4);

        assertEquals(CellCoordinate.ZERO, group.getTopLeftCorner());
        assertEquals(new CellCoordinate(1, 0), group.getTopRightCorner());
        assertEquals(new CellCoordinate(0, 1), group.getBottomLeftCorner());
        assertEquals(new CellCoordinate(1, 1), group.getBottomRightCorner());
        assertEquals(2, group.getColumnCount());
        assertEquals(2, group.getRowCount());
    }

    @Test
    public void testCursorPositionMovementDirectionLeft() {
        CellGroup group = new CellGroup();
        group.setCursorMovementDirection(MovementDirection.LEFT);
        assertEquals(MovementDirection.LEFT, group.getCursorMovementDirection());

        CellDefinition def1 = new CellDefinition(new StaticValueReference<Integer>(1));
        group.addCell(def1);
        CellDefinition addedCellDef1 = group.getCellDefinition(CellCoordinate.ZERO);
        assertEquals(def1, addedCellDef1);

        CellDefinition def2 = new CellDefinition(new StaticValueReference<Integer>(1));
        group.addCell(def2);
        CellDefinition addedCellDef2 = group.getCellDefinition(new CellCoordinate(-1, 0));
        assertEquals(def2, addedCellDef2);

        CellDefinition def3 = new CellDefinition(new StaticValueReference<Integer>(1));
        group.addCell(new CellCoordinate(0, 1), def3);
        CellDefinition addedCellDef3 = group.getCellDefinition(new CellCoordinate(0, 1));
        assertEquals(def3, addedCellDef3);

        CellDefinition def4 = new CellDefinition(new StaticValueReference<Integer>(1));
        group.addCell(def4);
        CellDefinition addedCellDef4 = group.getCellDefinition(new CellCoordinate(-1, 1));
        assertEquals(def4, addedCellDef4);

        assertEquals(new CellCoordinate(-1, 0), group.getTopLeftCorner());
        assertEquals(new CellCoordinate(0, 0), group.getTopRightCorner());
        assertEquals(new CellCoordinate(-1, 1), group.getBottomLeftCorner());
        assertEquals(new CellCoordinate(0, 1), group.getBottomRightCorner());
        assertEquals(2, group.getColumnCount());
        assertEquals(2, group.getRowCount());
    }

    @Test
    public void testCursorPositionMovementDirectionUp() {
        CellGroup group = new CellGroup();
        group.setCursorMovementDirection(MovementDirection.UP);
        assertEquals(MovementDirection.UP, group.getCursorMovementDirection());

        CellDefinition def1 = new CellDefinition(new StaticValueReference<Integer>(1));
        group.addCell(def1);
        CellDefinition addedCellDef1 = group.getCellDefinition(CellCoordinate.ZERO);
        assertEquals(def1, addedCellDef1);

        CellDefinition def2 = new CellDefinition(new StaticValueReference<Integer>(1));
        group.addCell(def2);
        CellDefinition addedCellDef2 = group.getCellDefinition(new CellCoordinate(0, -1));
        assertEquals(def2, addedCellDef2);

        CellDefinition def3 = new CellDefinition(new StaticValueReference<Integer>(1));
        group.addCell(new CellCoordinate(1, 0), def3);
        CellDefinition addedCellDef3 = group.getCellDefinition(new CellCoordinate(1, 0));
        assertEquals(def3, addedCellDef3);

        CellDefinition def4 = new CellDefinition(new StaticValueReference<Integer>(1));
        group.addCell(def4);
        CellDefinition addedCellDef4 = group.getCellDefinition(new CellCoordinate(1, -1));
        assertEquals(def4, addedCellDef4);

        assertEquals(new CellCoordinate(0, -1), group.getTopLeftCorner());
        assertEquals(new CellCoordinate(1, -1), group.getTopRightCorner());
        assertEquals(new CellCoordinate(0, 0), group.getBottomLeftCorner());
        assertEquals(new CellCoordinate(1, 0), group.getBottomRightCorner());
        assertEquals(2, group.getColumnCount());
        assertEquals(2, group.getRowCount());
    }

    @Test
    public void testDefaultMovementDirection() {
        CellGroup group = new CellGroup();
        group.setCursorMovementDirection(MovementDirection.RIGHT);
        assertEquals(MovementDirection.RIGHT, group.getCursorMovementDirection());
    }

    @Test
    public void testOverrideCellDefinition() {
        CellGroup group = new CellGroup();
        group.setCursorMovementDirection(MovementDirection.RIGHT);

        CellDefinition def1 = new CellDefinition(new StaticValueReference<Integer>(1));
        group.addCell(def1);
        CellDefinition addedCellDef1 = group.getCellDefinition(CellCoordinate.ZERO);
        assertTrue(group.containsCoordinate(CellCoordinate.ZERO));
        assertEquals(def1, addedCellDef1);

        CellDefinition def2 = new CellDefinition(new StaticValueReference<Integer>(2));
        group.addCell(CellCoordinate.ZERO, def2);
        CellDefinition addedCellDef2 = group.getCellDefinition(CellCoordinate.ZERO);
        assertTrue(group.containsCoordinate(CellCoordinate.ZERO));
        assertEquals(def2, addedCellDef2);
        assertNotEquals(def1, addedCellDef2);
        assertFalse(group.containsCellDefinition(def1));
    }

    @Test
    public void testGetCellCoordinates() {
        CellGroup group = new CellGroup();
        group.setCursorMovementDirection(MovementDirection.RIGHT);
        CellDefinition def1 = new CellDefinition(new StaticValueReference<Integer>(1));
        CellDefinition def2 = new CellDefinition(new StaticValueReference<Integer>(2));
        group.addCells(new CellDefinitions(def1, def2));
        Set<CellCoordinate> coordinates = group.getCoordinates();
        assertEquals(2, coordinates.size());
        assertTrue(coordinates.contains(CellCoordinate.ZERO));
        assertTrue(coordinates.contains(new CellCoordinate(1, 0)));

        coordinates.clear();
        assertEquals(0, coordinates.size());

        Set otherCoordinates = group.getCoordinates();
        assertEquals(2, otherCoordinates.size());
        assertTrue(otherCoordinates.contains(CellCoordinate.ZERO));
        assertTrue(otherCoordinates.contains(new CellCoordinate(1, 0)));
    }

    @Test
    public void testGetCellDefinitions() {
        CellGroup group = new CellGroup();
        group.setCursorMovementDirection(MovementDirection.RIGHT);
        CellDefinition def1 = new CellDefinition(new StaticValueReference<Integer>(1));
        CellDefinition def2 = new CellDefinition(new StaticValueReference<Integer>(2));
        group.addCells(new CellDefinitions(def1, def2));
        Collection<CellDefinition> cellDefinitions = group.getCellDefinitions();
        assertEquals(2, cellDefinitions.size());
        assertTrue(cellDefinitions.contains(def1));
        assertTrue(cellDefinitions.contains(def2));

        cellDefinitions.clear();
        assertEquals(0, cellDefinitions.size());

        Collection<CellDefinition> otherCellDefinitions = group.getCellDefinitions();
        assertEquals(2, otherCellDefinitions.size());
        assertTrue(otherCellDefinitions.contains(def1));
        assertTrue(otherCellDefinitions.contains(def2));
    }
}
