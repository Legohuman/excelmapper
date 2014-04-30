package ru.dlevin.excelmapper.samples;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import ru.dlevin.excelmapper.engine.*;
import ru.dlevin.excelmapper.samples.domain.Importance;
import ru.dlevin.excelmapper.samples.domain.Issue;
import ru.dlevin.excelmapper.samples.domain.IssueType;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import static ru.dlevin.excelmapper.engine.References.property;

/**
 * User: Dmitry Levin
 * Date: 10.03.14
 */
public class ColumnsTable {
    public static void main(String[] args) throws IOException {
        FileOutputStream fileOut = null;
        try {
            Workbook wb = new HSSFWorkbook();
            fileOut = new FileOutputStream("workbook.xls");


            ImportanceStyleReference importanceStyleReference = new ImportanceStyleReference();

            Font font = wb.createFont();
            font.setFontName("Arial");

            importanceStyleReference.registerCellStyle(Importance.MINOR, createCellStyle(wb, font, IndexedColors.GREEN.getIndex()));
            importanceStyleReference.registerCellStyle(Importance.MAJOR, createCellStyle(wb, font, IndexedColors.YELLOW.getIndex()));
            importanceStyleReference.registerCellStyle(Importance.CRITICAL, createCellStyle(wb, font, IndexedColors.ORANGE.getIndex()));
            importanceStyleReference.registerCellStyle(Importance.BLOCKER, createCellStyle(wb, font, IndexedColors.RED.getIndex()));

            Sheet sheet = wb.createSheet();
            CellGroup group = new CellGroup();
            group.addCell(new CellDefinition(new IssueNumberAndTypeValueReference(), importanceStyleReference, 2, 1));
            group.addCell(new CellCoordinate(1, 1), property("asignee"));
            group.addCell(new CellCoordinate(0, 2), new CellDefinition(property("title"), 2, 1));
            group.addCell(new CellCoordinate(0, 3), new CellDefinition(property("description"), 2, 1));

            CompositeRectangle containerGroup = new CompositeRectangle();
            ItemContainerFactory factory = new ItemContainerFactory();

            for (int i = 0; i < 3; i++) {
                ItemContainer container = factory.createItemContainer(sheet,
                    containerGroup.isEmpty() ? CellCoordinate.ZERO : containerGroup.getBottomLeftCorner().plusRow(2));
                containerGroup.add(container);
                container.addItems(
                    Arrays.asList(new Issue(1, IssueType.BUG, Importance.MINOR, "Issue 1", "Small ui bug", "John"),
                        new Issue(2, IssueType.BUG, Importance.MAJOR, "Issue 2", "Small ui bug2", "John"),
                        new Issue(3, IssueType.BUG, Importance.CRITICAL, "Issue 3", "Small ui bug3", "John")), group);
                container.setCursorCoordinate(container.getTopLeftCorner().plusColumn(3));
                container.addItems(Arrays.asList(new Issue(4, IssueType.BUG, Importance.BLOCKER, "Issue 4", "Small ui bug4", "John"),
                    new Issue(5, IssueType.BUG, Importance.MINOR, "Issue 5", "Small ui bug5", "John")), group);
            }

            wb.write(fileOut);
        } finally {
            if (fileOut != null) {
                fileOut.close();
            }
        }
    }

    private static CellStyle createCellStyle(Workbook wb, Font font, short colorIndex) {
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(colorIndex);
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        return cellStyle;
    }

    private static class IssueNumberAndTypeValueReference extends AbstractContextAwareReadableValueReference<Issue, String> {
        @Override
        public String getValue() {
            Issue issue = getContext();
            return issue.getNumber() + " " + issue.getType();
        }
    }

    private static class ImportanceStyleReference extends ContextAwareCellStyleRefence<Importance, Issue> {
        @Override
        public Importance getCellStyleCode() {
            return getContext().getImportance();
        }
    }
}
