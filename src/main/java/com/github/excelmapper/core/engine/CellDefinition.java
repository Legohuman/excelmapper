package com.github.excelmapper.core.engine;

import com.github.excelmapper.core.utils.Validate;

/**
 * User: Dmitry Levin
 * Date: 07.03.14
 */
public class CellDefinition<T> implements Rectangle {

    public static final CellStyleReference DEFAULT_CELL_STYLE_REF = CellStyleReference.UNDEFINED;
    public static final int DEFAULT_ROW_SPAN = 1;
    public static final int DEFAULT_COL_SPAN = 1;

    private ValueReference<T> valueRef;
    private CellStyleReference cellStyleReference = DEFAULT_CELL_STYLE_REF;
    private int rowSpan = DEFAULT_ROW_SPAN;
    private int colSpan = DEFAULT_COL_SPAN;

    public CellDefinition(ValueReference<T> valueRef) {
        this(valueRef, DEFAULT_CELL_STYLE_REF);
    }

    public CellDefinition(ValueReference<T> valueRef, CellStyleReference cellStyleReference) {
        this(valueRef, cellStyleReference, DEFAULT_COL_SPAN, DEFAULT_ROW_SPAN);
    }

    public CellDefinition(ValueReference<T> valueRef, int colSpan, int rowSpan) {
        this(valueRef, DEFAULT_CELL_STYLE_REF, colSpan, rowSpan);
    }

    public CellDefinition(ValueReference<T> valueRef, CellStyleReference cellStyleReference, int colSpan, int rowSpan) {
        setValueRef(valueRef);
        setCellStyleReference(cellStyleReference);
        setRowSpan(rowSpan);
        setColSpan(colSpan);
    }

    public ValueReference<T> getValueRef() {
        return valueRef;
    }

    public void setValueRef(ValueReference<T> valueRef) {
        Validate.notNull(valueRef, "Can not set null value reference for cell definition.");
        this.valueRef = valueRef;
    }

    public CellStyleReference getCellStyleReference() {
        return cellStyleReference;
    }

    public void setCellStyleReference(CellStyleReference cellStyleReference) {
        Validate.notNull(cellStyleReference, "Can not set null style reference for cell definition.");
        this.cellStyleReference = cellStyleReference;
    }

    public int getRowSpan() {
        return rowSpan;
    }

    public void setRowSpan(int rowSpan) {
        Validate.isTrue(rowSpan > 0, "Can not set row span less or equal to zero.");
        this.rowSpan = rowSpan;
    }

    public int getColSpan() {
        return colSpan;
    }

    public void setColSpan(int colSpan) {
        Validate.isTrue(colSpan > 0, "Can not set column span less or equal to zero.");
        this.colSpan = colSpan;
    }

    public CellDefinition withValue(ValueReference<?> valueRef) {
        return new CellDefinition(valueRef, this.cellStyleReference, this.colSpan, this.rowSpan);
    }

    @Override
    public CellCoordinate getTopLeftCorner() {
        return CellCoordinate.ZERO;
    }

    @Override
    public CellCoordinate getTopRightCorner() {
        return new CellCoordinate(colSpan - 1, 0);
    }

    @Override
    public CellCoordinate getBottomLeftCorner() {
        return new CellCoordinate(0, rowSpan - 1);
    }

    @Override
    public CellCoordinate getBottomRightCorner() {
        return new CellCoordinate(colSpan - 1, rowSpan - 1);
    }

    @Override
    public int getRowCount() {
        return rowSpan;
    }

    @Override
    public int getColumnCount() {
        return colSpan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CellDefinition that = (CellDefinition)o;

        if (colSpan != that.colSpan) return false;
        if (rowSpan != that.rowSpan) return false;
        if (cellStyleReference != null ? !cellStyleReference.equals(that.cellStyleReference) : that.cellStyleReference != null) {
            return false;
        }
        if (valueRef != null ? !valueRef.equals(that.valueRef) : that.valueRef != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = valueRef != null ? valueRef.hashCode() : 0;
        result = 31 * result + (cellStyleReference != null ? cellStyleReference.hashCode() : 0);
        result = 31 * result + rowSpan;
        result = 31 * result + colSpan;
        return result;
    }
}
