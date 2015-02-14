package com.github.excelmapper.core.utils;

import com.github.excelmapper.core.engine.CellCoordinate;
import com.github.excelmapper.core.engine.CellRectangle;
import com.github.excelmapper.core.engine.Rectangle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;

import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

/**
 * User: Dmitry Levin
 * Date: 08.03.14
 */
public class CellUtils {

    public static final int UNDEFINED_HEIGHT = -1;

    private static final FontRenderContext fontRenderContext = new FontRenderContext(null, true, true);

    private static final FormulaEvaluator dummyEvaluator = new FormulaEvaluator() {
        public void clearAllCachedResultValues() {
        }

        public void notifySetFormula(Cell cell) {
        }

        public void notifyDeleteCell(Cell cell) {
        }

        public void notifyUpdateCell(Cell cell) {
        }

        public CellValue evaluate(Cell cell) {
            return null;
        }

        public Cell evaluateInCell(Cell cell) {
            return null;
        }

        public void setDebugEvaluationOutputForNextEval(boolean value) {
        }

        public void evaluateAll() {
        }

        public int evaluateFormulaCell(Cell cell) {
            return cell.getCachedFormulaResultType();
        }

    };

    /**
     * Excel measures columns in units of 1/256th of a character width
     * but the docs say nothing about what particular character is used.
     * '0' looks to be a good choice.
     */
    private static final char defaultChar = '0';


    /**
     * This is the multiple that the font height is scaled by when determining the
     * boundary of rotated text.
     */
    private static final double fontHeightMultiple = 2.0;

    /**
     * This is the multiple that used when cell style has indentation.
     */
    private static final int indentMultiplier = 2;

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
        return getValue(sheet, cellCoordinate, null);
    }

    public static Object getValue(Sheet sheet, CellCoordinate cellCoordinate, Class hintDestinationClass) {
        Cell cell = getCell(sheet, cellCoordinate, false);
        return cell == null ? null : determineTypeAndGetValue(cell, hintDestinationClass);
    }

    private static Object determineTypeAndGetValue(Cell cell, Class hintDestinationClass) {
        int cellType = cell.getCellType();
        if (Cell.CELL_TYPE_FORMULA == cellType) {
            cellType = cell.getCachedFormulaResultType();
        }
        return getValueAccordingToType(cell, cellType, hintDestinationClass);
    }

    private static Object getValueAccordingToType(Cell cell, int dataType, Class hintDestinationClass) {
        if (Cell.CELL_TYPE_ERROR == dataType || Cell.CELL_TYPE_BLANK == dataType) {
            return null;
        } else if (Cell.CELL_TYPE_BOOLEAN == dataType) {
            return cell.getBooleanCellValue();
        } else if (Cell.CELL_TYPE_NUMERIC == dataType) {
            if (DateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue();
            } else {
                if (String.class.equals(hintDestinationClass) && cell instanceof XSSFCell) {
                    return ((XSSFCell) cell).getRawValue();
                } else {
                    return cell.getNumericCellValue();
                }
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

    public static float getPreferredCellHeight(Sheet sheet, CellCoordinate cellCoordinate) {
        Cell cell = getCell(sheet, cellCoordinate, false);
        if (cell == null) return UNDEFINED_HEIGHT;
        Workbook wb = cell.getSheet().getWorkbook();
        CellStyle style = cell.getCellStyle();
        if (style == null) return UNDEFINED_HEIGHT;
        float colWidthPx = getColumnWidthInPx(sheet, cell.getColumnIndex());
        float defaultCharWidth = getDefaultCharWidth(cell.getSheet());
        float breakWidth = colWidthPx - style.getIndention() * defaultCharWidth * indentMultiplier;
        if (breakWidth <= 0) return UNDEFINED_HEIGHT;

        Font font = wb.getFontAt(style.getFontIndex());

        int cellType = cell.getCellType();
        // for formula cells we compute the cell width for the cached formula result
        if (cellType == Cell.CELL_TYPE_FORMULA) cellType = cell.getCachedFormulaResultType();

        String cellValue = null;
        if (cellType == Cell.CELL_TYPE_STRING) {
            cellValue = cell.getStringCellValue();
        } else {
            if (cellType == Cell.CELL_TYPE_NUMERIC) {
                // Try to get it formatted to look the same as excel
                try {
                    cellValue = new DataFormatter().formatCellValue(cell, dummyEvaluator);
                } catch (Exception e) {
                    cellValue = String.valueOf(cell.getNumericCellValue());
                }
            } else if (cellType == Cell.CELL_TYPE_BOOLEAN) {
                cellValue = String.valueOf(cell.getBooleanCellValue()).toUpperCase();
            }
        }

        return cellValue == null ? 0 : calculateHeight(font, cellValue, breakWidth, style.getRotation(), defaultCharWidth);
    }

    public static float getColumnWidthInPx(Sheet sheet, int columnIndex) {
        return sheet.getColumnWidth(columnIndex) * getDefaultCharWidth(sheet) / 256;
    }

    private static float calculateHeight(Font font, String cellValue, double breakWidth, short rotation, double defaultCharWidth) {
        AttributedString attrStr = new AttributedString(cellValue);
        copyAttributes(font, attrStr);

        AttributedCharacterIterator paragraph = attrStr.getIterator();
        int paragraphStart = paragraph.getBeginIndex();
        int paragraphEnd = paragraph.getEndIndex();

        LineBreakMeasurer lineMeasurer = new LineBreakMeasurer(paragraph, fontRenderContext);

        // Set position to the index of the first character in the paragraph.
        lineMeasurer.setPosition(paragraphStart);

        TextLayout layout;
        if (rotation == 0) {
            float runningHeight = 0;
            while (lineMeasurer.getPosition() < paragraphEnd) {
                layout = lineMeasurer.nextLayout((float) breakWidth);
                runningHeight += layout.getAscent() + layout.getDescent() + layout.getLeading();
            }
            return runningHeight;
        } else {
            double maxLineHeight = 0;
            while (lineMeasurer.getPosition() < paragraphEnd) {
                layout = lineMeasurer.nextLayout((float) breakWidth);
                AffineTransform trans = new AffineTransform();
                trans.concatenate(AffineTransform.getRotateInstance(rotation * 2.0 * Math.PI / 360.0));
                trans.concatenate(AffineTransform.getScaleInstance(1, fontHeightMultiple));
                maxLineHeight = Math.max(maxLineHeight, layout.getOutline(trans).getBounds().getHeight() + defaultCharWidth);
            }
            return (float) maxLineHeight;
        }
    }

    private static void copyAttributes(Font font, AttributedString str) {
        str.addAttribute(TextAttribute.FAMILY, font.getFontName());
        str.addAttribute(TextAttribute.SIZE, (float) font.getFontHeightInPoints());
        if (font.getBoldweight() == Font.BOLDWEIGHT_BOLD)
            str.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        if (font.getItalic()) str.addAttribute(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE);
        if (font.getUnderline() == Font.U_SINGLE) str.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
    }

    private static float getDefaultCharWidth(Sheet sheet) {
        Workbook wb = sheet.getWorkbook();
        Font defaultFont = wb.getFontAt((short) 0);

        AttributedString str = new AttributedString(String.valueOf(defaultChar));
        copyAttributes(defaultFont, str);
        TextLayout layout = new TextLayout(str.getIterator(), fontRenderContext);
        return layout.getAdvance();
    }
}
