import java.util.ArrayList;

public class ELosange implements Formula {
    Formula child, parent;

    public ELosange(Formula child) {
        this.child = child;
        child.setParent(this);
        parent = null;
    }

    @Override
    public String asString() {
        return "E◊" + child.asString();
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
        return new EU(new Atom("True"), child.rewrite());
    }
    @Override
    public ArrayList<Formula> getAtoms() {
        return child.getAtoms();
    }
}
