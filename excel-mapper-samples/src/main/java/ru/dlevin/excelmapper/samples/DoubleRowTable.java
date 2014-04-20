package ru.dlevin.excelmapper.samples;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import ru.dlevin.excelmapper.engine.CellCoordinate;
import ru.dlevin.excelmapper.engine.CellDefinitions;
import ru.dlevin.excelmapper.engine.CellGroup;
import ru.dlevin.excelmapper.engine.ItemContainer;
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
public class DoubleRowTable {

    public static void main(String[] args) throws IOException {
        FileOutputStream fileOut = null;
        try {
            Workbook wb = new HSSFWorkbook();
            fileOut = new FileOutputStream("workbook.xls");

            Sheet sheet = wb.createSheet();
            CellGroup group = new CellGroup();
            group.addCells(CellDefinitions.fromReferences(property("name"), property("post")));
            group.addCells(new CellCoordinate(0, 1), CellDefinitions.fromReferences(property("age"), value("---")));
            ItemContainer container = new ItemContainer(null, sheet, new CellCoordinate(2, 2));
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
