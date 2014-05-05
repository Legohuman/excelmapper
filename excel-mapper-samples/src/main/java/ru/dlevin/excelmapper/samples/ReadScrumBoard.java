package ru.dlevin.excelmapper.samples;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import ru.dlevin.excelmapper.engine.*;
import ru.dlevin.excelmapper.samples.domain.Issue;
import ru.dlevin.excelmapper.samples.domain.IssueType;
import ru.dlevin.excelmapper.samples.domain.UserStory;

import java.io.File;
import java.io.IOException;

import static ru.dlevin.excelmapper.engine.References.property;

/**
 * User: Dmitry Levin
 * Date: 10.03.14
 */
public class ReadScrumBoard {
    public static void main(String[] args) throws IOException, InvalidFormatException {
        File fileInput = new File("scrum-board.xls");
        Workbook wb = WorkbookFactory.create(fileInput);

        Sheet sheet = wb.createSheet();
        CellGroup userStoryGroup = new CellGroup()
            .addCell(new CellDefinition(property("name"), 6, 1));


        CellGroup ticketGroup = new CellGroup()
            .setCursorMovementDirection(MovementDirection.DOWN)
            .addCell(new CellDefinition(new IssueNumberAndTypeValueReference(), 2, 1))
            .setCursorMovementDirection(MovementDirection.NONE)
            .nextColumn()
            .addCell(property("asignee")).nextRow().resetColumn()
            .setCursorMovementDirection(MovementDirection.DOWN)
            .addCell(new CellDefinition(property("title"), 2, 1))
            .addCell(new CellDefinition(property("description"), 2, 1));

        CompositeRectangle containerGroup = new CompositeRectangle();
        ItemContainerFactory factory = new ItemContainerFactory();

        for (int i = 0; i < 3; i++) {
            ItemContainer userStoryContainer = factory.createItemContainer(sheet, CellCoordinate.ZERO);
            containerGroup.add(userStoryContainer);

            UserStory userStory = new UserStory();
            System.out.println(userStory);
            userStoryContainer.readItem(userStory, userStoryGroup, ProcessMessagesHolder.NOP);
            ItemContainer ticketsContainer = factory.createItemContainer(sheet,
                containerGroup.getBottomLeftCorner().plusRow(1));
            containerGroup.add(ticketsContainer);

            Issue issue = new Issue();
            ticketsContainer.readItem(issue, ticketGroup, ProcessMessagesHolder.NOP);
            System.out.println(issue);
            ticketsContainer.readItem(issue, ticketGroup, ProcessMessagesHolder.NOP);
            ticketsContainer.readItem(issue, ticketGroup, ProcessMessagesHolder.NOP);
            ticketsContainer.readItem(issue, ticketGroup, ProcessMessagesHolder.NOP);
            ticketsContainer.setCurrentCoordinate(ticketsContainer.getTopRightCorner().nextColumn());
        }
    }


    private static class IssueNumberAndTypeValueReference extends ContextAwareValueReference<Issue, String> {

        @Override
        public void setValue(String value) {
            String[] parts = value.split("\\s+");
            int number = new Integer(parts[0]);
            IssueType type = IssueType.valueOf(parts[1]);
            Issue issue = getContext();
            issue.setNumber(number);
            issue.setType(type);
        }
    }
}
