import java.util.ArrayList;

public class ACarre implements Formula {
    Formula child;
    Formula parent;

    public ACarre(Formula formula) {
        this.child = formula;
        child.setParent(this);
        parent = null;
    }

    @Override
    public String asString() {
        return "A◻" + child.asString();
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
        return new Negation(new EU (new Atom("True"), new Negation(child.rewrite())));
    }
    @Override
    public ArrayList<Formula> getAtoms() {
        return child.getAtoms();
    }
}
