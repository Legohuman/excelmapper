package ru.dlevin.excelmapper.samples;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import ru.dlevin.excelmapper.engine.*;
import ru.dlevin.excelmapper.samples.domain.Person;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static ru.dlevin.excelmapper.engine.References.property;
import static ru.dlevin.excelmapper.engine.References.value;

/**
 * User: Dmitry Levin
 * Date: 07.03.14
 */
public class SimpleTable {

    public static void main(String[] args) throws IOException {
        FileOutputStream fileOut = null;
        try {
            Workbook wb = new HSSFWorkbook();
            fileOut = new FileOutputStream("workbook.xls");

            Sheet sheet = wb.createSheet();
            CellStyle style = wb.createCellStyle();
            Font font = wb.createFont();
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            style.setFont(font);
            style.setAlignment(CellStyle.ALIGN_CENTER);
            CellGroup header = new CellGroup();
            header.setCellStyleReference(new StaticCellStyleReference(style));
            header.addCells(CellDefinitions.fromReferences(value("Name"), value("Post")));
            header.addCell(new CellCoordinate(4, 0), value("Age"));

            CellGroup group = new CellGroup();
            group.addCells(CellDefinitions.fromReferences(property("name"), property("post")));
            group.addCell(new CellDefinition(value("---"), null, 2, 1));
            group.addCell(property("age"));
            ItemContainer container = new ItemContainer(null, sheet, new CellCoordinate(2, 2));
            container.addItem(null, header);
            List<Person> items =
                Arrays.asList(new Person("John", "director", 40), new Person("Mike", "secretary", 35), new Person("Adam", "engineer", 30));
            container.addItems(items, group);

            wb.write(fileOut);
        } finally {
            if (fileOut != null) {
                fileOut.close();
            }
        }
    }
}
