[Apache POI project](https://poi.apache.org/) was always considered as a good way for dealing with MS Excel files. It`s flexible and provide a lot of options to describe inner structure of a file. However very often developer needs just to export POJOs to Excel or import them from it. This project is an approach to achieve greater simplicity for exporting and importing processes.

The central conception of this framework is a **cell group**. Unlike other approaches which tend to describe data like a table of rows we use a cell group like template of several cells. In exporting process single cell group can be printed wherever you like at a sheet for each individual exported item. Each cell in a cell group is bound to a value with a **value reference**. The value reference can be either static which is suitable for printing static text like headers or dynamic which is good for getting an exported item values. To avoid completely manual positioning of cell groups we use **item containers**. The item container is a flexible container that prints cell group at some cell coordinate. Cell coordinate is defined by **cursor** position and its movement direction. In a basic scenario which includes exporting table header and some items you just need to define one cell group for table header and other for table row. Then you need to create item container with a specified initial cell coordinate and add your items bound to row cell group.

This is an example how it can be done.

<pre>
import static com.github.excelmapper.core.engine.CellDefinitions.fromReferences;
import static com.github.excelmapper.core.engine.References.property;
import static com.github.excelmapper.core.engine.References.value;

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
</pre>