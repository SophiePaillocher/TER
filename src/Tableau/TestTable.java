package Tableau;

import Formula.*;

import java.util.ArrayList;

public class TestTable {
    public static void main(String[] args)
    {
        Tree testTree = new Tree();

        Atom a = new Atom("a");
        Atom b = new Atom("b");
        //Forme Conjonctive
        Negation nnf =  new Negation(null, new Negation(null, a));
        Negation naVb = new Negation(null, new QF1opF2(null, new Disjunction(), a,b));
        Negation nEaUb = new Negation(null, new QF1opF2(new Every(), new Until(), a,b));
        Negation nAaUb = new Negation(null, new QF1opF2(new ForAll(), new Until(),a,b));
        QopF eCarre = new QopF(new Every(), new Square(), a);
        QopF aCarre = new QopF(new ForAll(), new Square(),a);

        ArrayList<Node> conjunctions = new ArrayList<>();
        ArrayList<Formula> empty = new ArrayList<>();

        ArrayList<Formula> list1 = new ArrayList<>();
        list1.add(nnf);
        Node n1 = new Node(empty, list1);
        conjunctions.add(n1);

        ArrayList<Formula> list2 = new ArrayList<>();
        list2.add(naVb);
        Node n2 = new Node(empty, list2);
        conjunctions.add(n2);

        ArrayList<Formula> list3 = new ArrayList<>();
        list3.add(nEaUb);
        Node n3 = new Node(empty, list3);
        conjunctions.add(n3);

        ArrayList<Formula> list4 = new ArrayList<>();
        list4.add(nAaUb);
        Node n4 = new Node(empty, list4);
        conjunctions.add(n4);

        ArrayList<Formula> list5 = new ArrayList<>();
        list5.add(eCarre);
        Node n5 = new Node(empty, list4);
        conjunctions.add(n5);

        ArrayList<Formula> list6 = new ArrayList<>();
        list6.add(aCarre);
        Node n6 = new Node(empty, list6);
        conjunctions.add(n6);
/*
        for(Node n : conjunctions)
        {
            ArrayList<Node> temp = testTree.traiter(n);
            if(temp.size() == 0)
            {
                System.out.println("Uston we have a problem!");
            }
            else {
                System.out.println("=======" +n.toString());
                for (Node m : temp) {
                    System.out.println(m.toString());
                }
            }
        }
*/
        //Disjonctions
        ArrayList<Node> disjonctions = new ArrayList<>();

        QF1opF2 aVb = new QF1opF2(null, new Disjunction(), a,b);
        QF1opF2 eaUb = new QF1opF2(new Every(), new Until(), a,b);
        QF1opF2 aaUb = new QF1opF2(new ForAll(), new Until(), a,b);
        QopF eDiamond = new QopF(new Every(), new Diamond(), a);
        QopF aDiamond = new QopF(new ForAll(), new Diamond(), a);

        ArrayList<Formula> lst1 = new ArrayList<>();
        lst1.add(aVb);
        Node d1 = new Node(empty, lst1);
        disjonctions.add(d1);

        ArrayList<Formula> lst2 = new ArrayList<>();
        lst2.add(eaUb);
        Node d2 = new Node(empty, lst2);
        disjonctions.add(d2);

        ArrayList<Formula> lst3 = new ArrayList<>();
        lst3.add(aaUb);
        Node d3 = new Node(empty, lst3);
        disjonctions.add(d3);

        ArrayList<Formula> lst4 = new ArrayList<>();
        lst4.add(eDiamond);
        Node d4 = new Node(empty, lst4);
        disjonctions.add(d4);

        ArrayList<Formula> lst5 = new ArrayList<>();
        lst5.add(aDiamond);
        Node d5 = new Node(empty, lst5);
        disjonctions.add(d5);
/*
        for(Node n : disjonctions)
        {
            ArrayList<Node> temp = testTree.traiter(n);
            if(temp.size() == 0)
            {
                System.out.println("Uston we have a problem!  >>>" + n.toString());
            }
            else {
                System.out.println("=======" +n.toString());
                for (Node m : temp) {
                    System.out.println(m.toString());
                }
            }
        }
*/
        //Successeur
        ArrayList<Node> suc = new ArrayList<>();

        QopF eRond = new QopF(new Every(), new Ring(), a);
        Negation neRond = new Negation(null, new QopF(new Every(), new Ring(), a));

        ArrayList<Formula> l1 = new ArrayList<>();
        l1.add(eRond);
        Node s1 = new Node(empty, l1);
        suc.add(s1);

        ArrayList<Formula> l2 = new ArrayList<>();
        l2.add(neRond);
        Node s2 = new Node(empty, l2);
        suc.add(s2);

        System.out.println("-------------------");

        ArrayList<Formula> melange = new ArrayList<>();
        melange.add(neRond);
        melange.add(eaUb);
        melange.add(nnf);
        Node mel = new Node(empty, melange);



        ArrayList<Formula> mel2 = new ArrayList<>();
        mel2.add(nnf);
        mel2.add(eaUb);
        mel2.add(neRond);
        Node m2 = new Node(empty, mel2);
        System.out.println(m2.equals(mel));

        Tree experiment = new Tree(eaUb);

        for(Node n : experiment.getTree())
        {
            System.out.println(n.toString());
        }

    }
}
