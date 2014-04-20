package ru.dlevin.excelmapper.engine.converters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: Dmitry Levin
 * Date: 24.03.14
 */
public class DateStringConverter implements Converter<Date, String> {

    public static final String DATE_FORMAT_STR = "dd.MM.yyyy";
    private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(DATE_FORMAT_STR);
        }
    };

    @Override
    public String convertTo(Date sourceValue) {
        if (sourceValue == null) return null;
        return DATE_FORMAT.get().format(sourceValue);
    }

    @Override
    public Date convertFrom(String destinationValue) {
        if (destinationValue == null) return null;
        try {
            return DATE_FORMAT.get().parse(destinationValue);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Can not parse date: " + destinationValue, e);
        }
    }

    @Override
    public Class<Date> getSourceType() {
        return Date.class;
    }

    @Override
    public Class<String> getDestinationType() {
        return String.class;
    }
}
