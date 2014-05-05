package ru.dlevin.excelmapper.engine;

import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * User: Dmitry Levin
 * Date: 07.03.14
 */
public class CellGroup implements Rectangle {
    private final Map<CellCoordinate, CellDefinition> cells = new HashMap<CellCoordinate, CellDefinition>();

    private CellStyleReference cellStyleReference = CellStyleReference.UNDEFINED;

    private Cursor cursor = new Cursor(CellCoordinate.ZERO, MovementDirection.RIGHT);
    private ResizableRectangle rectangle = null;//undefined for empty group

    public CellStyleReference getCellStyleReference() {
        return cellStyleReference;
    }

    public void setCellStyleReference(CellStyleReference cellStyleReference) {
        this.cellStyleReference = cellStyleReference;
    }

    public MovementDirection getCursorMovementDirection() {
        return cursor.getMovementDirection();
    }

    public CellGroup setCursorMovementDirection(MovementDirection cursorMovementDirection) {
        cursor.setMovementDirection(cursorMovementDirection);
        return this;
    }

    public int cellsCount() {
        return cells.size();
    }

    public boolean hasNoCells() {
        return cells.isEmpty();
    }

    public boolean containsCellDefinition(CellDefinition cellDefinition) {
        return cells.containsValue(cellDefinition);
    }

    public boolean containsCoordinate(CellCoordinate coordinate) {
        return cells.containsKey(coordinate);
    }

    public CellDefinition getCellDefinition(CellCoordinate coordinate) {
        return cells.get(coordinate);
    }

    public CellGroup addCell(CellCoordinate coordinate, CellDefinition cellDefinition) {
        if (coordinate != null) {
            cursor.setCurrentCoordinate(coordinate);
        }
        if (!CellStyleReference.UNDEFINED.equals(cellStyleReference) &&
            CellStyleReference.UNDEFINED.equals(cellDefinition.getCellStyleReference())) {
            cellDefinition.setCellStyleReference(cellStyleReference);
        }
        cells.put(cursor.getCurrentCoordinate(), cellDefinition);
        CellRectangle addedCellRectangle = new CellRectangle(cellDefinition);
        addedCellRectangle.moveBy(cursor.getCurrentCoordinate());
        if (rectangle == null) {
            rectangle = new CellRectangle(addedCellRectangle);
        } else {
            rectangle.include(addedCellRectangle);
        }
        cursor.moveBy(addedCellRectangle);
        return this;
    }

    public CellGroup addCell(CellDefinition cellDefinition) {
        return this.addCell(null, cellDefinition);
    }

    public CellGroup addCell(CellCoordinate coordinate, ValueReference reference) {
        return this.addCell(coordinate, new CellDefinition(reference));
    }

    public CellGroup addCell(ValueReference reference) {
        return this.addCell(new CellDefinition(reference));
    }

    public CellGroup addCells(CellCoordinate coordinate, CellDefinitions cellDefinitions) {
        if (coordinate != null) {
            cursor.setCurrentCoordinate(coordinate);
        }
        for (CellDefinition definition : cellDefinitions) {
            this.addCell(definition);
        }
        return this;
    }

    public CellGroup addCells(CellDefinitions cellDefinitions) {
        return this.addCells(null, cellDefinitions);
    }

    @Nullable
    @Override
    public CellCoordinate getTopLeftCorner() {
        return rectangle == null ? null : rectangle.getTopLeftCorner();
    }


    @Nullable
    @Override
    public CellCoordinate getTopRightCorner() {
        return rectangle == null ? null : rectangle.getTopRightCorner();
    }

    @Nullable
    @Override
    public CellCoordinate getBottomLeftCorner() {
        return rectangle == null ? null : rectangle.getBottomLeftCorner();
    }

    @Nullable
    @Override
    public CellCoordinate getBottomRightCorner() {
        return rectangle == null ? null : rectangle.getBottomRightCorner();
    }

    @Override
    public int getRowCount() {
        return rectangle == null ? 0 : rectangle.getRowCount();
    }

    @Override
    public int getColumnCount() {
        return rectangle == null ? 0 : rectangle.getColumnCount();
    }

    public Set<CellCoordinate> getCoordinates() {
        return new HashSet<CellCoordinate>(cells.keySet());
    }

    public Collection<CellDefinition> getCellDefinitions() {
        return new ArrayList<CellDefinition>(cells.values());
    }

    public Set<Map.Entry<CellCoordinate, CellDefinition>> getEntries() {
        return new HashSet<Map.Entry<CellCoordinate, CellDefinition>>(cells.entrySet());
    }

    public CellGroup plusColumn(int column) {
        cursor.plusColumn(column);
        return this;
    }

    public CellGroup plusRow(int row) {
        cursor.plusRow(row);
        return this;
    }

    public CellGroup minusColumn(int column) {
        cursor.minusColumn(column);
        return this;
    }

    public CellGroup minusRow(int row) {
        cursor.minusRow(row);
        return this;
    }

    public CellGroup nextColumn() {
        cursor.nextColumn();
        return this;
    }

    public CellGroup nextRow() {
        cursor.nextRow();
        return this;
    }

    public CellGroup prevColumn() {
        cursor.prevColumn();
        return this;
    }

    public CellGroup prevRow() {
        cursor.prevRow();
        return this;
    }

    public CellGroup plusCoordinate(CellCoordinate coordinate) {
        cursor.plusCoordinate(coordinate);
        return this;
    }

    public CellGroup minusCoordinate(CellCoordinate coordinate) {
        cursor.minusCoordinate(coordinate);
        return this;
    }

    public CellGroup plusCoordinate(int column, int row) {
        cursor.plusCoordinate(column, row);
        return this;
    }

    public CellGroup minusCoordinate(int column, int row) {
        cursor.minusCoordinate(column, row);
        return this;
    }

    public CellGroup resetColumn() {
        cursor.resetColumn();
        return this;
    }

    public CellGroup resetRow() {
        cursor.resetRow();
        return this;
    }

    public CellGroup resetCoordinate() {
        cursor.resetCoordinate();
        return this;
    }
}
