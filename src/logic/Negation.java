package logic;

import Interpretation.Interpretation;
import Interpretation.State;

import java.util.ArrayList;

public class Negation extends Formula {

    private Formula f;

    public Negation(Formula parent, Formula f) {
        super(parent);
        this.f = f;
        f.setParent(this);
    }

    public Negation(Formula parent, Interpretation i, ArrayList<State> marks, Formula f) {
        super(parent, i, marks);
        this.f = f;
        this.f.setI(i);
        f.setParent(this);
    }

    protected Negation() {
        super();
    }

    public Negation(Formula parent) {
        super(parent);
    }

    public Negation(Formula parent, Interpretation i, ArrayList<State> marks) {
        super(parent, i, marks);
    }

    @Override
    public Interpretation getI() {
        return super.getI();
    }

    @Override
    public void setI(Interpretation i) {
        super.setI(i);
        if(this.f !=null)this.f.setI(i);
    }

    @Override
    public void setMarks(ArrayList<State> marks) {
        super.setMarks(marks);
    }

    @Override
    public Formula getParent() {
        return super.getParent();
    }

    @Override
    public void setParent(Formula parent) {
        super.setParent(parent);
    }

    @Override
    public void mark(State e) {
        super.mark(e);
    }

    public void setF(Formula f) {
        this.f = f;
        f.setParent(this);
        if(this.getI() != null && f.getI() == null)
        {
            this.f.setI(this.getI());
        }
    }

    public Formula getF() {
        return f;
    }

    @Override
    public ArrayList<State> getMarks() {
        return super.getMarks();
    }

    @Override
    public String toString() {
        if (this.getF() instanceof Atom) return "¬" + f.toString();
        return "¬(" + f.toString() + ")";
    }

    @Override
    public boolean isMarkedBy(State e) {
        return super.isMarkedBy(e);
    }

    @Override
    public Formula reWrite() {
        return this;
    }

    @Override
    public boolean hasParent() {
        return super.hasParent();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Negation(Interpretation i) {
        super(i);
    }

    public Negation(Formula parent, Interpretation i) {
        super(parent, i);
    }

    @Override
    public boolean isInNegationForm() {
        return super.isInNegationForm();
    }

    @Override
    public boolean isTheSameAsMe(Formula f) {
        if(f instanceof Negation)
        {
            return this.toString().equals(f.toString());
        }
        return false;
    }

    @Override
    public Formula toNegation() {
        //si mon enfant est aussi une negation on va s'anuller
        if(this.getF() instanceof Negation)
        {
            Formula newson = ((Negation) this.getF()).getF();
            //si moi j'ai un parent'
            if (this.getParent() != null)
            {
                newson.setParent(this.getParent());
                //si le parent est QopF
                if (this.getParent() instanceof QopF) {
                    ((QopF) this.getParent()).setF(newson);
                }
                if (this.getParent() instanceof QF1opF2) {
                    if(this.equals(((QF1opF2)this.getParent()).getF1()))
                    {
                        ((QF1opF2)this.getParent()).setF1(newson);
                    }
                }
            }
            else{newson.setParent(null);}

            //je lance la reecriture en profondeur ou cas ou ce qui reste n'est pas en fomre normal de negation
            if(!newson.isInNegationForm())
            {
                if(newson instanceof QopF)
                {
                    ((QopF) newson).setF(((QopF) newson).getF().toNegation());
                }
                if(newson instanceof QF1opF2)
                {
                    ((QF1opF2)newson).setF1(((QF1opF2)newson).getF1().toNegation());
                    ((QF1opF2)newson).setF2(((QF1opF2)newson).getF2().toNegation());
                }
                if(newson instanceof Negation)
                {
                    ((Negation)newson).setF(((Negation)newson).getF().toNegation());
                }
            }
            return newson;
        }
        if(this.getF() instanceof QopF)
        {
            QopF myson = (QopF) this.getF();
            Negation newf = new Negation(myson, myson.getF());
            myson.getF().setParent(newf);
            myson.setF(newf);
            // A -> E
            if(myson.getQ() instanceof ForAll)
            {
                myson.setQ(new Every());
            }
            //E -> A
            else{
                myson.setQ(new ForAll());
            }
            //Diamon -> Box
            if(myson.getOp() instanceof Diamond)
                myson.setOp(new Square());
            //Box -> Diamond
            if(myson.getOp() instanceof Square)
                myson.setOp(new Diamond());
            //Ring stays the same
            // If I have a parent he is changing sons
            if(this.getParent() != null)
            {
                //my son changes father
                myson.setParent(this.getParent());
                //father changing sons
                if(this.getParent() instanceof QopF)
                {
                    ((QopF)this.getParent()).setF(myson);
                }
                if(this.getParent() instanceof QF1opF2)
                {
                    if(this.equals(((QF1opF2)this.getParent()).getF1()))
                        ((QF1opF2)this.getParent()).setF1(myson);
                    ((QF1opF2)this.getParent()).setF2(myson);
                }
            }
            if(!myson.isInNegationForm())
                myson.getF().toNegation();
            return myson;
        }
        if(this.getF() instanceof  QF1opF2)
        {
            QF1opF2 myson = (QF1opF2) this.getF();
            Negation newF1 = new Negation(myson, myson.getF1());
            newF1.getF().setParent(newF1);
            Negation newF2 = new Negation(myson, myson.getF2());
            newF2.getF().setParent(newF2);
            myson.setF1(newF1);
            myson.setF2(newF2);
            if(myson.getQ() != null){
                if(myson.getQ() instanceof Every)
                    myson.setQ(new ForAll());
                if(myson.getQ() instanceof ForAll)
                    myson.setQ(new Every());
            }
            if(myson.getOp() instanceof Until)
                myson.setOp(new Release());
            if(myson.getOp() instanceof Release)
                myson.setOp(new Until());
            if(myson.getOp() instanceof Conjunction)
                myson.setOp(new Disjunction());
            if(myson.getOp() instanceof Disjunction)
                myson.setOp(new Conjunction());
            if(this.getParent() != null)
            {
                myson.setParent(this.getParent());
                if(this.getParent() instanceof QopF)
                {
                    ((QopF)this.getParent()).setF(myson);
                }
                if(this.getParent() instanceof QF1opF2)
                {
                    if(this.equals(((QF1opF2)this.getParent()).getF1()))
                        ((QF1opF2)this.getParent()).setF1(myson);
                    ((QF1opF2)this.getParent()).setF2(myson);
                }
            }
            if(!myson.isInNegationForm())
            {
                myson.getF1().toNegation();
                myson.getF2().toNegation();
            }
            return myson;
        }
        return this;
    }
}
