public class ALosange implements Formula {

    Formula formula;

    public ALosange(Formula formula) {
        this.formula = formula;
    }

    @Override
    public String asString() {
        return "A◊" + formula.asString();
    }

    @Override
    public Tree asTree() {
        return null;
    }
}
