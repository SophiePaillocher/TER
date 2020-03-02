public class EU implements Formula {
    private Formula child1, child2, parent;

    public EU(Formula child1, Formula child2) {
        this.child1 = child1;
        this.child2 = child2;
        child1.setParent(this);
        child2.setParent(this);
        parent = null;
    }

    @Override
    public String asString() {
        return "E(" + child1.asString()+" U " + child2.asString() +")";
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
}
