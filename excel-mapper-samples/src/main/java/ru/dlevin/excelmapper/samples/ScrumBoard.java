package ru.dlevin.excelmapper.samples;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import ru.dlevin.excelmapper.engine.*;
import ru.dlevin.excelmapper.samples.domain.Importance;
import ru.dlevin.excelmapper.samples.domain.Issue;
import ru.dlevin.excelmapper.samples.domain.IssueType;
import ru.dlevin.excelmapper.samples.domain.UserStory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import static ru.dlevin.excelmapper.engine.References.property;
import static ru.dlevin.excelmapper.engine.References.value;

/**
 * User: Dmitry Levin
 * Date: 10.03.14
 */
public class ScrumBoard {
    public static void main(String[] args) throws IOException {
        FileOutputStream fileOut = null;
        try {
            Workbook wb = new HSSFWorkbook();
            fileOut = new FileOutputStream("scrum-board.xls");

            Font userStoryTitleFont = createUserStoryTitleFont(wb);
            Font ticketCategoryTitleFont = createTicketCategoryTitleFont(wb);
            Font ticketTitleFont = createTicketTitleFont(wb);

            ImportanceStyleReference ticketHeaderStyleRefernce = new ImportanceStyleReference();
            ticketHeaderStyleRefernce
                .setUndefinedCellStyle(createTicketTitleStyle(wb, ticketTitleFont, IndexedColors.GREY_25_PERCENT.getIndex()));
            ticketHeaderStyleRefernce
                .registerCellStyle(Importance.MINOR, createTicketTitleStyle(wb, ticketTitleFont, IndexedColors.GREEN.getIndex()));
            ticketHeaderStyleRefernce
                .registerCellStyle(Importance.MAJOR, createTicketTitleStyle(wb, ticketTitleFont, IndexedColors.YELLOW.getIndex()));
            ticketHeaderStyleRefernce.registerCellStyle(Importance.CRITICAL, createTicketTitleStyle(wb, ticketTitleFont,
                IndexedColors.ORANGE.getIndex()));
            ticketHeaderStyleRefernce
                .registerCellStyle(Importance.BLOCKER, createTicketTitleStyle(wb, ticketTitleFont, IndexedColors.RED.getIndex()));

            Sheet sheet = wb.createSheet();
            StaticCellStyleReference userStoryStyleRef = new StaticCellStyleReference(createTitleCellStyleByFont(wb, userStoryTitleFont));
            StaticCellStyleReference ticketCategoryStyleRef =
                new StaticCellStyleReference(createTitleCellStyleByFont(wb, ticketCategoryTitleFont));
            CellGroup userStoryGroup = new CellGroup()
                .addCell(new CellDefinition(property("name"), userStoryStyleRef, 6, 1))
                .nextRow().resetColumn()
                .addCell(new CellDefinition(value("Todo"), ticketCategoryStyleRef, 2, 1))
                .addCell(new CellDefinition(value("In progress"), ticketCategoryStyleRef, 2, 1))
                .addCell(new CellDefinition(value("Done"), ticketCategoryStyleRef, 2, 1));


            StaticCellStyleReference longTextStyleRef = new StaticCellStyleReference(createLongTextStyle(wb));
            CellGroup ticketGroup = new CellGroup()
                .setCursorMovementDirection(MovementDirection.DOWN)
                .addCell(new CellDefinition(new IssueNumberAndTypeValueReference(), ticketHeaderStyleRefernce, 2, 1))
                .setCursorMovementDirection(MovementDirection.NONE)
                .addCell(value("Assignee:")).nextColumn()
                .addCell(property("asignee")).nextRow().resetColumn()
                .setCursorMovementDirection(MovementDirection.DOWN)
                .addCell(new CellDefinition(property("title"), longTextStyleRef, 2, 1))
                .addCell(new CellDefinition(property("description"), longTextStyleRef, 2, 1));

            CompositeRectangle containerGroup = new CompositeRectangle();
            ItemContainerFactory factory = new ItemContainerFactory();

            for (int i = 0; i < 3; i++) {
                ItemContainer userStoryContainer = factory.createItemContainer(sheet,
                    containerGroup.isEmpty() ? CellCoordinate.ZERO : containerGroup.getBottomLeftCorner().plusRow(2));
                containerGroup.add(userStoryContainer);

                userStoryContainer.addItem(new UserStory("User story " + (i + 1)), userStoryGroup);
                ItemContainer ticketsContainer = factory.createItemContainer(sheet,
                    containerGroup.getBottomLeftCorner().plusRow(1));
                containerGroup.add(ticketsContainer);

                ticketsContainer.addItems(
                    Arrays.asList(new Issue(1, IssueType.BUG, Importance.MINOR, "Issue 1", "Small ui bug", "John"),
                        new Issue(2, IssueType.BUG, Importance.MAJOR, "Issue 2", "Small ui bug2", "John"),
                        new Issue(3, IssueType.BUG, Importance.CRITICAL, "Issue 3", "Small ui bug3", "John")), ticketGroup);
                ticketsContainer.setCurrentCoordinate(ticketsContainer.getTopRightCorner().nextColumn());
                ticketsContainer
                    .addItems(Arrays.asList(new Issue(4, IssueType.BUG, Importance.BLOCKER, "Issue 4", "Small ui bug4", "John"),
                        new Issue(5, IssueType.BUG, null, "Issue 5", "Small ui bug5", "John")), ticketGroup);

                ticketsContainer.setCurrentCoordinate(ticketsContainer.getTopRightCorner().nextColumn());
                ticketsContainer
                    .addItems(Arrays.asList(new Issue(6, IssueType.BUG, Importance.CRITICAL, "Issue 6", "Critical bug", "John")),
                        ticketGroup);
            }

            wb.write(fileOut);
        } finally {
            if (fileOut != null) {
                fileOut.close();
            }
        }
    }

    private static Font createBaseFont(Workbook wb) {
        Font font = wb.createFont();
        font.setFontName("Arial");
        return font;
    }

    private static Font createUserStoryTitleFont(Workbook wb) {
        Font font = createBaseFont(wb);
        font.setFontHeightInPoints((short)16);
        return font;
    }

    private static Font createTicketCategoryTitleFont(Workbook wb) {
        Font font = createBaseFont(wb);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        return font;
    }

    private static Font createTicketTitleFont(Workbook wb) {
        Font font = createBaseFont(wb);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints((short)14);
        return font;
    }

    private static CellStyle createTitleCellStyleByFont(Workbook wb, Font font) {
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        return cellStyle;
    }

    private static CellStyle createTicketTitleStyle(Workbook wb, Font font, short colorIndex) {
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(colorIndex);
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        return cellStyle;
    }

    private static CellStyle createLongTextStyle(Workbook wb) {
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setWrapText(true);
        return cellStyle;
    }

    private static class IssueNumberAndTypeValueReference extends ContextAwareValueReference<Issue, String> {
        @Override
        public String getValue() {
            Issue issue = getContext();
            return issue.getNumber() + " " + issue.getType();
        }

        @Override
        public Class<String> getType() {
            return String.class;
        }
    }

    private static class ImportanceStyleReference extends ContextAwareCellStyleRefence<Importance, Issue> {
        @Override
        public Importance getCellStyleCode() {
            return getContext().getImportance();
        }
    }
}
