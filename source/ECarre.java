public class ECarre implements Formula {
    Formula child, parent;

    public ECarre(Formula child) {
        this.child = child;
        child.setParent(this);
        parent = null;
    }

    @Override
    public String asString() {
        return "E◻" + child.asString();
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
        return new Negation(new ALosange(new Negation(child.rewrite())));
    }
}
