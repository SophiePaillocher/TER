import java.util.ArrayList;

public interface Formula {
    // Retourne la formule sous forme de String
    String asString();
    void setParent(Formula formula);
    Formula getParent();
    Formula getChild();
    // fonction de rééecriture de la fonction en vue du Model Checking
    Formula rewrite();
    //retourne les feuilles de l'arbre syntaxique de la formule
    ArrayList<Formula> getAtoms();

    Formula getChild1();

    Formula getChild2();
}
