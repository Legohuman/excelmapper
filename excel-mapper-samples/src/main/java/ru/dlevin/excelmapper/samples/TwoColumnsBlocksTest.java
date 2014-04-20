package ru.dlevin.excelmapper.samples;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import ru.dlevin.excelmapper.engine.CellCoordinate;
import ru.dlevin.excelmapper.engine.CellGroup;
import ru.dlevin.excelmapper.engine.ItemContainer;
import ru.dlevin.excelmapper.engine.References;
import ru.dlevin.excelmapper.samples.domain.Ticket;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * User: Dmitry Levin
 * Date: 10.03.14
 */
public class TwoColumnsBlocksTest {
    public static void main(String[] args) throws IOException {
        FileOutputStream fileOut = null;
        try {
            Workbook wb = new HSSFWorkbook();
            fileOut = new FileOutputStream("workbook.xls");

            Sheet sheet = wb.createSheet();
            CellGroup<Ticket> group = new CellGroup<Ticket>();
            group.addCell(new CellCoordinate(0, 0), References.property("number"));
            group.addCell(new CellCoordinate(0, 1), References.property("title"));

            ItemContainer previousContainer = null;

            for (int i = 0; i < 3; i++) {
                ItemContainer container = new ItemContainer(null, sheet,
                    previousContainer == null ? CellCoordinate.ZERO : previousContainer.getBottomLeftCorner().plusRow(2));

                container.addItems(
                    Arrays.asList(new Ticket(1, "Ticket 1"), new Ticket(2, "Ticket 2"), new Ticket(3, "Ticket 3")), group);
                container.setCursorCoordinate(container.getTopLeftCorner().plusColumn(1));
                container.addItems(Arrays.asList(new Ticket(4, "Ticket 4"), new Ticket(5, "Ticket 5")), group);
                previousContainer = container;
            }

            wb.write(fileOut);
        } finally {
            if (fileOut != null) {
                fileOut.close();
            }
        }
    }
}
