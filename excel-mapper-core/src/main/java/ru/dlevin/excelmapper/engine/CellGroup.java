package ru.dlevin.excelmapper.engine;

import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * User: Dmitry Levin
 * Date: 07.03.14
 */
public class CellGroup<T> implements Rectangle {
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
        return cursor.getDirection();
    }

    public void setCursorMovementDirection(MovementDirection cursorMovementDirection) {
        cursor.setDirection(cursorMovementDirection);
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

    public CellGroup addCell(CellCoordinate coordinate, ReadableValueReference reference) {
        return this.addCell(coordinate, new CellDefinition(reference));
    }

    public CellGroup addCell(ReadableValueReference reference) {
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
    public CellCoordinate getTopLeftCorner() {
        return rectangle == null ? null : rectangle.getTopLeftCorner();
    }


    @Nullable
    public CellCoordinate getTopRightCorner() {
        return rectangle == null ? null : rectangle.getTopRightCorner();
    }

    @Nullable
    public CellCoordinate getBottomLeftCorner() {
        return rectangle == null ? null : rectangle.getBottomLeftCorner();
    }

    @Nullable
    public CellCoordinate getBottomRightCorner() {
        return rectangle == null ? null : rectangle.getBottomRightCorner();
    }

    public int getRowCount() {
        return rectangle == null ? 0 : rectangle.getRowCount();
    }

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
}
