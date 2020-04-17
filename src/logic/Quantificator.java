package logic;

import java.util.Objects;

public abstract class Quantificator {

    private final String name;

    public Quantificator(String name) {
        this.name = name;
    }

    public String toString(){
        return this.name;
    };

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quantificator)) return false;
        Quantificator that = (Quantificator) o;
        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
