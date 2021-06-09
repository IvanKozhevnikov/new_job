package ru.kozhevnikov.element;

import java.util.Objects;

public class Element {

    private SequenceColumns sequenceColumns;
    private String number;
    private boolean valid;

    public Element(SequenceColumns sequenceColumns, String number) {
        this.sequenceColumns = sequenceColumns;
        this.number = number;
        valid = !number.isEmpty();
    }

    public boolean isValid() {
        return valid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Element entity = (Element) o;
        return sequenceColumns == entity.sequenceColumns &&
                number.equals(entity.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sequenceColumns, number);
    }

    @Override
    public String toString() {
        return "\"" + number + "\"";
    }
}
