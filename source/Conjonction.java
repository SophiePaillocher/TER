import java.util.ArrayList;

public class Conjonction implements Formula{

    private Formula child1, child2, parent;

    public Conjonction(Formula child1, Formula child2){
        this.child1 = child1;
        this.child2 = child2;
        child1.setParent(this);
        child2.setParent(this);
        parent = null;
    }

    @Override
    public String asString() {

        return "(" + child1.asString() + " ∧ " + child2.asString() +")";
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
        ArrayList<Atom> atoms = child1.getAtoms();
        atoms.addAll(child2.getAtoms());
        return atoms;
    }

    @Override
    public Formula getChild1() {
        return child1;
    }

    @Override
    public Formula getChild2() {
        return child2;
    }
}
