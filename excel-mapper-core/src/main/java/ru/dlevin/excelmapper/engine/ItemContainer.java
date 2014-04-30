package ru.dlevin.excelmapper.engine;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import ru.dlevin.excelmapper.exceptions.ItemCreationException;
import ru.dlevin.excelmapper.utils.CellUtils;

import java.util.Map;

/**
 * User: Dmitry Levin
 * Date: 08.03.14
 */
public class ItemContainer implements Rectangle {

    private Cursor cursor;
    private CellRectangle rectangle;
    private final ConversionEngine conversionEngine;
    private final Sheet sheet;

    public Sheet getSheet() {
        return sheet;
    }

    public ItemContainer(ConversionEngine conversionEngine, Sheet sheet) {
        this(conversionEngine, sheet, CellCoordinate.ZERO);
    }

    public ItemContainer(ConversionEngine conversionEngine, Sheet sheet, CellCoordinate origin) {
        this.conversionEngine = conversionEngine;
        this.sheet = sheet;
        this.cursor = new Cursor(origin);
        this.rectangle = new CellRectangle(origin, origin);
    }

    public ItemContainer(ConversionEngine conversionEngine, Sheet sheet, CellCoordinate origin, MovementDirection direction) {
        this.conversionEngine = conversionEngine;
        this.sheet = sheet;
        this.cursor = new Cursor(origin, direction);
        this.rectangle = new CellRectangle(origin, origin);
    }

    public CellCoordinate getTopLeftCorner() {
        return rectangle.getTopLeftCorner();
    }

    public CellCoordinate getTopRightCorner() {
        return rectangle.getTopRightCorner();
    }

    public CellCoordinate getBottomLeftCorner() {
        return rectangle.getBottomLeftCorner();
    }

    public CellCoordinate getBottomRightCorner() {
        return rectangle.getBottomRightCorner();
    }

    @Override
    public int getRowCount() {
        return rectangle.getRowCount();
    }

    @Override
    public int getColumnCount() {
        return rectangle.getColumnCount();
    }

    public void setCursorCoordinate(CellCoordinate cursorCoordinate) {
        cursor.setCurrentCoordinate(cursorCoordinate);
    }

    public CellCoordinate getCursorCoordinate() {
        return cursor.getCurrentCoordinate();
    }

    public ItemContainer addItem(Object item, CellGroup cellGroup) {
        for (Map.Entry<CellCoordinate, CellDefinition> entry : cellGroup.getEntries()) {
            CellCoordinate coordinateInGroup = entry.getKey();
            CellDefinition cellDefinition = entry.getValue();
            CellCoordinate sheetCellCoordinate = this.getCursorCoordinate().plusCoordinate(coordinateInGroup);
            ReadableValueReference valueRef = cellDefinition.getValueRef();
            if (valueRef instanceof ContextAware) {
                ((ContextAware)valueRef).setContext(item);
            }
            Object propertyValue = valueRef.getValue();
            setCellStyle(sheetCellCoordinate, item, cellDefinition.getCellStyleReference());
            setCellValue(sheetCellCoordinate, conversionEngine.convert(String.class, propertyValue));
            setCellSpans(sheetCellCoordinate, cellDefinition.getRowSpan(), cellDefinition.getColSpan());
        }
        this.cellGroupAdded(cellGroup);
        return this;
    }


    public ItemContainer addItems(Iterable items, CellGroup cellGroup) {
        for (Object item : items) {
            addItem(item, cellGroup);
        }
        return this;
    }

    public ItemContainer readItem(Object existingItem, CellGroup cellGroup) {
        for (Map.Entry<CellCoordinate, CellDefinition> entry : cellGroup.getEntries()) {
            CellCoordinate coordinateInGroup = entry.getKey();
            CellDefinition cellDefinition = entry.getValue();
            CellCoordinate sheetCellCoordinate = this.getCursorCoordinate().plusCoordinate(coordinateInGroup);
            ReadableValueReference<?> valueRef = cellDefinition.getValueRef();
            if (valueRef instanceof WritableValueReference) {
                if (valueRef instanceof PropertyValueReference) {
                    ((PropertyValueReference)valueRef).setContext(existingItem);
                }
                ((WritableValueReference)valueRef).setValue(conversionEngine.convert(valueRef.getType(), getCellValue(
                    sheetCellCoordinate)));
            }
        }
        this.cellGroupAdded(cellGroup);
        return this;
    }

    public <T> T readItem(Class<T> itemClass, CellGroup cellGroup) {
        try {
            T item = itemClass.newInstance();
            readItem(item, cellGroup);
            return item;
        } catch (InstantiationException e) {
            handleItemCreationException(itemClass, e);
        } catch (IllegalAccessException e) {
            handleItemCreationException(itemClass, e);
        }
        return null;
    }

    private void handleItemCreationException(Class itemClass, Exception e) {
        throw new ItemCreationException("Can not create new item of class " + itemClass.getName(), e);
    }

    private void setCellStyle(CellCoordinate cellCoordinate, Object item, CellStyleReference cellStyleReference) {
        if (cellStyleReference != null) {
            if (cellStyleReference instanceof ContextAware) {
                ((ContextAware)cellStyleReference).setContext(item);
            }
            CellStyle cellStyle = cellStyleReference.getCellStyle();
            if (cellStyle != null) {
                CellUtils.setCellStyle(sheet, cellCoordinate, cellStyle);
            }
        }
    }

    private void setCellValue(CellCoordinate cellCoordinate, Object value) {
        CellUtils.setValue(sheet, cellCoordinate, value);
    }

    private Object getCellValue(CellCoordinate cellCoordinate) {
        return CellUtils.getValue(sheet, cellCoordinate);
    }

    private void setCellSpans(CellCoordinate cellCoordinate, int rowSpan, int colSpan) {
        if (rowSpan != 1 || colSpan != 1) {
            CellUtils.setCellSpans(sheet, cellCoordinate, rowSpan, colSpan);
        }
    }

    private void cellGroupAdded(CellGroup cellGroup) {
        CellRectangle cellGroupRectangle = new CellRectangle(cellGroup);
        cellGroupRectangle.moveBy(cursor.getCurrentCoordinate()); //cell group coordinates are relative to cursor
        rectangle.include(cellGroupRectangle);
        cursor.moveBy(cellGroupRectangle);
    }
}
