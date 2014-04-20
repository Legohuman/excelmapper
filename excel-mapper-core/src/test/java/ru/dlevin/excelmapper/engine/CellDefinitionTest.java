package ru.dlevin.excelmapper.engine;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: Dmitry Levin
 * Date: 04.04.14
 */
public class CellDefinitionTest {

    @Test
    public void testCreationWithValueRef() {
        StaticValueReference<Integer> valueRef = new StaticValueReference<Integer>(1);
        CellDefinition def = new CellDefinition(valueRef);
        assertEquals(valueRef, def.getValueRef());
        assertEquals(CellDefinition.DEFAULT_ROW_SPAN, def.getRowSpan());
        assertEquals(CellDefinition.DEFAULT_ROW_SPAN, def.getRowCount());
        assertEquals(CellDefinition.DEFAULT_COL_SPAN, def.getColSpan());
        assertEquals(CellDefinition.DEFAULT_COL_SPAN, def.getColumnCount());
        assertEquals(CellDefinition.DEFAULT_CELL_STYLE_REF, def.getCellStyleReference());
        assertEquals(CellCoordinate.ZERO, def.getTopLeftCorner());
        assertEquals(CellCoordinate.ZERO, def.getTopRightCorner());
        assertEquals(CellCoordinate.ZERO, def.getBottomLeftCorner());
        assertEquals(CellCoordinate.ZERO, def.getBottomRightCorner());
    }

    @Test
    public void testWithValue() {
        StaticValueReference<Integer> valueRef = new StaticValueReference<Integer>(1);
        StaticValueReference<Integer> newValueRef = new StaticValueReference<Integer>(2);
        Workbook wb = new XSSFWorkbook();
        CellStyle cellStyle = wb.createCellStyle();
        StaticCellStyleReference styleRef = new StaticCellStyleReference(cellStyle);
        CellDefinition def = new CellDefinition(valueRef, styleRef, 2, 3);
        CellDefinition newDef = def.withValue(newValueRef);
        assertEquals(newValueRef, newDef.getValueRef());
        assertEquals(3, newDef.getRowSpan());
        assertEquals(3, newDef.getRowCount());
        assertEquals(2, newDef.getColSpan());
        assertEquals(2, newDef.getColumnCount());
        assertEquals(styleRef, newDef.getCellStyleReference());
        assertEquals(CellCoordinate.ZERO, newDef.getTopLeftCorner());
        assertEquals(new CellCoordinate(1, 0), newDef.getTopRightCorner());
        assertEquals(new CellCoordinate(0, 2), newDef.getBottomLeftCorner());
        assertEquals(new CellCoordinate(1, 2), newDef.getBottomRightCorner());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testCreationIllegalValueRef() {
        new CellDefinition(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreationIllegalStyleRef() {
        new CellDefinition(new StaticValueReference<Integer>(1), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreationIllegalRowSpan() {
        Workbook wb = new XSSFWorkbook();
        CellStyle cellStyle = wb.createCellStyle();
        new CellDefinition(new StaticValueReference<Integer>(1), new StaticCellStyleReference(cellStyle), 1, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreationIllegalColSpan() {
        Workbook wb = new XSSFWorkbook();
        CellStyle cellStyle = wb.createCellStyle();
        new CellDefinition(new StaticValueReference<Integer>(1), new StaticCellStyleReference(cellStyle), -1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetIllegalValueRef() {
        CellDefinition def = new CellDefinition(new StaticValueReference<Integer>(1));
        def.setValueRef(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetIllegalStyleRef() {
        CellDefinition def = new CellDefinition(new StaticValueReference<Integer>(1));
        def.setCellStyleReference(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetIllegalRowSpan() {
        CellDefinition def = new CellDefinition(new StaticValueReference<Integer>(1));
        def.setRowSpan(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetIllegalColSpan() {
        CellDefinition def = new CellDefinition(new StaticValueReference<Integer>(1));
        def.setColSpan(-1);
    }

    @Test
    public void testEqualsWithStaticValueRef() {
        CellDefinition def1 = new CellDefinition(new StaticValueReference<Integer>(1));
        CellDefinition def2 = new CellDefinition(new StaticValueReference<Integer>(1));
        assertEquals(def1, def2);
    }

    @Test
    public void testEqualsWithStaticValueRefAndStyleRef() {
        Workbook wb = new XSSFWorkbook();
        CellStyle cellStyle = wb.createCellStyle();
        CellDefinition def1 = new CellDefinition(new StaticValueReference<Integer>(1), new StaticCellStyleReference(cellStyle));
        CellDefinition def2 = new CellDefinition(new StaticValueReference<Integer>(1), new StaticCellStyleReference(cellStyle));
        assertEquals(def1, def2);
    }

    @Test
    public void testEqualsWithStaticValueRefAndStyleRefAndSpans() {
        Workbook wb = new XSSFWorkbook();
        CellStyle cellStyle = wb.createCellStyle();
        CellDefinition def1 = new CellDefinition(new StaticValueReference<Integer>(1), new StaticCellStyleReference(cellStyle), 1, 2);
        CellDefinition def2 = new CellDefinition(new StaticValueReference<Integer>(1), new StaticCellStyleReference(cellStyle), 1, 2);
        assertEquals(def1, def2);
    }
}
