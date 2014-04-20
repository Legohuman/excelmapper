package ru.dlevin.excelmapper.engine;

import org.apache.poi.ss.usermodel.Sheet;
import org.joda.time.DateTime;
import org.joda.time.Instant;
import ru.dlevin.excelmapper.engine.converters.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * User: Dmitry Levin
 * Date: 23.03.14
 */
public class ItemContainerFactory {

    private final ConversionEngine conversionEngine;

    public ItemContainerFactory() {
        this.conversionEngine = createDefaultFormatterEngine();
    }

    private ConversionEngine createDefaultFormatterEngine() {
        SimpleConversionEngine simpleConversionEngine = new SimpleConversionEngine();
        simpleConversionEngine.registerConverter(Boolean.class, String.class, new BooleanStringConverter());
        simpleConversionEngine.registerConverter(Double.class, Byte.class, new DoubleByteConverter());
        simpleConversionEngine.registerConverter(Double.class, Character.class, new DoubleCharConverter());
        simpleConversionEngine.registerConverter(Double.class, Short.class, new DoubleShortConverter());
        simpleConversionEngine.registerConverter(Double.class, Integer.class, new DoubleIntConverter());
        simpleConversionEngine.registerConverter(Double.class, Long.class, new DoubleLongConverter());
        simpleConversionEngine.registerConverter(Double.class, BigDecimal.class, new DoubleBigDecimalConverter());
        simpleConversionEngine.registerConverter(Double.class, String.class, new DoubleStringConverter());
        simpleConversionEngine.registerConverter(Date.class, Calendar.class, new DateCalendarConverter());
        simpleConversionEngine.registerConverter(Date.class, Instant.class, new DateInstantConverter());
        simpleConversionEngine.registerConverter(Date.class, DateTime.class, new DateDateTimeConverter());
        simpleConversionEngine.registerConverter(Date.class, String.class, new DateStringConverter());
        simpleConversionEngine.registerConverter(Byte.class, String.class, new ByteStringConverter());
        simpleConversionEngine.registerConverter(Short.class, String.class, new ShortStringConverter());
        simpleConversionEngine.registerConverter(Integer.class, String.class, new IntegerStringConverter());
        simpleConversionEngine.registerConverter(Float.class, String.class, new FloatStringConverter());
        return simpleConversionEngine;
    }

    public ItemContainerFactory(ConversionEngine conversionEngine) {
        this.conversionEngine = conversionEngine;
    }

    public ItemContainer createItemContainer(Sheet sheet) {
        return new ItemContainer(conversionEngine, sheet);
    }

    public ItemContainer createItemContainer(Sheet sheet, CellCoordinate origin) {
        return new ItemContainer(conversionEngine, sheet, origin);
    }

    public ItemContainer createItemContainer(Sheet sheet, CellCoordinate origin, MovementDirection direction) {
        return new ItemContainer(conversionEngine, sheet, origin, direction);
    }

    public ConversionEngine getConversionEngine() {
        return conversionEngine;
    }
}
