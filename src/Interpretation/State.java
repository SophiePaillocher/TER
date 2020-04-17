package Interpretation;

import logic.Formula;

import java.util.ArrayList;

public class State {
    private String name;
    private ArrayList<State> predecessors;
    private ArrayList<State> successors;
    private ArrayList<String> labels;
    private ArrayList<String> marks;



    public State(String name) {
        this.name = name;
        this.predecessors = new ArrayList<>();
        this.successors = new ArrayList<>();
        this.marks = new ArrayList<>();
    }

    public State(String name, ArrayList<State> predecessors, ArrayList<State> successors) {
        this.name = name;
        this.predecessors = predecessors;
        this.successors = successors;
        this.marks = new ArrayList<>();
    }

    public State(String name, ArrayList<State> predecessors, ArrayList<State> successors, ArrayList<String> labels) {
        this.name = name;
        this.predecessors = predecessors;
        this.successors = successors;
        this.labels = labels;
        this.marks=labels;
    }

    public State(String name, ArrayList<String> labels) {
        this.name = name;
        this.labels = labels;
        this.marks=labels;
        this.predecessors = new ArrayList<>();
        this.successors = new ArrayList<>();
    }

    public ArrayList<String> getMarks() {
        return marks;
    }

    public String getName() {
        return name;
    }

    public ArrayList<State> getPredecessors() {
        return predecessors;
    }

    public ArrayList<State> getSuccessors() {
        return successors;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPredecessors(ArrayList<State> predecessors) {
        this.predecessors = predecessors;
    }

    public void setSuccessors(ArrayList<State> successors) {
        this.successors = successors;
        for (State child : successors){
            child.addParent(this);
        }
    }

    public void addParent(State p) {
        this.predecessors.add(p);
    }

    public void addParents(ArrayList<State> p) {
        this.predecessors.addAll(p);
    }

    public void addSon(State s) {
        this.successors.add(s);
    }

    public void addSons(ArrayList<State> s) {
        this.successors.addAll(s);
    }

    public void setLabels(ArrayList<String> labels){ this.labels = labels; marks = labels;}

    public void setMarks(ArrayList<String> marks) {
        this.marks = marks;
    }

    public boolean hasMark(Formula f) {
        if (marks.indexOf(f) == -1) return false;
        return true;
    }

    public void mark(String f) {
        if (marks.indexOf(f) != -1) marks = new ArrayList<>();
        marks.add(f);
    }

    public void clearMarks() {
        marks.clear();
    }

    public void removeMark(Formula f) {
        marks.remove(f);
    }

    public State() {
        super();
        marks = new ArrayList<>();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }
    //I'm using the formula in its string form because the formula comparing method uses the parent o the formula to test
    //ans in this case I'm only interested in it's written form
    public boolean isMarkedBy(String formula){
        for (int i = 0; i < this.marks.size() ; i++)
        {
            if (this.marks.get(i).toString().equals(formula))return true;
        }
        return false;
    }
}
