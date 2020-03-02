public class ERond implements Formula {
    Formula formula;

    public ERond(Formula formula) {
        this.formula = formula;
    }

    @Override
    public String asString() {
        return "E◯" + formula.asString();
    }

    @Override
    public Tree asTree() {
        return null;
    }
}

