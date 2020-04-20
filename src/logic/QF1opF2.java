package logic;

import Interpretation.Interpretation;
import Interpretation.State;

import java.util.ArrayList;
import java.util.Objects;

public class QF1opF2 extends Formula {

    private Quantificator q;
    private Operator op;
    private Formula f1;
    private Formula f2;

    public void setQ(Quantificator q) {
        this.q = q;
    }

    public void setOp(Operator op) {
        this.op = op;
    }

    public void setF1(Formula f1) {
        this.f1 = f1;
        f1.setParent(this);
    }

    public void setF2(Formula f2) {
        this.f2 = f2;
        f2.setParent(this);
    }

    public Operator getOp() {
        return op;
    }

    public Quantificator getQ() {
        return q;
    }

    public Formula getF1() {
        return f1;
    }

    public Formula getF2() {
        return f2;
    }

    @Override
    public String toString() {
        if(q == null) return "("+f1.toString()+" " +op.toString()+" "+f2.toString()+")";
        return q.toString()+"("+f1.toString()+" "+op.toString()+" "+f2.toString()+")";
    }

    @Override
    public Formula reWrite() {
        //Non (F1 || F2)
        if(q == null){
            if(this.getParent() instanceof Negation){
                //Non(A ou B)
                //I transform the existing Disjunction in to a conjunction and I send it back
                if(op instanceof  Disjunction){
                    Negation tmpf1 = new Negation(this, f1.reWrite());
                    Negation tmpf2 = new Negation(this, f2.reWrite());
                    this.setQ(null);
                    this.setOp(new Conjunction());
                    this.setF1(tmpf1);
                    this.setF2(tmpf2);
                    return this;
                }
            }
        }
        //E (F1 U F2)
        if(op instanceof Until) {
            //A(F1 U F2) >> (!(E[ (!F2) U !(F1 || F2)]) || (A Diamond F2))
            if (q instanceof ForAll){
                //!F2
                Negation nonF2 = new Negation();
                //vu que j'ai pas de parent j'ai comence la negation a blac
                //donc en bas on va lui atribue la reecriture de F2
                nonF2.setF(this.getF2().reWrite());
                //il faut etablir le parent pour la nouvelle formule
                nonF2.getF().setParent(nonF2);

                //!(F1 || F2) > initialize a "blanc"
                Negation nonF1VF2 = new Negation();
                //(F1 || F2) > initialize a full
                QF1opF2 f1Vf2 = new QF1opF2(nonF1VF2, null, new Disjunction(), this.getF1().reWrite(), this.getF2().reWrite());
                //les enfants de (F1 || F2) n'ont pas les bons parents donc on les change
                f1Vf2.getF1().setParent(f1Vf2);
                f1Vf2.getF2().setParent(f1Vf2);
                //On finit !(F1 || F2)
                nonF1VF2.setF(f1Vf2);
                //E ( (!F2) U !(F1 || F2)) > initialize sans parent vu que c'est le seul qu'on ne connait pas
                QF1opF2 eNonF1UNonF2 = new QF1opF2(new Every(), new Until(), nonF2, nonF1VF2);
                //les enfants de E ( (!F2) U !(F1 || F2)) n'ont pas de parent
                nonF2.setParent(eNonF1UNonF2);
                nonF1VF2.setParent(eNonF1UNonF2);
                // on a la forme !(F1 || F2) qui normalment doit se presenter sous la forme !F1 && !F2
                //donc je relance la reecriture
                eNonF1UNonF2.setF2(eNonF1UNonF2.getF2().reWrite());
                //La forme final est une disjonction qui a pour neud gauche la negation de E ( (!F2) U !(F1 || F2))
                Negation gauche = new Negation();
                gauche.setF(eNonF1UNonF2);
                eNonF1UNonF2.setParent(gauche);
                //creation du neud final qu'on va retourner
                //ce neud a la forme general F1 V F2
                // F1=gauche et F2=A ◊ F2 = droite
                QF1opF2 ret = new QF1opF2(this.getParent());
                ret.setQ(null);
                ret.setOp(new Conjunction());
                ret.setF1(gauche);
                gauche.setParent(ret);
                //creation de la partie droite de la formule final
                QopF droite = new QopF(ret, new ForAll(), new Diamond(), this.getF2().reWrite());
                droite.getF().setParent(droite);
                ret.setF2(droite);
                return ret;
            }
            //E(F1 U F2) >> F2 || (F1 && (E◯[E(F1 U F2)]))
            if (q instanceof  Every){
                //[E(F1 U F2)]
                Formula f2_clone1 = this.getF2().reWrite();
                Formula f1_clone1 = this.getF1().reWrite();
                QF1opF2 this_copy = new QF1opF2(new Every(), new Until(), f1_clone1, f2_clone1);
                this_copy.getF1().setParent(this_copy);
                this_copy.getF2().setParent(this_copy);
                //(E◯[E(F1 U F2)])
                QopF eRond = new QopF(new Every(), new Ring(), this_copy );
                this_copy.setParent(eRond);
                //(F1 && (E◯[E(F1 U F2)]))
                Formula f1_clone2 = f1.reWrite();
                QF1opF2 f1_et_eRond = new QF1opF2(null, new Conjunction(), f1_clone2, eRond);
                f1_et_eRond.getF1().setParent(f1_et_eRond);
                f1_et_eRond.getF2().setParent(f1_et_eRond);
                //F2 || (F1 && (E◯[E(F1 U F2)]))
                QF1opF2 ret = new QF1opF2(this.getParent(), null, new Disjunction(), this.getF2().reWrite(), f1_et_eRond);
                this.getF1().setParent(ret);
                this.getF2().setParent(ret);

                return ret;
            }
        }
        //for all the rest stay the same
        this.setF2(this.getF2().reWrite());
        this.setF1(this.getF1().reWrite());
        return this;
    }


