public class main {
    public static void main(String[] args){
        Formula p = new Atom("p");
        Formula q = new Atom("q");
        Formula f = new AU(new Disjonction(new Negation(p), q), new Conjonction(p,q));
        System.out.println("f = " + f.asString());
        System.out.println("parent de p : " +p.getParent());
    }
}
