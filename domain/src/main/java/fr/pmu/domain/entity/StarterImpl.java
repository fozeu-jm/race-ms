package fr.pmu.domain.entity;

import java.util.Objects;

public class StarterImpl implements Starter{
    private String name;
    private int number;

    public StarterImpl(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public StarterImpl() {
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StarterImpl starter = (StarterImpl) o;
        return name.equals(starter.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
