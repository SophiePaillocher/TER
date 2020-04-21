package Tableau;
import Formula.*;

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
        if(this.chosenOne != null){
            return Integer.toString(number) + " : " + chosenOne.toString();}
        else
        {
            StringBuilder ret = new StringBuilder("{");
            for(int i = 0 ; i < this.marks.size(); i++)
            {
                if(i == 0){
                    ret.append(marks.get(0).toString());}
                else{
                ret.append(", ").append(marks.get(i).toString());}
            }
            for(int i = 0 ; i < this.to_develop.size(); i++)
            {
                if(i== 0 && marks.size() ==0) {
                    ret.append(to_develop.get(0).toString());
                }
                else {
                    ret.append(", ").append(to_develop.get(i).toString());
                }
            }
            return ret.append("}").toString();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node) o;
        boolean b = this.marks.size() != node.getMarks().size();
        if( b)
            return false;
        else
        {
            for (Formula f : node.getMarks()) {
                //if the formula doesn't exist in this nodes marks the they are not the same
                if (!marksContainsFormula(f.toString()))
                    return false;
            }
        }
        boolean a = this.to_develop.size() != node.getTo_develop().size();

        if(a){
            return false;
        }
        else{
            for(Formula f : node.getTo_develop())
            {
                //same as above if a formula in the objects to develop doesn't exist in my to develop the we are not the same
                if(!toDevelopContainsFormula(f.toString()))
                    return false;
            }
        }
        return true;
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

    public void addFollower (Node n)
    {
        this.following.add(n);
    }

    private boolean marksContainsFormula (String f)
    {
        for(Formula n : this.getMarks())
        {
            if(f.equals(n.toString()))
                return true;
        }
        return false;
    }
    private boolean toDevelopContainsFormula (String f)
    {
        for(Formula n : this.getTo_develop())
        {
            if(f.equals(n.toString()))
                return true;
        }
        return false;
    }
}
