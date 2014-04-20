package ru.dlevin.excelmapper.engine;

import ru.dlevin.excelmapper.utils.Validate;

import java.util.*;

/**
 * User: Dmitry Levin
 * Date: 15.03.14
 */
public class CellDefinitions implements Collection<CellDefinition> {

    private List<CellDefinition> cellDefinitions;
    private CellStyleReference cellStyleReference = CellStyleReference.UNDEFINED;

    public CellDefinitions() {
        this(new ArrayList<CellDefinition>());
    }

    public CellDefinitions(List<CellDefinition> cellDefinitions) {
        this(CellStyleReference.UNDEFINED, cellDefinitions);
    }

    public CellDefinitions(CellDefinition... cellDefinitions) {
        this(Arrays.asList(cellDefinitions));
    }

    public CellDefinitions(CellStyleReference cellStyleReference) {
        this(cellStyleReference, new ArrayList<CellDefinition>());
    }

    public CellDefinitions(CellStyleReference cellStyleReference, List<CellDefinition> cellDefinitions) {
        Validate.notNull(cellStyleReference, "Can not create cell definitions with null cell style references");
        Validate.notNull(cellDefinitions, "Can not create cell definitions with null cell definitions");
        this.cellStyleReference = cellStyleReference;
        this.cellDefinitions = new ArrayList<CellDefinition>(cellDefinitions);
    }

    public CellDefinitions(CellStyleReference cellStyleReference, CellDefinition... cellDefinitions) {
        this(cellStyleReference, Arrays.asList(cellDefinitions));
    }

    public static CellDefinitions fromReferences(Collection<ReadableValueReference<?>> references) {
        CellDefinitions definitions = new CellDefinitions();
        definitions.addReferences(references);
        return definitions;
    }

    public static CellDefinitions fromReferences(ReadableValueReference... references) {
        Collection<ReadableValueReference<?>> refs = Arrays.<ReadableValueReference<?>>asList(references);
        return fromReferences(refs);
    }

    public CellStyleReference getCellStyleReference() {
        return cellStyleReference;
    }

    public void setCellStyleReference(CellStyleReference cellStyleReference) {
        this.cellStyleReference = cellStyleReference;
        //do not propagate style change to existing cells
    }

    @Override
    public int size() {
        return cellDefinitions.size();
    }

    @Override
    public boolean isEmpty() {
        return cellDefinitions.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return cellDefinitions.contains(o);
    }

    @Override
    public Iterator<CellDefinition> iterator() {
        return cellDefinitions.iterator();
    }

    @Override
    public Object[] toArray() {
        return cellDefinitions.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return cellDefinitions.toArray(a);
    }

    @Override
    public boolean add(CellDefinition cellDefinition) {
        Validate.notNull(cellDefinition, "Can not add null cell definition to definition list");
        applyCellStyleIfNotSet(cellDefinition);
        return cellDefinitions.add(cellDefinition);
    }

    public boolean add(ReadableValueReference reference) {
        Validate.notNull(reference, "Can not add null value reference to definition list");
        CellDefinition cellDefinition = new CellDefinition(reference);
        applyCellStyleIfNotSet(cellDefinition);
        return cellDefinitions.add(cellDefinition);
    }

    private void applyCellStyleIfNotSet(CellDefinition cellDefinition) {
        if (CellStyleReference.UNDEFINED.equals(cellDefinition.getCellStyleReference())) {
            cellDefinition.setCellStyleReference(this.cellStyleReference);
        }
    }

    @Override
    public boolean remove(Object o) {
        return cellDefinitions.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return cellDefinitions.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends CellDefinition> c) {
        Validate.notNull(c, "Can not add null collection of cell definitions to definition list");
        for (CellDefinition definition : c) {
            Validate.notNull(definition, "Can not add null cell definition to definition list");
            applyCellStyleIfNotSet(definition);
        }
        return cellDefinitions.addAll(c);
    }

    public boolean addReferences(ReadableValueReference... references) {
        Collection<ReadableValueReference<?>> refs = Arrays.<ReadableValueReference<?>>asList(references);
        return addReferences(refs);
    }

    public boolean addReferences(Collection<? extends ReadableValueReference> c) {
        Validate.notNull(c, "Can not add null collection of value references to definition list");
        List<CellDefinition> definitions = new ArrayList<CellDefinition>(c.size());
        for (ReadableValueReference reference : c) {
            Validate.notNull(reference, "Can not add null value reference to definition list");
            CellDefinition cellDefinition = new CellDefinition(reference);
            applyCellStyleIfNotSet(cellDefinition);
            definitions.add(cellDefinition);
        }
        return cellDefinitions.addAll(definitions);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return cellDefinitions.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return cellDefinitions.retainAll(c);
    }

    @Override
    public void clear() {
        cellDefinitions.clear();
    }
}
