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
    public Formula rewrite() {
        return this;
    }

    @Override
    public ArrayList<Formula> getAtoms() {
        ArrayList<Formula> toReturn =new ArrayList<>();
        toReturn.add(this);
        return toReturn;
    }
}
