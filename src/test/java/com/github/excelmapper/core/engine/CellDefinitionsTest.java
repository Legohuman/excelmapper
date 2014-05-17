package com.github.excelmapper.core.engine;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * User: Dmitry Levin
 * Date: 06.04.14
 */
public class CellDefinitionsTest {

    @Test
    public void testCreationEmpty() {
        CellDefinitions defs = new CellDefinitions();
        assertEquals(0, defs.size());
        assertTrue(defs.isEmpty());
    }

    @Test
    public void testCreationFromCollection() {
        CellDefinition def1 = new CellDefinition(new StaticValueReference<Integer>(1));
        CellDefinition def2 = new CellDefinition(new StaticValueReference<Integer>(2));
        CellDefinitions defs = new CellDefinitions(Arrays.asList(def1, def2));
        assertEquals(2, defs.size());
        assertFalse(defs.isEmpty());
        assertTrue(defs.contains(def1));
        assertTrue(defs.contains(def2));
        assertTrue(defs.containsAll(Arrays.asList(def1, def2)));
    }

    @Test
    public void testCreationFromArray() {
        CellDefinition def1 = new CellDefinition(new StaticValueReference<Integer>(1));
        CellDefinition def2 = new CellDefinition(new StaticValueReference<Integer>(2));
        CellDefinitions defs = new CellDefinitions(def1, def2);
        assertEquals(2, defs.size());
        assertFalse(defs.isEmpty());
        assertTrue(defs.contains(def1));
        assertTrue(defs.contains(def2));
        assertTrue(defs.containsAll(Arrays.asList(def1, def2)));
    }

    @Test
    public void testCreationFromReferencesCollection() {
        StaticValueReference<Integer> ref = new StaticValueReference<Integer>(1);
        CellDefinitions defs = CellDefinitions.fromReferences(Arrays.<ValueReference<?>>asList(ref));
        assertEquals(1, defs.size());
        assertFalse(defs.isEmpty());
        Iterator<CellDefinition> iterator = defs.iterator();
        assertTrue(iterator.hasNext());
        CellDefinition def = iterator.next();
        assertNotNull(def);
        assertEquals(ref, def.getValueRef());
        assertEquals(DynamicCellStyleReference.UNDEFINED, def.getCellStyleReference());
        assertEquals(1, def.getRowSpan());
        assertEquals(1, def.getColSpan());
    }

    @Test
    public void testCreationFromReferencesArray() {
        StaticValueReference<Integer> ref = new StaticValueReference<Integer>(1);
        CellDefinitions defs = CellDefinitions.fromReferences(ref);
        assertEquals(1, defs.size());
        assertFalse(defs.isEmpty());
        Iterator<CellDefinition> iterator = defs.iterator();
        assertTrue(iterator.hasNext());
        CellDefinition def = iterator.next();
        assertNotNull(def);
        assertEquals(ref, def.getValueRef());
        assertEquals(DynamicCellStyleReference.UNDEFINED, def.getCellStyleReference());
        assertEquals(1, def.getRowSpan());
        assertEquals(1, def.getColSpan());
    }

    @Test
    public void testAdd() {
        Workbook wb = new XSSFWorkbook();
        CellStyle cellStyle = wb.createCellStyle();
        CellDefinition def1 = new CellDefinition(new StaticValueReference<Integer>(1));
        CellDefinition def2 = new CellDefinition(new StaticValueReference<Integer>(2));
        CellDefinitions defs = new CellDefinitions(def1);
        StaticCellStyleReference styleRef = new StaticCellStyleReference(cellStyle);
        defs.setCellStyleReference(styleRef);
        assertEquals(1, defs.size());
        assertTrue(defs.contains(def1));
        defs.add(def2);
        assertEquals(2, defs.size());
        assertTrue(defs.contains(def1));
        assertTrue(defs.contains(def2));
        assertTrue(defs.containsAll(Arrays.asList(def1, def2)));
        assertEquals(CellStyleReference.UNDEFINED, def1.getCellStyleReference());
        assertEquals(styleRef, def2.getCellStyleReference());
    }

