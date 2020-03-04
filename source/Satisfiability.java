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
}
