public class EU implements Formula {
    private Formula formula1, formula2;

    public EU(Formula formula1, Formula formula2) {
        this.formula1 = formula1;
        this.formula2 = formula2;
    }

    @Override
    public String asString() {
        return "E(" + formula1.asString()+" U " + formula2.asString() +")";
    }


    @Override
    public Tree asTree() {
        return null;
    }
}
