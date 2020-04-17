package logic;



public abstract class Operator {
    private final String name;

    protected Operator(String name) {
        this.name = name;
    }

    public String toString()
    {
        return this.name;
    }

}
