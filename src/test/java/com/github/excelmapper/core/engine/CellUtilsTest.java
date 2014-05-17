package com.github.excelmapper.core.engine;

import com.github.excelmapper.core.utils.CellUtils;
import org.apache.poi.common.usermodel.Hyperlink;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

/**
 * User: Dmitry Levin
 * Date: 16.03.14
 */
public class CellUtilsTest {

    @Test
    public void getValueWithDifferentTypesTest() throws IOException {

        HSSFWorkbook wb = new HSSFWorkbook();
        CreationHelper createHelper = wb.getCreationHelper();
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setDataFormat(
            createHelper.createDataFormat().getFormat("m/d/yy h:mm"));
        HSSFSheet sheet = wb.createSheet();
        String str = "String value";
        sheet.createRow(0).createCell(0).setCellValue(str);
        double number = 1.23;
        sheet.createRow(1).createCell(0).setCellValue(number);
        Date date = new Date();
        HSSFCell dateCell = sheet.createRow(2).createCell(0);
        dateCell.setCellValue(date);
        dateCell.setCellStyle(cellStyle);
        GregorianCalendar calendar = new GregorianCalendar();
        HSSFCell calendarCell = sheet.createRow(3).createCell(0);
        calendarCell.setCellStyle(cellStyle);
        calendarCell.setCellValue(calendar);
        String richText = "Rich text";
        sheet.createRow(4).createCell(0).setCellValue(new HSSFRichTextString(richText));
        sheet.createRow(5).createCell(0).setCellValue(true);
        HSSFHyperlink hyperlink = new HSSFHyperlink(Hyperlink.LINK_URL);
        hyperlink.setAddress("http://www.ya.ru");
        sheet.createRow(6).createCell(0).setHyperlink(hyperlink);


        assertEquals(str, CellUtils.getValue(sheet, new CellCoordinate(0, 0)));
        assertEquals(number, (Double)CellUtils.getValue(sheet, new CellCoordinate(0, 1)), 1e-6);
        assertEquals(date, CellUtils.getValue(sheet, new CellCoordinate(0, 2)));
        assertEquals(calendar.getTime(), CellUtils.getValue(sheet, new CellCoordinate(0, 3)));
        assertEquals(richText, CellUtils.getValue(sheet, new CellCoordinate(0, 4)));
        assertEquals(true, CellUtils.getValue(sheet, new CellCoordinate(0, 5)));
    }
}
