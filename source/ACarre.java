public class ACarre implements Formula {
    Formula formula;

    public ACarre(Formula formula) {
        this.formula = formula;
    }

    @Override
    public String asString() {
        return "A◻" + formula.asString();
    }

    @Override
    public Tree asTree() {
        return null;
    }
}
