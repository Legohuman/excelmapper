package com.github.excelmapper.core.engine.converters;

import java.util.Calendar;
import java.util.Date;

/**
 * User: Dmitry Levin
 * Date: 24.03.14
 */
public class DateCalendarConverter implements Converter<Date, Calendar> {

    @Override
    public Calendar convertTo(Date sourceValue) {
        if (sourceValue == null) return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sourceValue);
        return calendar;
    }

    @Override
    public Date convertFrom(Calendar destinationValue) {
        if (destinationValue == null) return null;
        return destinationValue.getTime();
    }

    @Override
    public Class<Date> getSourceType() {
        return Date.class;
    }

    @Override
    public Class<Calendar> getDestinationType() {
        return Calendar.class;
    }
}
