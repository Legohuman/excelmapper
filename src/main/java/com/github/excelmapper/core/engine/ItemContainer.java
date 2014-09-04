package com.github.excelmapper.core.engine;

import com.github.excelmapper.core.exceptions.ItemCreationException;
import com.github.excelmapper.core.utils.CellUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Map;

/**
 * User: Dmitry Levin
 * Date: 08.03.14
 */
public class ItemContainer implements Rectangle, Postionable {

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

    @Override
    public CellCoordinate getTopLeftCorner() {
        return rectangle.getTopLeftCorner();
    }

    @Override
    public CellCoordinate getTopRightCorner() {
        return rectangle.getTopRightCorner();
    }

    @Override
    public CellCoordinate getBottomLeftCorner() {
        return rectangle.getBottomLeftCorner();
    }

    @Override
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

    @Override
    public ItemContainer setCurrentCoordinate(CellCoordinate cursorCoordinate) {
        cursor.setCurrentCoordinate(cursorCoordinate);
        return this;
    }

    @Override
    public CellCoordinate getCurrentCoordinate() {
        return cursor.getCurrentCoordinate();
    }

    @Override
    public MovementDirection getMovementDirection() {
        return cursor.getMovementDirection();
    }

    @Override
    public ItemContainer setMovementDirection(MovementDirection direction) {
        cursor.setMovementDirection(direction);
        return this;
    }

    @Override
    public ItemContainer moveBy(Rectangle rectangle) {
        cursor.moveBy(rectangle);
        return this;
    }

    @Override
    public ItemContainer plusColumn(int column) {
        cursor.plusColumn(column);
        return this;
    }

    @Override
    public ItemContainer plusRow(int row) {
        cursor.plusRow(row);
        return this;
    }

    @Override
    public ItemContainer minusColumn(int column) {
        cursor.minusColumn(column);
        return this;
    }

    @Override
    public ItemContainer minusRow(int row) {
        cursor.minusRow(row);
        return this;
    }

    @Override
    public ItemContainer nextColumn() {
        cursor.nextColumn();
        return this;
    }

    @Override
    public ItemContainer nextRow() {
        cursor.nextRow();
        return this;
    }

    @Override
    public ItemContainer prevColumn() {
        cursor.prevColumn();
        return this;
    }

    @Override
    public ItemContainer prevRow() {
        cursor.prevColumn();
        return this;
    }

    @Override
    public ItemContainer plusCoordinate(CellCoordinate coordinate) {
        cursor.plusCoordinate(coordinate);
        return this;
    }

    @Override
    public ItemContainer minusCoordinate(CellCoordinate coordinate) {
        cursor.minusCoordinate(coordinate);
        return this;
    }

    @Override
    public ItemContainer plusCoordinate(int column, int row) {
        cursor.plusCoordinate(column, row);
        return this;
    }

    @Override
    public ItemContainer minusCoordinate(int column, int row) {
        cursor.minusCoordinate(column, row);
        return this;
    }

    @Override
    public ItemContainer resetColumn() {
        cursor.resetColumn();
        return this;
    }

    @Override
    public ItemContainer resetRow() {
        cursor.resetRow();
        return this;
    }

    @Override
    public ItemContainer resetCoordinate() {
        cursor.resetCoordinate();
        return this;
    }

    public ItemContainer addItem(Object item, CellGroup cellGroup) {
        for (Map.Entry<CellCoordinate, CellDefinition> entry : cellGroup.getEntries()) {
            CellCoordinate coordinateInGroup = entry.getKey();
            CellDefinition cellDefinition = entry.getValue();
            CellCoordinate sheetCellCoordinate = this.getCurrentCoordinate().plusCoordinate(coordinateInGroup);
            ValueReference valueRef = cellDefinition.getValueRef();
            if (valueRef instanceof ContextAware) {
                ((ContextAware)valueRef).setContext(item);
            }
            Object propertyValue = valueRef.getValue();
            setCellStyle(sheetCellCoordinate, item, cellDefinition.getColSpan(), cellDefinition.getRowSpan(),
                cellDefinition.getCellStyleReference());
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

    public boolean containsAnyCellGroupCell(CellGroup cellGroup) {
        for (CellCoordinate coordinateInGroup : cellGroup.getCoordinates()) {
            CellCoordinate sheetCellCoordinate = this.getCurrentCoordinate().plusCoordinate(coordinateInGroup);
            if (CellUtils.cellExists(sheet, sheetCellCoordinate)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsAllCellGroupCells(CellGroup cellGroup) {
        for (CellCoordinate coordinateInGroup : cellGroup.getCoordinates()) {
            CellCoordinate sheetCellCoordinate = this.getCurrentCoordinate().plusCoordinate(coordinateInGroup);
            if (!CellUtils.cellExists(sheet, sheetCellCoordinate)) {
                return false;
            }
        }
        return true;
    }

    public boolean containsAllCellGroupMergeRegions(CellGroup cellGroup) {
        for (CellDefinition cellDefinition : cellGroup.getCellDefinitions()) {
            CellRectangle sheetRectangle = new CellRectangle(cellDefinition);
            sheetRectangle.moveBy(this.getCurrentCoordinate());
            if (!CellUtils.mergeRegionExists(sheet, sheetRectangle)) {
                return false;
            }
        }
        return true;
    }

    public ItemContainer readItem(Object existingItem, CellGroup cellGroup, ProcessMessagesHolder messagesHolder) {
        for (Map.Entry<CellCoordinate, CellDefinition> entry : cellGroup.getEntries()) {
            CellCoordinate coordinateInGroup = entry.getKey();
            CellDefinition cellDefinition = entry.getValue();
            CellCoordinate sheetCellCoordinate = this.getCurrentCoordinate().plusCoordinate(coordinateInGroup);
            ValueReference valueRef = cellDefinition.getValueRef();
            if (valueRef instanceof ProcessMessagesHolderAware && messagesHolder != null) {
                ((ProcessMessagesHolderAware)valueRef).setMessageHolder(messagesHolder);
            }
            if (valueRef instanceof ContextAware) {
                ((ContextAware)valueRef).setContext(existingItem);
                try {
                    valueRef.setValue(conversionEngine.convert(valueRef.getType(), getCellValue(
                        sheetCellCoordinate)));
                } catch (Exception e) {
                    if (messagesHolder != null) {
                        messagesHolder.add(
                            new ProcessMessage(ProcessMessageType.ERROR, sheetCellCoordinate, existingItem, valueRef, e, "")
                        );
                    }
                    //todo think out logic without message holder
                }
            }
        }
        this.cellGroupAdded(cellGroup);
        return this;
    }

    public <T> T readItem(Class<T> itemClass, CellGroup cellGroup, ProcessMessagesHolder messagesHolder) {
        try {
            T item = itemClass.newInstance();
            readItem(item, cellGroup, messagesHolder);
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

    private void setCellStyle(CellCoordinate cellCoordinate, Object item, int colSpan, int rowSpan, CellStyleReference cellStyleReference) {
        if (cellStyleReference != null) {
            if (cellStyleReference instanceof ContextAware) {
                ((ContextAware)cellStyleReference).setContext(item);
            }
            CellStyle cellStyle = cellStyleReference.getCellStyle();
            if (cellStyle != null) {
                for (int rowNum = 1; rowNum <= rowSpan; rowNum++) {
                    for (int colNum = 1; colNum <= colSpan; colNum++) {
                        CellUtils.setCellStyle(getSheet(), cellCoordinate.plusCoordinate(colNum - 1, rowNum - 1), cellStyle);
                    }
                }
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
