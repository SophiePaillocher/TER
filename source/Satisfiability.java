import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;

public class Satisfiability {
    private Interpretation interpretation;
    private HashMap<String, ArrayList<Formula>> marks;

    public Satisfiability(Interpretation interpretation){
        this.interpretation=interpretation;
        marks = new HashMap<>();
    }

    public Interpretation getInterpretation() {
        return interpretation;
    }

    public void setInterpretation(Interpretation interpretation) {
        this.interpretation = interpretation;
    }

    public HashMap<String, ArrayList<Formula>> getMarks() {
        return marks;
    }

    public void setMarks(HashMap<String, ArrayList<Formula>> marks) {
        this.marks = marks;
    }

    public ArrayList<Formula> getMarksOfVertex(String vertex){
        return marks.get(vertex);
    }

    public void addMarkAtVertex(String vertex, Formula formula){
        ArrayList<Formula> temp = new ArrayList<Formula>();
        if (!marks.containsKey(vertex)) {
            temp.add(formula);
            marks.put(vertex, temp);
        }else{
            temp = marks.get(vertex);
            temp.add(formula);
            marks.replace(vertex, temp);
        }
    }

    public void markSat(Atom atom){
         for (String vertex : interpretation.getVertices()) {
             if (interpretation.getLabelsOfVertex(vertex).contains(atom.toString())){
                 addMarkAtVertex(vertex,atom);
             }
         }
    }

    public void markSat(Negation formula){
          for (String vertex : interpretation.getVertices()) {
              if (!marks.get(vertex).contains(formula.getChild())){
                   addMarkAtVertex(vertex, formula);
               }
          }
    }

    public void markSat(Conjonction formula){
          for (String vertex : interpretation.getVertices()) {
               if ((marks.get(vertex).contains(formula.getChild1())) && (marks.get(vertex).contains(formula.getChild2())) ) {
                    addMarkAtVertex(vertex, formula);
               }
          }
    }

    public void markSat(ERond formula){
          for (String vertex : interpretation.getVertices()) {
              for (String nextVertex : interpretation.getSuccesseurs(vertex)) {
                  if ((marks.get(nextVertex).contains(formula.getChild()))){
                     addMarkAtVertex(vertex, formula);
                     break;
                  }
              }
          }
    }

    public void markSat(EU formula){
           for (String vertex : interpretation.getVertices()) {
                if ((marks.get(vertex).contains(formula.getChild2()))) {
                    addMarkAtVertex(vertex, formula);
                }
           }
        boolean newMark =true;
        while (newMark){
            HashMap<String, ArrayList<Formula>> oldMarks = marks;
            for (String vertex : interpretation.getVertices()) {
                if (interpretation.getLabelsOfVertex(vertex).contains(formula.getChild1().toString())) {
                    boolean marked = true;
                    for (String nextVertex : interpretation.getSuccesseurs(vertex)) {
                        if (!(marks.get(nextVertex).contains(formula.getChild()))) {
                            marked = false;
                        }
                    }
                    if (marked) {
                        addMarkAtVertex(vertex, formula);
                    }
                }
            }
            if (marks.equals(oldMarks)){
                newMark= false;
            }
        }
    }

    public void markSat(ALosange formula){
           for (String vertex : interpretation.getVertices()) {
                if ((marks.get(vertex).contains(formula.getChild()))) {
                    addMarkAtVertex(vertex, formula);
                }
           }
        boolean newMark =true;
        while (newMark) {
            HashMap<String, ArrayList<Formula>> oldMarks = marks;
            for (String vertex : interpretation.getVertices()) {
                boolean marked = true;
                for (String nextVertex : interpretation.getSuccesseurs(vertex)) {
                    if (!(marks.get(nextVertex).contains(formula.getChild()))) {
                        marked = false;
                    }
                }
                if (marked) {
                    addMarkAtVertex(vertex, formula);
                }
            }
            if (marks.equals(oldMarks)) {
                newMark = false;
            }
        }
    }

    // A refaire en utilisant accept() car ça brule les yeux
    public void markSat(Formula formula){
        switch (formula.getClass().getName()){
            case "Atom" : markSat((Atom) formula);
            break;
            case "Negation" : markSat(((Negation) formula));
            break;
            case "ERond" : markSat((ERond) formula);
            break;
            case "ALosange": markSat((ALosange) formula);
            break;
            case "EU": markSat((EU) formula);
            break;
            case "Conjonction" : markSat((Conjonction) formula);
            break;
        }
    }

    public boolean sat(Formula formula){
        Formula f = formula.rewrite();
        for (Atom atom : f.getAtoms()){
            markSat(atom);
        }
        for (Atom atom : f.getAtoms()){
            Formula parent = atom.getParent();
            while (parent != null){
                parent = parent.getParent();
                markSat(parent);
            }
        }
        if (marks.containsValue(f)){
            return true;
        }else{
            return false;
        }
    }
}
