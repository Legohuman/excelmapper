package com.github.excelmapper.samples.read;

import com.github.excelmapper.core.engine.*;
import com.github.excelmapper.core.engine.converters.Converter;
import com.github.excelmapper.core.engine.converters.DateStringConverter;
import com.github.excelmapper.samples.domain.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.github.excelmapper.core.engine.References.converter;
import static com.github.excelmapper.core.engine.References.property;

/**
 * User: Dmitry Levin
 * Date: 10.03.14
 */
public class ReadInvoice {
    public static void main(String[] args) throws IOException, InvalidFormatException {
        InputStream fileInput = null;
        try {
            fileInput = new BufferedInputStream(ReadInvoice.class.getClassLoader().getResourceAsStream(
                "com/github/excelmapper/samples/invoice.xls"));
            Workbook wb = WorkbookFactory.create(fileInput);

            Sheet sheet = wb.getSheetAt(0);
            CellGroup invoiceGroup = new CellGroup()
                .setCursorMovementDirection(MovementDirection.DOWN)
                .addCell(new CellDefinition(property("number")))
                .addCell(new CellDefinition(converter(property("date"), new DateStringConverter("dd.MM.yy"))));

            CellGroup invoiceItemGroup = new CellGroup()
                .addCell(new CellDefinition(property("product.art")))
                .addCell(new CellDefinition(converter(property("product.color"), new ProductColorConverter())))
                .addCell(new CellDefinition(converter(property("product.material"), new ProductMaterialConverter())))
                .addCell(new CellDefinition(property("price")))
                .addCell(new CellDefinition(property("count")));

            CompositeRectangle containerGroup = new CompositeRectangle();
            ItemContainerFactory factory = new ItemContainerFactory();
            ProcessMessagesHolder messagesHolder = new SimpleProcessMessagesHolder();

            ItemContainer invoiceContainer = factory.createItemContainer(sheet, new CellCoordinate(1, 1));
            containerGroup.add(invoiceContainer);

            Invoice invoice = invoiceContainer.readItem(Invoice.class, invoiceGroup, messagesHolder);
            System.out.println("Data:");
            System.out.println(invoice);
            invoiceContainer.setCurrentCoordinate(new CellCoordinate(0, 5));
            InvoiceItemFactory invoiceItemFactory = new InvoiceItemFactory();
            while (invoiceContainer.getCurrentCoordinate().getRow() < sheet.getLastRowNum()) {
                if (invoiceContainer.containsAllCellGroupCells(invoiceItemGroup)) {
                    InvoiceItem invoiceItem = invoiceItemFactory.createEmpty();
                    invoiceContainer.readItem(invoiceItem, invoiceItemGroup, messagesHolder);
                    System.out.println(invoiceItem);
                } else {
                    invoiceContainer.nextRow();
                }
            }

            System.out.println("\nErrors:");
            for (int i = 0; i < messagesHolder.count(); i++) {
                ProcessMessage message = messagesHolder.get(i);
                System.out.println(message);
            }
        } finally {
            if (fileInput != null) {
                fileInput.close();
            }
        }
    }

    private static class InvoiceItemFactory {

        public InvoiceItem createEmpty() {
            InvoiceItem invoiceItem = new InvoiceItem();
            invoiceItem.setProduct(new Product());
            return invoiceItem;
        }
    }

    private static class ProductColorConverter implements Converter<ProductColor, String> {
        @Override
        public String convertTo(ProductColor sourceValue) {
            return sourceValue == null ? null : sourceValue.toString().toLowerCase();
        }

        @Override
        public ProductColor convertFrom(String destinationValue) {
            return (destinationValue == null || destinationValue.isEmpty()) ? null : ProductColor.valueOf(destinationValue.toUpperCase());
        }

        @Override
        public Class<ProductColor> getSourceType() {
            return ProductColor.class;
        }

        @Override
        public Class<String> getDestinationType() {
            return String.class;
        }
    }

    private static class ProductMaterialConverter implements Converter<ProductMaterial, String> {
        @Override
        public String convertTo(ProductMaterial sourceValue) {
            return sourceValue == null ? null : sourceValue.toString().toLowerCase();
        }

        @Override
        public ProductMaterial convertFrom(String destinationValue) {
            return (destinationValue == null || destinationValue.isEmpty()) ? null :
                ProductMaterial.valueOf(destinationValue.toUpperCase());
        }

        @Override
        public Class<ProductMaterial> getSourceType() {
            return ProductMaterial.class;
        }

        @Override
        public Class<String> getDestinationType() {
            return String.class;
        }
    }
}
