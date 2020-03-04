import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Interpretation {
    private ArrayList<String> vertices;
    private HashMap<String, ArrayList<String>> labels;
    private HashMap<String, ArrayList<String>> relations;

    public void Interpretation () {
        vertices = new ArrayList<>();
        labels = new HashMap<>();
        relations = new HashMap<>();
    }
    public void Interpretation (ArrayList<String> verteces){
        this.vertices = verteces;
        this.labels = new HashMap<>();
        this.relations = new HashMap<>();
    }

    public void Interpretation (ArrayList<String> verteces, HashMap<String, ArrayList<String>> labels, HashMap<String, ArrayList<String>> relations){
        this.vertices = verteces;
        this.labels = labels;
        this.relations = relations;
    }

    public ArrayList<String> getVertices() {
        return vertices;
    }

    public HashMap<String, ArrayList<String>> getLabels() {
        return labels;
    }

    public HashMap<String, ArrayList<String>> getRelations() {
        return relations;
    }

    public void setLabels(HashMap<String, ArrayList<String>> labels) {
        this.labels = labels;
    }

    public void setRelations(HashMap<String, ArrayList<String>> relations) {
        this.relations = relations;
    }

    public void setVertices(ArrayList<String> vertices) {
        this.vertices = vertices;
    }

    public ArrayList<String> getSuccesseurs(String sommet){
        return relations.get(sommet);
    }

    public ArrayList<String> getLabelsOfVertex(String sommet){
        return labels.get(sommet);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interpretation that = (Interpretation) o;
        return vertices.equals(that.vertices) &&
                labels.equals(that.labels) &&
                relations.equals(that.relations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertices, labels, relations);
    }

    @Override
    public String toString() {
        return "Interpretation{" +
                "vertices=" + vertices +
                ", labels=" + labels +
                ", relations=" + relations +
                '}';
    }

}
