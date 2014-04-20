package ru.dlevin.excelmapper.engine.converters;

import org.joda.time.Instant;

import java.util.Date;

/**
 * User: Dmitry Levin
 * Date: 24.03.14
 */
public class DateInstantConverter implements Converter<Date, Instant> {

    @Override
    public Instant convertTo(Date sourceValue) {
        if (sourceValue == null) return null;
        return new Instant(sourceValue);
    }

    @Override
    public Date convertFrom(Instant destinationValue) {
        if (destinationValue == null) return null;
        return destinationValue.toDate();
    }

    @Override
    public Class<Date> getSourceType() {
        return Date.class;
    }

    @Override
    public Class<Instant> getDestinationType() {
        return Instant.class;
    }
}
