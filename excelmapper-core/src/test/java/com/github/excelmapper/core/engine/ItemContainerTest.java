package com.github.excelmapper.core.engine;

import com.github.excelmapper.core.utils.CellUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

import static com.github.excelmapper.core.engine.References.property;
import static com.github.excelmapper.core.engine.References.value;
import static org.junit.Assert.assertEquals;

/**
 * User: Dmitry Levin
 * Date: 15.03.14
 */
public class ItemContainerTest {

    @Test
    public void testAddItem() {
        HSSFSheet sheet = new HSSFWorkbook().createSheet();
        ItemContainer container = new ItemContainerFactory().createItemContainer(sheet);
        CellGroup header = new CellGroup();
        header.addCells(CellDefinitions.fromReferences(value("Name"), value("Age"), value("Salary")));
        container.addItem(null, header);
        assertEquals("Name", CellUtils.getValue(sheet, new CellCoordinate(0, 0)));
        assertEquals("Age", CellUtils.getValue(sheet, new CellCoordinate(1, 0)));
        assertEquals("Salary", CellUtils.getValue(sheet, new CellCoordinate(2, 0)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddItemUnresolvedProperty() {
        HSSFSheet sheet = new HSSFWorkbook().createSheet();
        ItemContainer container = new ItemContainerFactory().createItemContainer(sheet);
        CellGroup header = new CellGroup();
        header.addCells(CellDefinitions.fromReferences(property("name"), property("age"), property("salary")));
        container.addItem(null, header);
        assertEquals("Name", CellUtils.getValue(sheet, new CellCoordinate(0, 0)));
        assertEquals("Age", CellUtils.getValue(sheet, new CellCoordinate(1, 0)));
        assertEquals("Salary", CellUtils.getValue(sheet, new CellCoordinate(2, 0)));
    }
}
