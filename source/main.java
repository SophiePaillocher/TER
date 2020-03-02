public class main {
    public static void main(String[] args){
        Formula p = new Atom("p");
        Formula q = new Atom("q");
        Formula f1 = new Conjonction(p,q);
        System.out.println("f1 = " + f1.asString());
        Formula f2 = new Negation(f1);
        System.out.println("f2 = " + f2.asString());
        Formula f3 = new Disjonction(p, f1);
        System.out.println("f3 = " + f3.asString());
        Formula f4 = new ACarre(f3);
        System.out.println("f4 = " + f4.asString());
        Formula f5 = new ECarre(f3);
        System.out.println("f5 = " + f5.asString());
        Formula f6 = new ARond(f3);
        System.out.println("f6 = " + f6.asString());
        Formula f7 = new AU(p,f1);
        System.out.println(("f7 = " + f7.asString()));
    }
}
