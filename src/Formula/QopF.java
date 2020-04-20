package Formula;

import Interpretation.Interpretation;
import Interpretation.State;

import java.util.ArrayList;
import java.util.Objects;

public class QopF extends Formula {

    private Quantificator q;
    private Operator op;
    private Formula f;

    public Formula getF() {
        return f;
    }

    public Operator getOp() {
        return op;
    }

    public Quantificator getQ() {
        return q;
    }

    public void setF(Formula f) {
        this.f = f;
        f.setParent(this);
    }

    public void setOp(Operator op) {
        this.op = op;
    }

    public void setQ(Quantificator q) {
        this.q = q;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QopF)) return false;
        QopF qopF = (QopF) o;
        return Objects.equals(getQ(), qopF.getQ()) &&
                Objects.equals(getOp(), qopF.getOp()) &&
                Objects.equals(getF(), qopF.getF());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQ(), getOp(), getF());
    }


    @Override
    public String toString() {
        if(q == null) {
            if(op == null) return "(" + f.toString() + ")";
            return op.toString() + "(" + f.toString() + ")";
        }
        String s = q.toString() + op.toString() + "(" + f.toString() + ")";
        return s;
    }

    public QopF(Quantificator q, Operator op, Formula f) {
        super();
        this.q = q;
        this.op = op;
        this.f = f;
        f.setParent(this);
    }

    public QopF(Formula parent, Quantificator q, Operator op, Formula f) {
        super(parent);
        this.q = q;
        this.op = op;
        this.f = f;
        f.setParent(this);
        if(parent != null && parent.getI() != null && this.getI() == null) this.setI(parent.getI());
    }

    public QopF(Formula parent, Interpretation i, ArrayList<State> marks, Quantificator q, Operator op, Formula f) {
        super(parent, i, marks);
        this.q = q;
        this.op = op;
        this.f = f;
        f.setParent(this);
    }

    @Override
    public Interpretation getI() {
        return super.getI();
    }

    @Override
    public ArrayList<State> getMarks() {
        return super.getMarks();
    }

    @Override
    public void setI(Interpretation i) {
        super.setI(i);
        if(this.f != null)this.f.setI(i);

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

    @Override
    public boolean isMarkedBy(State e) {
        return super.isMarkedBy(e);
    }

    @Override
    public Formula reWrite() {

        //E
        if ( q instanceof Every)
        {
            //E Box
            if (op instanceof Square){
                Negation nonADiamond = new Negation(this.getParent());
                QopF aDiamondNf = new QopF(nonADiamond);
                aDiamondNf.setOp(new Diamond());
                aDiamondNf.setQ(new ForAll());
                Negation nonF = new Negation(aDiamondNf);
                nonF.setF(this.getF().reWrite());
                nonF.getF().setParent(nonF);
                aDiamondNf.setF(nonF);
                nonADiamond.setF(aDiamondNf);
                return nonADiamond;
            }
            // E Rond
            if (op instanceof Ring) {
                this.setF(f.reWrite());
                return this;
            }
            //E Losange
            if (op instanceof Diamond){
                QF1opF2 temp1 = new QF1opF2(this.getParent(), new Every(), new Until(), new Atom("True"), this.f.reWrite());
                temp1.getF2().setParent(temp1);
                return temp1;
            }

        }
        //A
        if ( q instanceof ForAll)
        {
            //A Box
            if (op instanceof Square){
                Negation nonEDiamond = new Negation(this.getParent());
                QopF eDiamondNf = new QopF(nonEDiamond);
                eDiamondNf.setQ(new Every());
                eDiamondNf.setOp(new Diamond());
                Negation nonF = new Negation(eDiamondNf);
                nonF.setF(this.getF().reWrite());
                nonF.getF().setParent(nonF);
                eDiamondNf.setF(nonF);
                nonEDiamond.setF(eDiamondNf);
                return nonEDiamond;
            }
            //A Rond
            if (op instanceof Ring) {
                Negation nonF = new Negation(this.getParent(), this.getF().reWrite());
                nonF.getF().setParent(nonF);
                Negation ret = new Negation(this.getParent(), new QopF(new Every(), new Ring(), nonF));
                ret.getF().setParent(ret);
                return ret;
            }
            //A Diamond
            if (op instanceof Diamond){
               QopF aDiamond = new QopF(new ForAll(), new Diamond(), this.getF().reWrite());
               aDiamond.getF().setParent(aDiamond);

               QopF aRond = new QopF(new ForAll(), new Ring(), aDiamond);
               aRond.getF().setParent(aRond);

               QF1opF2 ret = new QF1opF2(this.getParent(), null, new Disjunction(),this.getF().reWrite(), aRond);
               ret.getF1().setParent(ret);
               ret.getF2().setParent(ret);
               return   ret;
            }
        }
        return null;
    }

    public QopF(Formula parent, Interpretation i, ArrayList<State> marks) {
        super(parent, i, marks);
    }

    public QopF() {
        super();
    }

    public QopF(Formula parent) {
        super(parent);
    }

    @Override
    public boolean hasParent() {
        return super.hasParent();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public QopF(Formula parent, Interpretation i) {
        super(parent, i);
    }



    @Override
    public Formula toNegation() {
        if(!this.getF().isInNegationForm())
            this.setF(this.f.toNegation());
        return this;
    }

    @Override
    public boolean isTheSameAsMe(Formula f) {
        if(f instanceof QopF)
        {
            return this.toString().equals(f.toString());
        }
        return false;
    }
}
