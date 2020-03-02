public class ARond implements Formula {
    Formula formula;

    public ARond(Formula formula) {
        this.formula = formula;
    }

    @Override
    public String asString() {
        return "A◯" + formula.asString();
    }

    @Override
    public Tree asTree() {
        return null;
    }
}
