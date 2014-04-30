package ru.dlevin.excelmapper.engine;

import org.apache.poi.ss.usermodel.CellStyle;

/**
 * User: Dmitry Levin
 * Date: 07.03.14
 */
public interface DynamicCellStyleReference<SC extends Enum> extends CellStyleReference {

    void registerCellStyle(SC styleCode, CellStyle style);

    SC getCellStyleCode();
}
