public class ELosange implements Formula {
    Formula formula;

    public ELosange(Formula formula) {
        this.formula = formula;
    }

    @Override
    public String asString() {
        return "E◊" + formula.asString();
    }
    @Override
    public Tree asTree() {
        return null;
    }
}
