public class Negation implements Formula
{
    Formula formula;

    public Negation(Formula formula) {
        this.formula = formula;
    }

    @Override
    public String asString() {
        return "¬" + formula.asString();
    }

    @Override
    public Tree asTree() {
        return null;
    }
}
