package logic;

import Interpretation.Interpretation;
import Interpretation.State;

import java.util.ArrayList;
import java.util.Objects;

public class Atom extends Formula {
    private String name;

    public void Atom(String n){
        this.name = n;
    }

    @Override
    public String toString() {
        return name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Atom)) return false;
        Atom atom = (Atom) o;
        return name.equals(atom.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public Atom(String name) {
        super();
        this.name = name;
    }

    @Override
    public Interpretation getI() {
        return super.getI();
    }

    @Override
    public ArrayList<State> getMarks() {
        return super.getMarks();
    }

    @Override
    public void setI(Interpretation i) {
        super.setI(i);
    }

    @Override
    public void setMarks(ArrayList<State> marks) {
        super.setMarks(marks);
    }

    @Override
    public Formula getParent() {
        return super.getParent();
    }

    @Override
    public void setParent(Formula parent) {
        super.setParent(parent);
    }

    @Override
    public void mark(State e) {
        super.mark(e);
    }

    @Override
    public boolean isMarkedBy(State e) {
        return super.isMarkedBy(e);
    }

    @Override
    public boolean hasParent() {
        return super.hasParent();
    }

    @Override
    public Formula reWrite() {
        return this;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public Formula toNegation() {
        return this;
    }

    @Override
    public boolean isTheSameAsMe(Formula f) {
        if(f instanceof Atom)
            return this.toString().equals(f.toString());
        return false;
    }
}
