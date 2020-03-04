import java.text.Normalizer;

public class main {
    public static void main(String[] args){
        Formula p = new Atom("p");
        Formula f = new ACarre(p);
        Formula F = new ELosange(f);
        System.out.println(F.asString());
        Formula g = F.rewrite();
        System.out.println(g.asString());
    }
}
