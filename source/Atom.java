public class Atom implements Formula {

    private String name;

    public Atom(String name){
        this.name = name;
    }

    @Override
    public String asString() {
        return name;
    }

    @Override
    public Tree asTree() {
        return null;
    }
}
