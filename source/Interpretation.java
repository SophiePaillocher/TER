import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Interpretation {
    private ArrayList<String> s ;
    private HashMap<String, ArrayList<String>> l;
    private HashMap<String, ArrayList<String>> r;

    public void Interpretation () {
        s = new ArrayList<>();
        l = new HashMap<>();
        r = new HashMap<>();
    }
    public void Interpretation (ArrayList<String> vertexes){
        this.s = vertexes;
        this.l = new HashMap<>();
        this.r = new HashMap<>();
    }

    public void Interpretation (ArrayList<String> vertexes, HashMap<String, ArrayList<String>> labels, HashMap<String, ArrayList<String>> relations){
        this.s = vertexes;
        this.l = labels;
        this.r = relations;
    }

    public ArrayList<String> getS() {
        return s;
    }

    public HashMap<String, ArrayList<String>> getL() {
        return l;
    }

    public HashMap<String, ArrayList<String>> getR() {
        return r;
    }

    public void setL(HashMap<String, ArrayList<String>> l) {
        this.l = l;
    }

    public void setR(HashMap<String, ArrayList<String>> r) {
        this.r = r;
    }

    public void setS(ArrayList<String> s) {
        this.s = s;
    }

    public ArrayList<String> getSuccesseurs(String sommet){
        return r.get(sommet);
    }

    public ArrayList<String> getMarquings(String sommet){
        return l.get(sommet);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interpretation that = (Interpretation) o;
        return s.equals(that.s) &&
                l.equals(that.l) &&
                r.equals(that.r);
    }

    @Override
    public int hashCode() {
        return Objects.hash(s, l, r);
    }

    @Override
    public String toString() {
        return "Interpretation{" +
                "s=" + s +
                ", l=" + l +
                ", r=" + r +
                '}';
    }

}
