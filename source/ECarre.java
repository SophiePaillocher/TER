public class ECarre implements Formula {
    Formula formula;

    public ECarre(Formula formula) {
        this.formula = formula;
    }

    @Override
    public String asString() {
        return "E◻" + formula.asString();
    }
    @Override
    public Tree asTree() {
        return null;
    }
}
