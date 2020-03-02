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
    public Formula rewrite() {
        return this;
    }
}
