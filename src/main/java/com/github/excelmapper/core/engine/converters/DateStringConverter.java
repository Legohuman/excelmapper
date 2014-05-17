package com.github.excelmapper.core.engine.converters;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: Dmitry Levin
 * Date: 24.03.14
 */
public class DateStringConverter implements Converter<Date, String> {

    public static final String DEFAULT_DATE_FORMAT_STR = "dd.MM.yyyy";

    private final String dateFormatString;
    private final DateFormat dateFormat;

    public DateStringConverter() {
        this(DEFAULT_DATE_FORMAT_STR);
    }

    public DateStringConverter(String dateFormatString) {
        this.dateFormatString = dateFormatString;
        this.dateFormat = new SimpleDateFormat(dateFormatString);
    }

    public String getDateFormatString() {
        return dateFormatString;
    }

    @Override
    public String convertTo(Date sourceValue) {
        if (sourceValue == null) return null;
        return dateFormat.format(sourceValue);
    }

    @Override
    public Date convertFrom(String destinationValue) {
        if (destinationValue == null) return null;
        try {
            return dateFormat.parse(destinationValue);
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
