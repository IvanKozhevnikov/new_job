package ru.kozhevnikov.element;

public enum SequenceColumns {
    First(0),
    Second(1),
    Third(2);

    private int value;

    public int getValue() {
        return value;
    }

    SequenceColumns(int value) {
        this.value = value;
    }
}