    @Test
    public void testCreationWithCellStyleReference() {
        Workbook wb = new XSSFWorkbook();
        CellStyle cellStyle = wb.createCellStyle();
        CellDefinition def1 = new CellDefinition(new StaticValueReference<Integer>(1));
        CellDefinition def2 = new CellDefinition(new StaticValueReference<Integer>(2));
        StaticCellStyleReference styleRef = new StaticCellStyleReference(cellStyle);
        CellDefinitions defs = new CellDefinitions(styleRef);
        defs.add(def1);
        defs.add(def2);
        assertEquals(2, defs.size());
        assertTrue(defs.contains(def1));
        assertTrue(defs.contains(def2));
        assertTrue(defs.containsAll(Arrays.asList(def1, def2)));
        assertEquals(styleRef, def1.getCellStyleReference());
        assertEquals(styleRef, def2.getCellStyleReference());
    }

    @Test
    public void testCreationWithCellStyleReferenceAndCellDefinitions() {
        Workbook wb = new XSSFWorkbook();
        CellStyle cellStyle = wb.createCellStyle();
        CellDefinition def1 = new CellDefinition(new StaticValueReference<Integer>(1));
        CellDefinition def2 = new CellDefinition(new StaticValueReference<Integer>(2));
        StaticCellStyleReference styleRef = new StaticCellStyleReference(cellStyle);
        CellDefinitions defs = new CellDefinitions(styleRef, def1, def2);
        defs.add(def1);
        defs.add(def2);
        assertEquals(4, defs.size());
        assertTrue(defs.contains(def1));
        assertTrue(defs.contains(def2));
        assertTrue(defs.containsAll(Arrays.asList(def1, def2)));
        assertEquals(styleRef, def1.getCellStyleReference());
        assertEquals(styleRef, def2.getCellStyleReference());
    }

    @Test
    public void testAddReference() {
        Workbook wb = new XSSFWorkbook();
        CellStyle cellStyle = wb.createCellStyle();
        CellDefinition def1 = new CellDefinition(new StaticValueReference<Integer>(1));

        CellDefinitions defs = new CellDefinitions(def1);
        StaticCellStyleReference styleRef = new StaticCellStyleReference(cellStyle);
        defs.setCellStyleReference(styleRef);
        assertEquals(1, defs.size());
        assertTrue(defs.contains(def1));
        defs.add(new StaticValueReference<Integer>(2));
        assertEquals(2, defs.size());
        Iterator<CellDefinition> iterator = defs.iterator();
        iterator.next();
        CellDefinition def2 = iterator.next();
        assertTrue(defs.contains(def1));
        assertTrue(defs.contains(def2));
        assertTrue(defs.containsAll(Arrays.asList(def1, def2)));
        assertEquals(CellStyleReference.UNDEFINED, def1.getCellStyleReference());
        assertEquals(styleRef, def2.getCellStyleReference());
    }

    @Test
    public void testAddReferencesCollection() {
        StaticValueReference<Integer> ref = new StaticValueReference<Integer>(1);
        CellDefinitions defs = new CellDefinitions();
        defs.addReferences(Arrays.<ValueReference<?>>asList(ref));
        assertEquals(1, defs.size());
        assertFalse(defs.isEmpty());
        Iterator<CellDefinition> iterator = defs.iterator();
        assertTrue(iterator.hasNext());
        CellDefinition def = iterator.next();
        assertNotNull(def);
        assertEquals(ref, def.getValueRef());
        assertEquals(CellStyleReference.UNDEFINED, def.getCellStyleReference());
        assertEquals(1, def.getRowSpan());
        assertEquals(1, def.getColSpan());
    }

    @Test
    public void testAddReferencesArray() {
        StaticValueReference<Integer> ref = new StaticValueReference<Integer>(1);
        CellDefinitions defs = new CellDefinitions();
        defs.addReferences(ref);
        assertEquals(1, defs.size());
        assertFalse(defs.isEmpty());
        Iterator<CellDefinition> iterator = defs.iterator();
        assertTrue(iterator.hasNext());
        CellDefinition def = iterator.next();
        assertNotNull(def);
        assertEquals(ref, def.getValueRef());
        assertEquals(CellStyleReference.UNDEFINED, def.getCellStyleReference());
        assertEquals(1, def.getRowSpan());
        assertEquals(1, def.getColSpan());
    }

