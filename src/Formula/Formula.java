package Formula;

import Interpretation.Interpretation;
import Interpretation.State;

import java.util.ArrayList;

public abstract class  Formula {

    private Formula parent;
    private Interpretation i;
    private ArrayList<State> marks;

    public Formula(Interpretation i){
        this.parent = null;
        this.i = null;
        this.marks = new ArrayList<>();
    }

    public Formula(Formula parent) {
        this.parent = parent;
    }

    public Formula(Formula parent, Interpretation i, ArrayList<State> marks) {
        this.parent = parent;
        this.i = i;
        this.marks = marks;
    }

    public Formula(Formula parent, Interpretation i) {
        this.parent = parent;
        this.i = i;
        mark(i);
    }

    public Formula() {
        this.parent = null;
        this.i = null;
        this.marks = new ArrayList<>();
    }

    public Interpretation getI() {
        return i;
    }

    public ArrayList<State> getMarks() {
        return marks;
    }

    public void setI(Interpretation i) {
        this.i = i;
        mark(i);
    }

    public void setMarks(ArrayList<State> marks) {
        this.marks = marks;
    }

    public Formula getParent() {
        return parent;
    }

    public void setParent(Formula parent) {
        this.parent = parent;
    }

    public void mark(State e){
        marks.add(e);
    }

    //thecnically this does SAT de la formule par apport a l'interpretation
    private void mark(Interpretation i)
    {   State temp;
        String meString = this.toString();
        //Going through all the nodes in the graph
        for (int j = 0; j<i.getModel().size(); j++)
        {
            temp = i.getModel().get(j);
            if(temp.isMarkedBy(meString))
                this.mark(temp);
        }
    }

    /**
     * tells if a formula is marked by a certain node of th interpretation
     * @param e
     * @return boolean
     */
    public boolean isMarkedBy(State e){
        if(marks.indexOf(e) == -1) return false;
        return true;
    }

    /**
     * Tells if a formula as a parent or not
     * @return boolean
     */
    public boolean hasParent(){
        if (this.parent != null)
            return true;
        return false;
    }

    /**
     * simple String output
     * @return String
     */
    public abstract String toString();
    /**
     * this will return an equivalent of the given formula that can be used on CTL model checking
     * @return Formula
     */
    public abstract Formula reWrite();

    /**
     * This will transforma formula in to it's negation form
     * @return Formula
     */
    public abstract Formula toNegation();

    /**
     * this will tell if a formula is or is not in normal negation form
     * @return boolean
     */
    public boolean isInNegationForm(){
         ArrayList<Formula> toCheck= new ArrayList<>();
         toCheck.add(this);
         int previous = 42;
         int checked = 0;
         //collecte de tout les "neuds" de la for,ule dans une liste
         while(previous != toCheck.size())
         {
             previous = toCheck.size();
             if(toCheck.get(checked) instanceof Negation)
             {
                 toCheck.add(((Negation)toCheck.get(checked)).getF());
             }
             if(toCheck.get(checked) instanceof QF1opF2)
             {
                toCheck.add(((QF1opF2)toCheck.get(checked)).getF1());
                toCheck.add(((QF1opF2)toCheck.get(checked)).getF2());

             }
             if(toCheck.get(checked) instanceof QopF)
             {
                toCheck.add(((QopF)toCheck.get(checked)).getF());
             }
             checked++;

         }

        for (Formula formula : toCheck) {
            if (formula instanceof Negation) {
                Negation negation = (Negation) formula;
                if(!(negation.getF() instanceof Atom))
                    return false;
            }
        }
        return true;
    }

    /**
     * This Function verifies that the written output of two functions is the same there by we have the same function
     * @param f
     * @return boolean
     */
    public abstract boolean isTheSameAsMe(Formula f);
}
