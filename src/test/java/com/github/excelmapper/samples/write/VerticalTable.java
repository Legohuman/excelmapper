package com.github.excelmapper.samples.write;

import com.github.excelmapper.core.engine.*;
import com.github.excelmapper.samples.domain.Person;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.github.excelmapper.core.engine.CellDefinitions.fromReferences;
import static com.github.excelmapper.core.engine.References.property;
import static com.github.excelmapper.core.engine.References.value;

/**
 * User: Dmitry Levin
 * Date: 07.03.14
 */
public class VerticalTable {

    public static void main(String[] args) throws IOException {
        FileOutputStream fileOut = null;
        try {
            //Open output file and create sheet
            Workbook wb = new HSSFWorkbook();
            fileOut = new FileOutputStream("workbook.xls");
            Sheet sheet = wb.createSheet();

            //Prepare cell styles
            CellStyle style = wb.createCellStyle();
            Font font = wb.createFont();
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            style.setFont(font);
            style.setAlignment(CellStyle.ALIGN_CENTER);

            //Define header cell group
            CellGroup header = new CellGroup();
            header.setCellStyleReference(new StaticCellStyleReference(style));
            header.addCells(fromReferences(value("Name"), value("Post"), value("Age")));

            //Define row cell group
            CellGroup group = new CellGroup();
            group.addCells(fromReferences(property("name"), property("post"), property("age")));

            //Create item container
            ItemContainerFactory factory = new ItemContainerFactory();
            ItemContainer container = factory.createItemContainer(sheet, CellCoordinate.ZERO);

            //Add header and table items
            container.addItem(null, header);
            List<Person> items = Arrays.asList(
                new Person("John", "director", 40),
                new Person("Mike", "secretary", 35),
                new Person("Adam", "engineer", 30)
            );
            container.addItems(items, group);

            //Write file
            wb.write(fileOut);
        } finally {
            if (fileOut != null) {
                fileOut.close();
            }
        }
    }
}
