package ru.dlevin.excelmapper.engine;

import org.apache.poi.ss.usermodel.CellStyle;

/**
 * User: Dmitry Levin
 * Date: 07.03.14
 */
public interface CellStyleReference {

    public static final CellStyleReference UNDEFINED = new CellStyleReference() {
        @Override
        public CellStyle getCellStyle() {
            return null;
        }
    };

    CellStyle getCellStyle();
}
