package ru.kozhevnikov.group;

import ru.kozhevnikov.element.PlentyElement;

import java.util.List;
import java.util.Objects;

public class Group {

    private List<PlentyElement> group;

    public Group(List<PlentyElement> group) {
        this.group = group;
    }

    public List<PlentyElement> getGroup() {
        return group;
    }

    public int size() {
        return group.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group groupOne = (Group) o;
        return group.equals(groupOne.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (PlentyElement plentyElement : group) {
            str.append(plentyElement + "\n");
        }
        return str.toString();
    }
}
