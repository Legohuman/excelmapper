package ru.dlevin.excelmapper.engine;

import org.apache.poi.ss.usermodel.CellStyle;

/**
 * User: Dmitry Levin
 * Date: 07.03.14
 */
public class StaticCellStyleReference implements CellStyleReference {
    private final CellStyle cellStyle;

    public StaticCellStyleReference(CellStyle cellStyle) {
        this.cellStyle = cellStyle;
    }

    @Override
    public CellStyle getCellStyle() {
        return cellStyle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StaticCellStyleReference that = (StaticCellStyleReference)o;

        if (cellStyle != null ? !cellStyle.equals(that.cellStyle) : that.cellStyle != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return cellStyle != null ? cellStyle.hashCode() : 0;
    }
}
