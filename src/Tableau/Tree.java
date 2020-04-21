package Tableau;
<<<<<<< HEAD
import Interpretation.State;
import Formula.*;
=======
import logic.*;
>>>>>>> parent of 815d542... OS qui marche (besoin de tester un peut plus peut etre)

import java.util.ArrayList;
import java.util.Objects;

public class Tree {
    Node root;
    ArrayList<Node> tree;


    public Tree() {
        tree = new ArrayList<>();
    }

    public Tree(ArrayList<Node> tree, Node root) {
        this.tree = tree;
        this.root = root;
        create_ExtendedClosure();
    }

    public Tree(Node root) {
        this.root = root;
        this.tree = new ArrayList<>();
        create_ExtendedClosure();
    }

    public Tree(Formula f)
    {
        this.tree = new ArrayList<>();
        this.root = new Node();
        root.getTo_develop().add(f);
        create_ExtendedClosure();
    }

    public void setTree(ArrayList<Node> tree) {
        this.tree = tree;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return root;
    }

    public ArrayList<Node> getTree() {
        return tree;
    }

    @Override
    public String toString() {
        String ret = root.toString();
        for(int i = 1; i < tree.size();i++)
        {
            ret = ret + tree.get(i).toString();
        }
        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tree)) return false;
        Tree tree = (Tree) o;
        return Objects.equals(getRoot(), tree.getRoot());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoot());
    }

    private void create_ExtendedClosure()
    {
        ArrayList<Node> a_traiter = new ArrayList<>();
        ArrayList<Node> traite = new ArrayList<>();
        ArrayList<Node> temp;

        a_traiter.add(this.root);
        tree.add(root);
        boolean test = false;
        while( ! test)
        {
            temp = traiter_ExtendedClosure(a_traiter.get(0));//temp detien la liste des somets qui genere le premier de la liste a traiter
           int c;
            for(c = 0; c < temp.size(); c++)
           {
               Node m = temp.get(c);
               // on parcour temp et on va ferifier si il est deja dans l'arbre
               for(Node t : tree)
               {
                   if(t.getNumber() == 5)
                       System.out.println(m.equals(t));
                   if(m.equals(t))
                   { //si on l'a deja croise on fait le lien au neud qui existe deja
                       a_traiter.get(0).addFollower(t);
                       temp.remove(c);
                   }
               }
           }
            for(Node n : temp)
            {
                a_traiter.get(0).addFollower(n);
                a_traiter.add(n);
                tree.add(n);
            }
            traite.add(a_traiter.get(0));
           a_traiter.remove(0);
           if(a_traiter.size()==0)
               test=true;
        }
    }
    /**
     * This is a function that will look at a node and do the appropriate action giving us it's list of successors
     * if the list is empty then the node has none
     * @param n
     * @return ArrayList</Node>
     */
    private ArrayList<Node> traiter_ExtendedClosure(Node n)
    {
        //choice of the formula to develop from the node
        int i = hasConjunctionFormula(n);
        ArrayList<Node> ret = new ArrayList<>() ;
        if(!(i==-1))
        {
            //this is where we "store the node we chose" so that when reading through we know what "operation" we chose
            n.setChosenOne(n.getTo_develop().get(i));
            //this will be the to_develop of the "infant" nodes
            ArrayList<Formula> tDvl1 = new ArrayList<>();
            ArrayList<Formula> mrk = new ArrayList<>(n.getMarks());
            mrk.add(n.getTo_develop().get(i));
            //if there are some formulas to carry over to the infants this is where we get them
            if(n.getTo_develop().size() > 1)
            {
                for(int j =0; j < n.getTo_develop().size() ; j++)
                {
                    if(j != i)
                    {
                        tDvl1.add(n.getTo_develop().get(j));
                    }
                }
            }
            //Si on a une negation ¬F
            if(n.getTo_develop().get(i) instanceof Negation)
            {
                Negation f1 = (Negation)n.getTo_develop().get(i);
                //¬¬(F)
                if(f1.getF() instanceof Negation)
                {
                    tDvl1.add(((Negation)f1.getF()).getF());
                    Node nTmp = new Node(mrk, tDvl1);
                    nTmp.setNumber(n.getNumber()+1);
                    ret.add(nTmp);
                }
                Node n1 = new Node(mrk, tDvl1);
                n1.setNumber(n.getNumber()+1);
                Node n2 = new Node(mrk, tDvl1);
                n2.setNumber(n.getNumber()+2);
                //¬(Q(f1 Op f2))
                if(f1.getF() instanceof QF1opF2)
                {
                    Negation a;
                    Negation b;
                    QF1opF2 temp = (QF1opF2)f1.getF();
                    //¬(a v b)
                    if(temp.getQ() == null && temp.getOp() instanceof Disjunction)
                    {
                        a = new Negation(null, temp.getF1());
                        b = new Negation(null, temp.getF2());
                        n1.getTo_develop().add(a);
                        n2.getTo_develop().add(b);
                        ret.add(n1);
                        ret.add(n2);
                    }
                    //E (a U b)
                    if(temp.getQ() instanceof Every && temp.getOp() instanceof Until)
                    {
                        b = new Negation(null, temp.getF2());
                        a = new Negation(null, temp.getF1());
                        n1.getTo_develop().add(b);
                        QF1opF2 naVnErond = new QF1opF2(null, new Disjunction(), a, new Negation(null, new QopF(new Every(), new Ring(), temp )));
                        n2.getTo_develop().add(naVnErond);
                        ret.add(n1);
                        ret.add(n2);
                    }
                    //A (a U b)
                    if(temp.getQ() instanceof ForAll && temp.getOp() instanceof Until)
                    {
                        a = new Negation(null, temp.getF1());
                        b = new Negation(null, temp.getF2());
                        n1.getTo_develop().add(b);
                        QF1opF2 naVnArond = new QF1opF2(null, new Disjunction(), a, new QopF(new ForAll(), new Ring(), temp));
                        n2.getTo_develop().add(naVnArond);
                        ret.add(n1);
                        ret.add(n2);
                    }
                }
            }
            //si on a Q op f
            if(n.getTo_develop().get(i) instanceof QopF)
            {
                QopF f1 = (QopF)n.getTo_develop().get(i);
                Node n1 = new Node(mrk, tDvl1);
                n1.setNumber(n.getNumber()+1);
                Node n2 = new Node(mrk, tDvl1);
                n2.setNumber(n.getNumber()+2);
                n1.getTo_develop().add(f1.getF());
                //E G a
                if(f1.getQ() instanceof Every && f1.getOp() instanceof Square)
                {
                    QopF eRond = new QopF(new Every(), new Ring(), f1);
                    n2.getTo_develop().add(eRond);
                }
                // A G a
                if(f1.getQ() instanceof ForAll && f1.getOp() instanceof Square)
                {
                    QopF aRond = new QopF(new ForAll(), new Ring(), f1);
                    n2.getTo_develop().add(aRond);
                }
                ret.add(n1);
                ret.add(n2);
            }
            for(int j = 1; j <= ret.size(); j++)
            {
                ret.get(j-1).setNumber(n.getNumber()+j);
            }
            return ret;
        }
        i = hasDisjuntionFormula(n);
        if(!(i==-1))
        {
            //this is where we "store the node we chose" so that when reading through we know what "operation" we chose
            n.setChosenOne(n.getTo_develop().get(i));
            //this will be the to_develop of the "infant" nodes
            ArrayList<Formula> tDvl1 = new ArrayList<>();
            ArrayList<Formula> mrk = new ArrayList<>(n.getMarks());
            mrk.add(n.getTo_develop().get(i));
            //if there are some formulas to carry over to the infants this is where we get them
            if(n.getTo_develop().size() > 1)
            {
                for(int j =0; j < n.getTo_develop().size() ; j++)
                {
                    if(j != i)
                    {
                        tDvl1.add(n.getTo_develop().get(j));
                    }
                }
            }
            Node a = new Node(mrk, tDvl1);
            Node b = new Node (mrk, tDvl1);
            //Q(f1 Op f2)
            if(n.getTo_develop().get(i) instanceof QF1opF2)
            {
                QF1opF2 f1 = (QF1opF2)n.getTo_develop().get(i);
                //a V b
                if(f1.getQ() == null && f1.getOp() instanceof Disjunction)
                {
                    a.getTo_develop().add(f1.getF1());
                    b.getTo_develop().add(f1.getF2());
                    ret.add(a);
                    ret.add(b);
                }
                //E (a U b)
                if(f1.getQ() instanceof Every && f1.getOp() instanceof Until)
                {
                    a.getTo_develop().add(f1.getF2());
                    b.getTo_develop().add(new QF1opF2(null, new Disjunction(), new Negation(null, f1.getF1()),new Negation(null, new QopF(new Every(), new Ring(), f1))));
                    ret.add(a);
                    ret.add(b);
                }
                //A(a U b)
                if(f1.getQ() instanceof ForAll && f1.getOp() instanceof Until)
                {
                    a.getTo_develop().add(f1.getF2());
                    b.getTo_develop().add(new QF1opF2(null, new Conjunction(),f1.getF1(), f1));
                    ret.add(a);
                    ret.add(b);
                }

            }
            //Q Op F
            if(n.getTo_develop().get(i) instanceof QopF)
            {
                QopF f1 = (QopF)n.getTo_develop().get(i);
                a.getTo_develop().add(f1.getF());
                //E F f
                if(f1.getQ() instanceof Every && f1.getOp() instanceof Diamond)
                {
                    b.getTo_develop().add(new QopF(new Every(), new Ring(), f1));
                    ret.add(a);
                    ret.add(b);
                }
                //A F f
                if(f1.getQ() instanceof ForAll && f1.getOp() instanceof Diamond)
                {
                    b.getTo_develop().add(new QopF(new ForAll(), new Ring(), f1));
                    ret.add(a);
                    ret.add(b);
                }
            }
            int indice = 1;
            for(int j = 0; j < ret.size(); j++)
            {
                ret.get(j).setNumber(n.getNumber()+indice);
                indice++;
            }
            return ret;
        }
        i = hasSuccessorFormula(n);
        if(!(i==-1))
        {
            n.setChosenOne(n.getTo_develop().get(i));
            ArrayList<Formula> tDvl1 = new ArrayList<>();
            ArrayList<Formula> mrk = new ArrayList<>();
            //if there are some formulas to carry over to the infants this is where we get them
            if(n.getTo_develop().get(i) instanceof Negation)
            {

                Negation f = (Negation)n.getTo_develop().get(i);
                tDvl1.add(new Negation(null, ((QopF)f.getF()).getF()));
                ret.add(new Node(mrk, tDvl1));
            }
            else
            {
                QopF f = (QopF)n.getTo_develop().get(i);
                tDvl1.add(f.getF());
                ret.add(new Node(mrk, tDvl1));
            }
            for(int j = 1; j <= ret.size(); j++)
            {
                ret.get(j-1).setNumber(n.getNumber()+j);
            }
            return ret;
        }
        if(n.getTo_develop().size() == 1)
            n.setChosenOne(n.getTo_develop().get(0));
        for(int j = 1; j <= ret.size(); j++)
        {
            ret.get(j-1).setNumber(n.getNumber()+j);
        }
        return ret;
    }
    private int hasConjunctionFormula(Node n)
    {
        ArrayList<Formula> contestants = new ArrayList<>();
        for(Formula f : n.getTo_develop())
        {
            if(isConjonctive(f))
                contestants.add(f);
        }
        if(!(contestants.size() == 0))
        {
            return n.getTo_develop().indexOf(contestants.get(0));
        }
        return -1;
    }
    private int hasDisjuntionFormula(Node n)
    {
        ArrayList<Formula> contestants = new ArrayList<>();
        for(Formula f : n.getTo_develop())
        {
            if(isDisjonctive(f))
                contestants.add(f);
        }
        if(!(contestants.size() == 0))
        {
            return n.getTo_develop().indexOf(contestants.get(0));
        }
        return -1;
    }
    private int hasSuccessorFormula(Node n)
    {
        ArrayList<Formula> contestants = new ArrayList<>();
        for(Formula f : n.getTo_develop())
        {
            if(isSuccessor(f))
                contestants.add(f);
        }
        if(!(contestants.size() == 0))
        {
            return n.getTo_develop().indexOf(contestants.get(0));
        }
        return -1;
    }
    private boolean isConjonctive(Formula f)
    {
        if(f instanceof Negation)
        {
            if(((Negation)f).getF() instanceof Negation)
            {
                return true;
            }
            if(((Negation)f).getF() instanceof QF1opF2)
                return true;
        }
        if(f instanceof QopF)
        {
            QopF f_ = (QopF)f;
            if(f_.getQ() instanceof Every && f_.getOp() instanceof Square)
                return true;
            return f_.getQ() instanceof ForAll && f_.getOp() instanceof Square;
        }
        return false;
    }
    private boolean isDisjonctive(Formula f)
    {
        if(f instanceof QF1opF2)
        {
            QF1opF2 f_ = (QF1opF2)f;
            if(f_.getQ() == null && f_.getOp() instanceof Disjunction)
                return true;
            if(f_.getOp() instanceof Until)
                return true;
        }
        if(f instanceof QopF)
        {
            QopF f_ = (QopF)f;
            if(f_.getOp() instanceof Diamond)
            {
                if(f_.getQ() instanceof Every || f_.getQ() instanceof  ForAll)
                    return true;
            }
        }
        return false;
    }
    private boolean isSuccessor(Formula f)
    {
        if(f instanceof QopF)
        {
            QopF f_ = (QopF)f;
            if(f_.getQ() instanceof Every && f_.getOp() instanceof Ring)
                return true;
        }
        if(f instanceof Negation)
            return isSuccessor(((Negation)f).getF());
        return false;
    }

    /**
     * This is to tell if we have f and !f to be dealt with so that we can "close" the branch
     * @param list
     * @return boolean
     */
    private boolean thereIsAContradiction(ArrayList<Formula> list)
    {
        if(list.size() > 1) {
            ArrayList<Negation> negTeam = new ArrayList<>();
            ArrayList<Formula> allTheRest = new ArrayList<>();
            for(Formula temp : list)
            {
                if(temp instanceof Negation)
                {
                    negTeam.add((Negation)temp);
                }
                else
                {
                    allTheRest.add(temp);
                }
            }
            if(negTeam.size() == 0)
                return false;
            for(Negation temp : negTeam)
            {
                for(Formula tempF : allTheRest)
                {
                    if(temp.getF().isTheSameAsMe(tempF))
                        return true;
                }
            }
        }
        return false;
    }
}
