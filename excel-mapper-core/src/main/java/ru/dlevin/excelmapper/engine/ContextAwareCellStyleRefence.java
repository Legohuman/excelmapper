package ru.dlevin.excelmapper.engine;

import org.apache.poi.ss.usermodel.CellStyle;
import ru.dlevin.excelmapper.utils.Validate;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Dmitry Levin
 * Date: 07.03.14
 */
public abstract class ContextAwareCellStyleRefence<SC extends Enum, C> implements DynamicCellStyleReference<SC>, ContextAware<C> {

    private C context;
    private Map<SC, CellStyle> stylesMap = new HashMap<SC, CellStyle>();
    private CellStyle undefinedCellStyle;

    @Override
    public void setContext(C context) {
        this.context = context;
    }

    @Override
    public void registerCellStyle(SC styleCode, CellStyle style) {
        Validate.notNull(styleCode, "Can not register style with null style code. Use setUndefinedCellStyle() instead");
        Validate.notNull(style, "Can not register null style");
        stylesMap.put(styleCode, style);
    }

    @Override
    public CellStyle getCellStyle() {
        return getCellStyleByCode(getCellStyleCode());
    }

    protected CellStyle getCellStyleByCode(SC styleCode) {
        return styleCode == null ? undefinedCellStyle : stylesMap.get(styleCode);
    }

    public CellStyle getUndefinedCellStyle() {
        return undefinedCellStyle;
    }

    public void setUndefinedCellStyle(CellStyle undefinedCellStyle) {
        this.undefinedCellStyle = undefinedCellStyle;
    }

    @Override
    public C getContext() {
        return context;
    }
}
