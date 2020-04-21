import Interpretation.Interpretation;
import Interpretation.State;
import Tableau.Node;
import Tableau.Tree;
import logic.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // initialisation des états étiquettés
        State s = new State("s");
        s.addLabel("p");
        State s1 = new State("s1");
        s1.addLabel("p");
        State s2 = new State("s2");
        s2.addLabel("q");
        State s3 = new State ("s3");
        s3.addLabel("p");
        s3.addLabel("q");
        s3.addLabel("r");
        State s4 = new State("s4");
        s4.addLabel("p");
        s4.addLabel("q");
        State s5 = new State("s5");
        s5.addLabel("p");
        State s6 = new State ("s6");
        s6.addLabel("p");
        s6.addLabel("r");


        //ajout des relations entre les états
        s.addSuccessor(s6);
        s.addSuccessor(s1);
        s.addSuccessor(s2);
        s6.addSuccessor(s4);
        s1.addSuccessor(s4);
        s2.addSuccessor(s3);
        s4.addSuccessor(s4);
        s3.addSuccessor(s4);
        s3.addSuccessor(s5);
        s5.addSuccessor(s5);

        //création du modèle
        ArrayList<State> states = new ArrayList<>();
        states.add(s);
        states.add(s1);
        states.add(s2);
        states.add(s3);
        states.add(s4);
        states.add(s5);
        states.add(s6);
        Interpretation model = new Interpretation(states,s);

        //La formule EX(-p/\EX(p/\EX(p/\q)))
        Formula p = new Atom("p");
        Formula q = new Atom("q");
        Formula f9 = new QF1opF2(null, new Conjunction(), p,q);
        Formula f8 = new QopF(null, new Every(), new Ring(), f9);
        Formula f6 = new QF1opF2(null, new Conjunction(), p,f8);
        Formula f4 = new QopF(null, new Every(), new Ring(), f6);
        Formula f3 = new Negation(null, p);
        Formula f2 = new QF1opF2(null, new Conjunction(), f3,f4);
        Formula psy = new QopF(null, new Every(), new Ring(), f2);

        System.out.println("psy = " + psy.toString());

        // Vérifions Sat(p)
        ArrayList<State> satp = model.sat_(p);
        System.out.print("sat(p)= ");
        for (State state : satp)
             System.out.print(state.getName() +" ; ");
        System.out.println();

        //Verifions Sat(f3)
        ArrayList<State> satNonp = model.sat_(f3);
        System.out.print("sat("+f3.toString()+")= ");
        for (State state : satNonp)
            System.out.print(state.getName() +" ; ");
        System.out.println();

        //Vérifions Sat(q)
        ArrayList<State> satq = model.sat_(q);
        System.out.print("sat(q)= ");
        for (State state : satq)
            System.out.print(state.getName() +" ; ");
        System.out.println();

        //Verifions Sat(f9)
        ArrayList<State> satf9 = model.sat_(f9);
        System.out.print("sat("+f9.toString()+")= ");
        for (State state : satf9)
            System.out.print(state.getName() +" ; ");
        System.out.println();

        //Vérifions Sat(f8)
        ArrayList<State> satf8 = model.sat_(f8);
        System.out.print("sat("+f8.toString()+")= ");
        for (State state : satf8)
            System.out.print(state.getName() +" ; ");
        System.out.println();

        //Vérifions Sat(f6)
        ArrayList<State> satf6 = model.sat_(f6);
        System.out.print("sat("+f6.toString()+")= ");
        for (State state : satf6)
            System.out.print(state.getName() +" ; ");
        System.out.println();

        //Vérifions Sat(f4)
        ArrayList<State> satf4 = model.sat_(f4);
        System.out.print("sat("+f4.toString()+")= ");
        for (State state : satf4)
            System.out.print(state.getName() +" ; ");
        System.out.println();

        //Verifions SAt(f2)
        ArrayList<State> satf2 = model.sat_(f2);
        System.out.print("sat("+f2.toString()+")= ");
        for (State state : satf8)
            System.out.print(state.getName() +" ; ");
        System.out.println();

        //Vérifions Sat(psy)
        ArrayList<State> satpsy = model.sat_(psy);
        System.out.print("sat("+psy.toString() +")= ");
        for (State state : satpsy)
            System.out.print(state.getName() +" ; ");
        System.out.println();

        Tree test1 = new Tree(f6);
        test1.create_OS(s);
        System.out.println("\n");

        for(Node gunter : test1.getTree())
        {
            System.out.println(gunter.toString());
        }
    }
}
