import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;

public class main {
    public static void main(String[] args){
        Formula p = new Atom("p");
        Formula f = new ACarre(p);
        Formula F = new ELosange(f);
        System.out.println(F.asString());
        Formula g = F.rewrite();
        System.out.println(g.asString());

        ArrayList<String> vertices = new ArrayList<String>();
        vertices.add("e0");
        vertices.add("e1");
        vertices.add("e2");
        vertices.add("e3");
        vertices.add("e4");
        HashMap<String, ArrayList<String>> labels = new HashMap<>();
        ArrayList<String> l0 = new ArrayList();
        l0.add("True");
        labels.put("e0",l0);
        ArrayList<String> l1 = new ArrayList<>();
        l1.add("True");
        l1.add("p");
        l1.add("q");
        labels.put("e1",l1);
        ArrayList<String> l2 = new ArrayList<>();
        l2.add("True");
        l2.add("p");
        l2.add("q");
        labels.put("e2",l2);
        ArrayList<String> l3 = new ArrayList<>();
        l3.add("True");
        l3.add("p");
        l3.add("r");
        labels.put("e3",l3);
        ArrayList<String> l4 = new ArrayList<>();
        l4.add("True");
        l4.add("p");
        labels.put("e4", l4);
        HashMap<String, ArrayList<String>> relations = new HashMap<>();
        ArrayList<String> r0 = new ArrayList<>();
        r0.add("e1");
        ArrayList<String>r1 = new ArrayList<>();
        r1.add("e1");
        r1.add("e2");
        ArrayList<String> r2 = new ArrayList<>();
        r2.add("e2");
        r2.add("e1");
        ArrayList<String>r3 = new ArrayList<>();
        r3.add("e4");
        ArrayList<String>r4 = new ArrayList<>();
        r4.add("e4");
        relations.put("e0",r0);
        relations.put("e1",r1);
        relations.put("e2",r2);
        relations.put("e3",r3);
        relations.put("e4",r4);

        Interpretation interpretation = new Interpretation();
        interpretation.setVertices(vertices);
        interpretation.setLabels(labels);
        interpretation.setRelations(relations);
        System.out.println(interpretation.getLabels());

        Satisfiability satisfiability = new Satisfiability(interpretation);
        satisfiability.markSat(p);
        System.out.println(satisfiability.getMarks());



    }
}
