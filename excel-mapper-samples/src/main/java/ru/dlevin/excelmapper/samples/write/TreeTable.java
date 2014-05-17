package ru.dlevin.excelmapper.samples.write;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import ru.dlevin.excelmapper.engine.*;
import ru.dlevin.excelmapper.samples.domain.Department;
import ru.dlevin.excelmapper.samples.domain.Person;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static ru.dlevin.excelmapper.engine.References.property;

/**
 * User: Dmitry Levin
 * Date: 07.03.14
 */
public class TreeTable {

    public static void main(String[] args) throws IOException {
        FileOutputStream fileOut = null;
        try {
            Workbook wb = new HSSFWorkbook();
            fileOut = new FileOutputStream("workbook.xls");

            Sheet sheet = wb.createSheet();
            CellGroup personGroup = new CellGroup();
            personGroup.addCells(CellDefinitions.fromReferences(property("name"), property("age")));

            CellGroup departmentGroup = new CellGroup();
            departmentGroup.addCell(new CellCoordinate(0, 0), new CellDefinition(new BeanPropertyValueReference("name")));


            Person p1 = new Person("John", "director", 40);
            Person p2 = new Person("Mike", "secretary", 35);
            Person p3 = new Person("Adam", "engineer", 30);
            Department d1 = new Department("IT Department", Arrays.asList(p1, p2, p3));

            Person p4 = new Person("Jane", "manager", 23);
            Person p5 = new Person("Helen", "manager", 22);
            Department d2 = new Department("HR Department", Arrays.asList(p4, p5));

            List<Department> departments = Arrays.asList(d1, d2);
            CellCoordinate containerOrigin = new CellCoordinate(0, 0);
            CompositeRectangle group = new CompositeRectangle();
            ItemContainerFactory factory = new ItemContainerFactory();

            for (Department department : departments) {
                ItemContainer departmentContainer =
                    factory.createItemContainer(sheet, group.isEmpty() ? containerOrigin : group.getBottomLeftCorner().plusRow(1));
                group.add(departmentContainer);
                departmentContainer.addItem(department, departmentGroup);
                departmentContainer.setCurrentCoordinate(departmentContainer.getCurrentCoordinate().plusColumn(1));
                departmentContainer.addItems(department.getPersons(), personGroup);
            }


            wb.write(fileOut);
        } finally {
            if (fileOut != null) {
                fileOut.close();
            }
        }
    }
}
