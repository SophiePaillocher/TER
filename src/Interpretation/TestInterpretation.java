package Interpretation;

import logic.*;

import java.util.ArrayList;

public class TestInterpretation {

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

        QopF f8 = new QopF(new Every(), new Ring(), new QF1opF2(null, new Disjunction(), new Atom("p"), new Atom ("q")));

        ArrayList<State> t = model.sat_(f8);
        for(State n : t)
        {
            System.out.println(n.toString());
        }
        /**

        //testons si chaque état a bien la liste de ses prédécesseurs
        for (State state : states ){
            System.out.print("les successeurs de "+ state.getName()+ " sont : ");
            for (State succ : state.getSuccessors()){
                System.out.print(succ.getName() + " ");
            }
            System.out.println();
            System.out.print("les prédécesseurs de "+ state.getName()+ " sont : ");
            for (State pred : state.getPredecessors()){
                System.out.print(pred.getName() + " ");
            }
            System.out.println();
        }

        //testons si les états sont bien étiquettés
        for (State state : states ){
            System.out.println("l'état " + state.getName()+" est étiquetté : " + state.getLabels());
        }
        **/
    }


}