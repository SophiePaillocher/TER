import java.util.ArrayList;

public class Disjonction implements Formula {
    private Formula child1, child2, parent;

    public Disjonction(Formula child1, Formula child2){
        this.child1 = child1;
        this.child2 = child2;
        child1.setParent(this);
        child2.setParent(this);
        parent = null;
    }

    @Override
    public String asString() {

        return "("+ child1.asString() + " ∨ " + child2.asString() +")";
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
        return new Negation(new Conjonction(new Negation(child1.rewrite()), new Negation(child2.rewrite())));
    }
    @Override
    public ArrayList<Formula> getAtoms() {
        ArrayList<Formula> atoms = child1.getAtoms();
        atoms.addAll(child2.getAtoms());
        return atoms;
    }
}
