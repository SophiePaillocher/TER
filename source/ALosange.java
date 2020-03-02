public class ALosange implements Formula {

    Formula child;
    Formula parent;

    public ALosange(Formula child) {
        this.child = child;
        child.setParent(this);
        parent = null;
    }

    @Override
    public String asString() {
        return "A◊" + child.asString();
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
