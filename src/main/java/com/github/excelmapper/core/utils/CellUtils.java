package com.github.excelmapper.core.utils;

import com.github.excelmapper.core.engine.CellCoordinate;
import com.github.excelmapper.core.engine.CellRectangle;
import com.github.excelmapper.core.engine.Rectangle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * User: Dmitry Levin
 * Date: 08.03.14
 */
public class CellUtils {

    public static boolean mergeRegionExists(Sheet sheet, Rectangle rectangle) {
        for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
            CellRangeAddress region = sheet.getMergedRegion(i);
            CellCoordinate topLeftCorner = new CellCoordinate(region.getFirstColumn(), region.getFirstRow());
            CellCoordinate bottomRightCorner = new CellCoordinate(region.getLastColumn(), region.getLastRow());
            CellRectangle mergedRegionRectangle = new CellRectangle(topLeftCorner, bottomRightCorner);
            if (rectangle.equals(mergedRegionRectangle)) {
                return true;
            }
        }
        return false;
    }

    public static boolean cellExists(Sheet sheet, CellCoordinate cellCoordinate) {
        return getCell(sheet, cellCoordinate, false) != null;
    }

    public static void setValue(Sheet sheet, CellCoordinate cellCoordinate, Object value) {
        Cell cell = getCell(sheet, cellCoordinate, true);
        cell.setCellValue(StringUtils.emptyIfNull(value));
    }

    public static Object getValue(Sheet sheet, CellCoordinate cellCoordinate) {
        Cell cell = getCell(sheet, cellCoordinate, false);
        return cell == null ? null : determineTypeAndGetValue(cell);
    }

    private static Object determineTypeAndGetValue(Cell cell) {
        int cellType = cell.getCellType();
        if (Cell.CELL_TYPE_FORMULA == cellType) {
            cellType = cell.getCachedFormulaResultType();
        }
        return getValueAccordingToType(cell, cellType);
    }

    private static Object getValueAccordingToType(Cell cell, int dataType) {
        if (Cell.CELL_TYPE_ERROR == dataType || Cell.CELL_TYPE_BLANK == dataType) {
            return null;
        } else if (Cell.CELL_TYPE_BOOLEAN == dataType) {
            return cell.getBooleanCellValue();
        } else if (Cell.CELL_TYPE_NUMERIC == dataType) {
            if (DateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue();
            } else {
                return cell.getNumericCellValue();
            }
        } else if (Cell.CELL_TYPE_STRING == dataType) {
            return cell.getStringCellValue();
        } else {
            return null;
        }
    }

    public static void setCellStyle(Sheet sheet, CellCoordinate cellCoordinate, CellStyle cellStyle) {
        Cell cell = getCell(sheet, cellCoordinate, true);
        cell.setCellStyle(cellStyle);
    }

    public static void setCellSpans(Sheet sheet, CellCoordinate cellCoordinate, int rowSpan, int colSpan) {
        int rowNum = cellCoordinate.getRow();
        int colNum = cellCoordinate.getColumn();
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + rowSpan - 1, colNum, colNum + colSpan - 1));
    }

    private static Cell getCell(Sheet sheet, CellCoordinate cellCoordinate, boolean createIfNotExist) {
        int rowNum = cellCoordinate.getRow();
        int colNum = cellCoordinate.getColumn();
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            if (createIfNotExist) {
                row = sheet.createRow(rowNum);
            } else {
                return null;
            }
        }
        Cell cell = row.getCell(colNum);
        if (cell == null) {
            cell = createIfNotExist ? row.createCell(colNum) : null;
        }
        return cell;
    }
}