    public QF1opF2() {
        super();
    }

    public QF1opF2(Formula parent) {
        super(parent);
    }

    public QF1opF2(Formula parent, Interpretation i, ArrayList<State> marks) {
        super(parent, i, marks);
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
        if(this.f1 != null)this.f1.setI(i);
        if(this.f2 != null)this.f2.setI(i);
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
        if(parent.getI() != null && this.getI() == null){
            this.setI(parent.getI());
        }
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QF1opF2)) return false;
        QF1opF2 qf1opF2 = (QF1opF2) o;
        return getQ().equals(qf1opF2.getQ()) &&
                getOp().equals(qf1opF2.getOp()) &&
                getF1().equals(qf1opF2.getF1()) &&
                getF2().equals(qf1opF2.getF2());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQ(), getOp(), getF1(), getF2());
    }

    @Override
    public boolean hasParent() {
        return super.hasParent();
    }

    public QF1opF2(Quantificator q, Operator op, Formula f1, Formula f2) {
        super();
        this.q = q;
        this.op = op;
        this.f1 = f1;
        this.f2 = f2;
        f1.setParent(this);
        f2.setParent(this);
    }

    public QF1opF2(Formula parent, Quantificator q, Operator op, Formula f1, Formula f2) {
        super(parent);
        this.q = q;
        this.op = op;
        this.f1 = f1;
        this.f2 = f2;
        if(parent != null && parent.getI() !=null && f1.getI() == null && f2.getI() == null){
            this.getF1().setI(parent.getI());
            this.getF2().setI(parent.getI());
        }
        f1.setParent(this);
        f2.setParent(this);
    }

    public QF1opF2(Formula parent, Interpretation i, ArrayList<State> marks, Quantificator q, Operator op, Formula f1, Formula f2) {
        super(parent, i, marks);
        this.q = q;
        this.op = op;
        this.f1 = f1;
        this.f2 = f2;
        this.f1.setI(i);
        this.f2.setI(i);
        f1.setParent(this);
        f2.setParent(this);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public Formula toNegation() {
        if(! this.getF1().isInNegationForm())
            this.setF1(this.getF1().toNegation());
        if(! this.getF2().isInNegationForm())
            this.setF2(this.getF2().toNegation());
        return this;
    }

    @Override
    public boolean isInNegationForm() {
        return super.isInNegationForm();
    }

    @Override
    public boolean isTheSameAsMe(Formula f) {
        if(f instanceof QF1opF2)
        {
            return this.toString().equals(f.toString());
        }
        return false;
    }


}
