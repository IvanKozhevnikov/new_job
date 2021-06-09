package ru.kozhevnikov.element;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class PlentyElement {

    private List<Element> elements = new ArrayList<>();

    public PlentyElement(String... strings) {
        for (SequenceColumns sequenceColumns : SequenceColumns.values()) {
            elements.add(new Element(sequenceColumns, strings[sequenceColumns.getValue()]));
        }
    }

    public Element getEntity(SequenceColumns sequenceColumns) {
        return elements.get(sequenceColumns.getValue());
    }

    public List<Element> getValidElements() {
        return elements.stream().filter(Element::isValid).collect(Collectors.toList());
    }

    public boolean isValid() {
        for (Element element : elements) {
            if(element.isValid()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlentyElement that = (PlentyElement) o;
        return elements.equals(that.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elements);
    }

    @Override
    public String toString() {
        StringJoiner delimiter = new StringJoiner(";");
        for (Element element : elements)
            delimiter.add(element.toString());
        return delimiter.toString();
    }
}
