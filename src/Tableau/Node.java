package Tableau;
import logic.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

public class Node {
    private int number;
    private ArrayList<Formula> to_develop;
    private ArrayList<Formula> marks;
    private ArrayList<Node> following;
    private Node previous;
    private Formula chosenOne;

    public Node()
    {
        this.to_develop = new ArrayList<>();
        this.marks = new ArrayList<>();
        this.following = new ArrayList<>();
    }
    public Node(ArrayList<Formula> marks, ArrayList<Formula> to_develop)
    {
        this.to_develop = new ArrayList<>();
        this.marks = new ArrayList<>();
        this.following = new ArrayList<>();
        this.marks.addAll(marks);
        this.to_develop.addAll(to_develop);
    }
    public Node(ArrayList<Formula> marks, ArrayList<Formula> to_develop, Node previous)
    {
        this.to_develop = new ArrayList<>();
        this.marks = new ArrayList<>();
        this.following = new ArrayList<>();
        this.marks.addAll(marks);
        this.to_develop.addAll(to_develop);
        this.previous = previous;
    }
    public Node(int number, ArrayList<Formula> marks, ArrayList<Formula> to_develop, ArrayList<Node> following, Node previous)
    {
        this.to_develop = new ArrayList<>();
        this.marks = new ArrayList<>();
        this.following = new ArrayList<>();
        this.number = number;
        this.marks.addAll(marks);
        this.to_develop.addAll(to_develop);
        this.following.addAll(following);
        this.previous = previous;
    }

    public ArrayList<Node> getFollowing() {
        return following;
    }

    public ArrayList<Formula> getMarks() {
        return marks;
    }

    @Override
    public String toString() {
        String me = Integer.toString(number) + " : {";
        if (!(marks.size() == 0)) {
            for (Formula i : marks) {
                me = me + i.toString() + ", ";
            }
        }
        for(Formula i : to_develop)
        {
            me = me + i.toString() + ", ";
        }
        return me+ "}\n" ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node) o;
        return Objects.equals(to_develop, node.to_develop) &&
                Objects.equals(getMarks(), node.getMarks());
    }

    @Override
    public int hashCode() {
        return Objects.hash(to_develop, getMarks());
    }

    public void setTo_develop(ArrayList<Formula> to_develop) {
        this.to_develop = to_develop;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setMarks(ArrayList<Formula> marks) {
        this.marks = marks;
    }

    public void setFollowing(ArrayList<Node> following) {
        this.following = following;
    }

    public Node getPrevious() {
        return previous;
    }

    public int getNumber() {
        return number;
    }

    public ArrayList<Formula> getTo_develop() {
        return to_develop;
    }

    public void setChosenOne(Formula chosenOne) {
        this.chosenOne = chosenOne;
    }

    public Formula getChosenOne() {
        return chosenOne;
    }
}
