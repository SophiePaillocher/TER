import java.util.ArrayList;

public class Atom implements Formula {

    Formula parent;

    private String name;

    public Atom(String name){
        this.name = name;
        parent = null;
    }

    @Override
    public String asString() {
        return name;
    }

    @Override
    public void setParent(Formula formula) {
        this.parent = formula;
    }

    @Override
    public Formula getParent() {
        return parent;
    }

    @Override
    public Formula getChild() {
        return null;
    }

    @Override
    public Formula rewrite() {
        return this;
    }

    @Override
    public ArrayList<Atom> getAtoms() {
        ArrayList<Atom> toReturn =new ArrayList<>();
        toReturn.add(this);
        return toReturn;
    }

    @Override
    public Formula getChild1() {
        return null;
    }

    @Override
    public Formula getChild2() {
        return null;
    }
}
