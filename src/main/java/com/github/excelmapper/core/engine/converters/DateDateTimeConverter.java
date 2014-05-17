package com.github.excelmapper.core.engine.converters;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * User: Dmitry Levin
 * Date: 24.03.14
 */
public class DateDateTimeConverter implements Converter<Date, DateTime> {

    @Override
    public DateTime convertTo(Date sourceValue) {
        if (sourceValue == null) return null;
        return new DateTime(sourceValue);
    }

    @Override
    public Date convertFrom(DateTime destinationValue) {
        if (destinationValue == null) return null;
        return destinationValue.toDate();
    }

    @Override
    public Class<Date> getSourceType() {
        return Date.class;
    }

    @Override
    public Class<DateTime> getDestinationType() {
        return DateTime.class;
    }
}
