package Tableau;
import logic.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

public class Tree {
    ArrayList<Node> tree;
    Node root;

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

    private void create()
    {
        ArrayList<Node> a_traiter = new ArrayList<>();
        ArrayList<Node> traite = new ArrayList<>();
        a_traiter.add(this.root);
        while(a_traiter.size( ) > 0)
        {

        }
    }

    /**
     * This is a function that will look at a node and do the appropriate action giving us it's list of successors
     * if the list is empty then the node has none
     * @param n
     * @return ArrayList</State>
     */
    private ArrayList<Node> traiter(Node n)
    {
        //choice of the formula to develop from the node
        int i = hasConjunctionFormula(n);
        ArrayList<Node> ret = new ArrayList<>() ;
        if(!(i==-1))
        {
            //this will be the to_develop of the "infant" nodes
            ArrayList<Formula> tDvl1 = new ArrayList<>();
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
            //Si ona une negation
            if(n.getTo_develop().get(i) instanceof Negation)
            {
                Negation f1 = (Negation)n.getTo_develop().get(i);
                if(f1.getF() instanceof Negation)
                {
                    tDvl1.add(((Negation)f1.getF()).getF());
                    //todo: missing the markings
                    Node nTmp = new Node();
                    nTmp.getTo_develop().addAll(tDvl1);
                    ret.add(nTmp);
                }
                Node n1 = new Node();
                n1.getTo_develop().addAll(tDvl1);
                Node n2 = new Node();
                n2.getTo_develop().addAll(tDvl1);
                if(f1.getF() instanceof QF1opF2)
                {
                    Negation a;
                    Negation b;
                    QF1opF2 temp = (QF1opF2)f1.getF();
                    if(temp.getQ() == null && temp.getOp() instanceof Disjunction)
                    {
                        a = new Negation(null, temp.getF1());
                        b = new Negation(null, temp.getF2());

                    }

                }
            }
        }
        i = hasDisjuntionFormula(n);
        if(!(i==-1))
        {
            //todo: implementer le tabelau de Disjunction
        }
        i = hasSuccessorFormula(n);
        if(!(i==-1))
        {
            //todo:implementer le tableau de successor
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
            if(f_.getQ() instanceof ForAll && f_.getOp() instanceof Square)
                return true;
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
     * This is to tell if we have f and !f to be dealth with so that we can "close" thebranch
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
