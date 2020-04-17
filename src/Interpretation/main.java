package Interpretation;

import java.util.ArrayList;

public class main {

    public static void main(String[] args) {

        // initialisation des états étiquettés
        ArrayList<String> e = new ArrayList<>();
        e.add("p");
        State s = new State("s",e);
        State s1 = new State("s1",e);
        State s5 = new State("s5",e);
        e.add("r");
        State s6 = new State ("s6", e);
        e.add("r");
        State s3 = new State ("s3",e);
        e.clear();
        e.add("q");
        State s2 = new State("s2",e);
        e.add("p");
        State s4 = new State("s4", e);

        //ajout des relations entre les états
        ArrayList<State> tempSucc = new ArrayList<>();
        tempSucc.add(s6);
        tempSucc.add(s1);
        tempSucc.add(s2);
        s.setSuccessors(tempSucc);

        tempSucc.clear();
        /**
        tempSucc.add(s4);
        s6.setSuccessors(tempSucc);
        s1.setSuccessors(tempSucc);
        s2.setSuccessors(tempSucc);
        s4.setSuccessors(tempSucc);
        tempSucc.add(s5);
        s3.setSuccessors(tempSucc);
        tempSucc.clear();
        tempSucc.add(s5);
        s5.setSuccessors(tempSucc);
**/
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

        //testons si chaque état a bien la liste de ses prédécesseurs
        for (State state : states ){
            System.out.print("les successeurs de "+ state.getName()+ " sont : ");
            for (State succ : state.getSuccessors()){
                System.out.print(succ.getName() + " ");
            }
            System.out.println();
            System.out.print("les prédécessures de "+ state.getName()+ " sont : ");
            for (State pred : state.getPredecessors()){
                System.out.print(pred.getName() + " ");
            }
            System.out.println();
        }

    }
}