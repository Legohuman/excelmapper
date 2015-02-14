package com.github.excelmapper.samples.resize;

import com.github.excelmapper.core.engine.CellCoordinate;
import com.github.excelmapper.core.utils.CellUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;

public class ResizeRowHeight {
    public static void main(String[] args) throws IOException, InvalidFormatException {
        InputStream fis = null;
        OutputStream fos = null;
        try {
            fis = new BufferedInputStream(ResizeRowHeight.class.getClassLoader().getResourceAsStream(
                    "com/github/excelmapper/samples/resize-row-height-sample.xls"));
            File outFile = File.createTempFile("resize-row-height-sample", ".xls");
            fos = new BufferedOutputStream(new FileOutputStream(outFile));
            Workbook wb = WorkbookFactory.create(fis);

            Sheet sheet = wb.getSheetAt(0);
            for (int col = 0; col <= 1; col++) {
                System.out.println("Column [" + col + "] width: " + CellUtils.getColumnWidthInPx(sheet, col));
            }


            setPrefHeight(sheet, new CellCoordinate(0, 0));
            setPrefHeight(sheet, new CellCoordinate(1, 0));
            setPrefHeight(sheet, new CellCoordinate(0, 1));

            setPrefHeight(sheet, new CellCoordinate(0, 2));
            setPrefHeight(sheet, new CellCoordinate(1, 2));
            setPrefHeight(sheet, new CellCoordinate(0, 3));

            setPrefHeight(sheet, new CellCoordinate(0, 4));
            setPrefHeight(sheet, new CellCoordinate(0, 5));
            setPrefHeight(sheet, new CellCoordinate(0, 6));

            wb.write(fos);
            System.out.println(outFile.getAbsolutePath());
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }

    private static void setPrefHeight(Sheet sheet, CellCoordinate coord) {
        float preferredCellHeight = CellUtils.getPreferredCellHeight(sheet, coord);
        System.out.println("Cell [" + coord.getColumn() + ", " + coord.getRow() + "] pref height: " + preferredCellHeight);
        sheet.getRow(coord.getRow()).setHeightInPoints(preferredCellHeight);
    }
}