package ru.dlevin.excelmapper.samples.write;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import ru.dlevin.excelmapper.engine.*;
import ru.dlevin.excelmapper.engine.converters.DoubleIntConverter;
import ru.dlevin.excelmapper.engine.converters.ReverseConverter;
import ru.dlevin.excelmapper.samples.domain.RouteRequest;
import ru.dlevin.excelmapper.samples.domain.RouteRequestInfo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import static ru.dlevin.excelmapper.engine.References.*;

/**
 * User: Dmitry Levin
 * Date: 07.03.14
 */
public class DynamicRowsTable {

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
            header.addCells(CellDefinitions.fromReferences(value("Train number"), value("From"), value("To"), value("Notes")));

            CellGroup singleTrainGroup = new CellGroup();

            ValueReference ref = converter((ValueReference)References.property("infos[0].trainNumber"),
                new ReverseConverter<Integer, Double>(new DoubleIntConverter()));
            singleTrainGroup.addCells(CellDefinitions
                .fromReferences(ref, property("infos[0].fromStation"), property("infos[0].toStation"),
                    property("note"), property("date")));

            CellGroup twoTrainsGroup = new CellGroup();
            twoTrainsGroup.addCells(CellDefinitions
                .fromReferences(property("infos[0].trainNumber"), property("infos[0].fromStation"), property("infos[0].toStation")));
            twoTrainsGroup.addCell(new CellDefinition(property("note"), 1, 2));
            twoTrainsGroup.addCells(new CellCoordinate(0, 1), CellDefinitions
                .fromReferences(property("infos[1].trainNumber"), property("infos[1].fromStation"), property("infos[1].toStation")));
            twoTrainsGroup.addCell(new CellCoordinate(4, 0), new CellDefinition(References.property("date"), 1, 2));

            ItemContainerFactory containerFactory = new ItemContainerFactory();
            ItemContainer container = containerFactory.createItemContainer(sheet, new CellCoordinate(2, 2));
            container.addItem(null, header);

            List<RouteRequest> requests = getRouteRequests();
            for (RouteRequest request : requests) {
                List<RouteRequestInfo> infos = request.getInfos();
                if (infos != null && !infos.isEmpty() && infos.size() <= 2) {
                    if (infos.size() == 2) {
                        container.addItem(request, twoTrainsGroup);
                    } else {
                        container.addItem(request, singleTrainGroup);
                    }
                }
            }
            wb.write(fileOut);

            Workbook wb2 = new HSSFWorkbook(new FileInputStream("workbook.xls"));
            Sheet sheet2 = wb2.getSheetAt(0);
            ItemContainer container2 = containerFactory.createItemContainer(sheet2, new CellCoordinate(2, 3));

            List<RouteRequestInfo> existingTrainInfo = Collections.singletonList(new RouteRequestInfo(0, null, null));
            RouteRequest existingTrain = new RouteRequest(existingTrainInfo, null, new GregorianCalendar(2012, 1, 1, 1, 11, 11).getTime());
            container2.readItem(existingTrain, singleTrainGroup, ProcessMessagesHolder.NOP);
            System.out.println(existingTrain);
        } finally {
            if (fileOut != null) {
                fileOut.close();
            }
        }
    }

    private static List<RouteRequest> getRouteRequests() {
        List<RouteRequestInfo> singleTrainInfo = Collections.singletonList(new RouteRequestInfo(111, "Saint Petersburg", "Moscow"));
        RouteRequest singleTrain = new RouteRequest(singleTrainInfo, "Increase speed", new Date());

        List<RouteRequestInfo> twoTrainInfos = new ArrayList<RouteRequestInfo>(2);
        twoTrainInfos.add(new RouteRequestInfo(222, "Saint Petersburg", "Helsinki"));
        twoTrainInfos.add(new RouteRequestInfo(333, "Helsinki", "Saint Petersburg"));
        RouteRequest twoTrains = new RouteRequest(twoTrainInfos, "Decrease speed", new Date());

        return Arrays.asList(singleTrain, twoTrains);
    }
}
