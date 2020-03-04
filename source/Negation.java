import java.util.ArrayList;

public class Negation implements Formula
{
    Formula child, parent;

    public Negation(Formula child) {
        this.child = child;
        child.setParent(this);
        parent = null;
    }

    @Override
    public String asString() {
        return "¬ " + child.asString();
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
        return child;
    }

    @Override
    public Formula rewrite() {
        return this;
    }
    @Override
    public ArrayList<Formula> getAtoms() {
        return child.getAtoms();
    }

    @Override
    public Formula getChild1() {
        return child;
    }

    @Override
    public Formula getChild2() {
        return child;
    }
}
