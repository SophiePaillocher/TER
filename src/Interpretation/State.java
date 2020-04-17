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
        this.labels = new ArrayList<>();
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

    public ArrayList<String> getLabels() {
        return labels;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addPredecessor(State p) {
        this.predecessors.add(p);
    }

    public void addPredecessors(ArrayList<State> p) {
        this.predecessors.addAll(p);
    }

    public void addSuccessor(State s) {
        this.successors.add(s);
        s.addPredecessor(this);
    }

    public void addSuccessors(ArrayList<State> successors) {
        this.successors.addAll(successors);
        for (State s : successors){
            s.addPredecessor(this);
        }
    }

    public void addLabel(String label){ labels.add(label); marks.add(label);}

    public void addLabels(ArrayList<String> labels){
        labels.addAll(labels);
        for (String label : labels){
             marks.add(label);
        }
    }

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