    @Test
    public void testAddReferencesCommonStyleApplication() {
        Workbook wb = new XSSFWorkbook();
        CellStyle cellStyle = wb.createCellStyle();
        StaticValueReference<Integer> ref = new StaticValueReference<Integer>(1);
        CellDefinitions defs = new CellDefinitions();
        StaticCellStyleReference styleRef = new StaticCellStyleReference(cellStyle);
        defs.setCellStyleReference(styleRef);
        defs.addReferences(ref);
        assertEquals(1, defs.size());
        assertFalse(defs.isEmpty());
        Iterator<CellDefinition> iterator = defs.iterator();
        assertTrue(iterator.hasNext());
        CellDefinition def = iterator.next();
        assertNotNull(def);
        assertEquals(ref, def.getValueRef());
        assertEquals(styleRef, def.getCellStyleReference());
        assertEquals(1, def.getRowSpan());
        assertEquals(1, def.getColSpan());
    }

    @Test
    public void testAddAll() {
        CellDefinition def1 = new CellDefinition(new StaticValueReference<Integer>(1));
        CellDefinition def2 = new CellDefinition(new StaticValueReference<Integer>(2));
        CellDefinitions defs = new CellDefinitions();
        assertEquals(0, defs.size());
        assertTrue(defs.isEmpty());
        defs.addAll(Arrays.asList(def1, def2));
        assertEquals(2, defs.size());
        assertTrue(defs.contains(def1));
        assertTrue(defs.contains(def2));
        assertTrue(defs.containsAll(Arrays.asList(def1, def2)));
    }

    @Test
    public void testRemove() {
        CellDefinition def1 = new CellDefinition(new StaticValueReference<Integer>(1));
        CellDefinition def2 = new CellDefinition(new StaticValueReference<Integer>(2));
        CellDefinitions defs = new CellDefinitions(def1, def2);
        assertEquals(2, defs.size());
        assertFalse(defs.isEmpty());
        defs.remove(def2);
        assertEquals(1, defs.size());
        assertTrue(defs.contains(def1));
        assertFalse(defs.contains(def2));
    }

    @Test
    public void testRemoveAll() {
        CellDefinition def1 = new CellDefinition(new StaticValueReference<Integer>(1));
        CellDefinition def2 = new CellDefinition(new StaticValueReference<Integer>(2));
        CellDefinitions defs = new CellDefinitions(def1, def2);
        assertEquals(2, defs.size());
        assertFalse(defs.isEmpty());
        defs.removeAll(Arrays.asList(def1, def2));
        assertEquals(0, defs.size());
        assertTrue(defs.isEmpty());
        assertFalse(defs.contains(def1));
        assertFalse(defs.contains(def2));
    }

    @Test
    public void testRetainAll() {
        CellDefinition def1 = new CellDefinition(new StaticValueReference<Integer>(1));
        CellDefinition def2 = new CellDefinition(new StaticValueReference<Integer>(2));
        CellDefinitions defs = new CellDefinitions(def1, def2);
        assertEquals(2, defs.size());
        assertFalse(defs.isEmpty());
        defs.retainAll(Arrays.asList(def1));
        assertEquals(1, defs.size());
        assertFalse(defs.isEmpty());
        assertTrue(defs.contains(def1));
        assertFalse(defs.contains(def2));
    }

    @Test
    public void testClear() {
        CellDefinition def1 = new CellDefinition(new StaticValueReference<Integer>(1));
        CellDefinition def2 = new CellDefinition(new StaticValueReference<Integer>(2));
        CellDefinitions defs = new CellDefinitions(def1, def2);
        assertEquals(2, defs.size());
        assertFalse(defs.isEmpty());
        defs.clear();
        assertEquals(0, defs.size());
        assertTrue(defs.isEmpty());
        assertFalse(defs.contains(def1));
        assertFalse(defs.contains(def2));
    }

    @Test
    public void testToArray() {
        CellDefinition def1 = new CellDefinition(new StaticValueReference<Integer>(1));
        CellDefinition def2 = new CellDefinition(new StaticValueReference<Integer>(2));
        CellDefinitions defs = new CellDefinitions(def1, def2);
        Object[] defsArr = defs.toArray();
        assertEquals(2, defsArr.length);
        assertEquals(def1, defsArr[0]);
        assertEquals(def2, defsArr[1]);
    }

    @Test
    public void testToExistingArray() {
        CellDefinition def1 = new CellDefinition(new StaticValueReference<Integer>(1));
        CellDefinition def2 = new CellDefinition(new StaticValueReference<Integer>(2));
        CellDefinitions defs = new CellDefinitions(def1, def2);
        CellDefinition[] defsArr = new CellDefinition[defs.size()];
        defs.toArray(defsArr);
        assertEquals(2, defsArr.length);
        assertEquals(def1, defsArr[0]);
        assertEquals(def2, defsArr[1]);
    }
}
