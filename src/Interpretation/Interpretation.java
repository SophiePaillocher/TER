package Interpretation;

import logic.*;

import java.util.ArrayList;
import java.util.Objects;

public class Interpretation {

    private ArrayList<State> model;
    private State root;

    public Interpretation() {
        model = new ArrayList<State>();

    }

    public Interpretation(ArrayList<State> model, State root) {
        this.model = model;
        this.root = root;
    }

    public Interpretation(State root) {
        this.root = root;
        this.model = new ArrayList<>();
    }

    public Interpretation(ArrayList<State> model) {
        this.model = model;
        this.root = null;
    }

    public void setRoot(State root) {
        this.root = root;
    }

    public State getRoot() {
        return root;
    }

    public ArrayList<State> getModel() {
        return model;
    }

    public void setModel(ArrayList<State> model) {
        this.model = model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Interpretation)) return false;
        Interpretation that = (Interpretation) o;
        return getModel().equals(that.getModel()) &&
                getRoot().equals(that.getRoot());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getModel(), getRoot());
    }

    //la je me preocupe pas si la formule est reecrite ou pas
    private ArrayList <State> sat_(Formula f){
        String testSub = f.toString(); //si jamais on a une formule compliquee on n'a pas a refaire le calcul.
        ArrayList<State> res_list = new ArrayList<>();
        if (f instanceof Negation)
        {
            //je lance sat avec la sous formule car si ce nest pas un atome la sous formule est traitée d'abor
            if(!(((Negation) f).getF() instanceof Atom))sat_(((Negation) f).getF());
            //je recupere la sous formule qui est nie et je vais tester par apport a l'opposite
            String testSub2 = ((Negation) f).getF().toString();
            for(int i = 0; i< model.size(); i++)
            {
                //si le neud n'est pas marquee par la sous formule je vais le marquer avec la negation
                //et apres je vais le mettre dans la liste de retour
                if(!model.get(i).isMarkedBy(testSub2))
                {
                    model.get(i).mark(testSub);
                    res_list.add(model.get(i));
                }
            }
            return res_list;
        }
        if(f instanceof QopF)
        {
            //vu qu'on fait sat du bas vers le haut on lance sat "d'embas d'abord
            res_list = this.sat_(((QopF) f).getF());
            
            //A carré F
            if(((QopF) f).getQ() instanceof ForAll && ((QopF) f).getOp() instanceof Square){
                ArrayList<State> temp = new ArrayList<>();
                String testSub2 = ((QopF) f).getF().toString();
                ArrayList<State> toReturn = new ArrayList<>();
                for(int i = 0; i< model.size(); i++){
                    if(!model.get(i).isMarkedBy(testSub2))
                        temp.add(model.get(i));
                    for (State s : res_list){
                        boolean intersectionEmpty = true;
                        for(State son : s.getSuccessors()){
                            if (temp.contains(son))
                                intersectionEmpty = false;
                        }
                        if (!intersectionEmpty){
                            s.mark(testSub);
                            toReturn.add(s);
                        }

                    }

                }
                return toReturn;
            }
            
            //E Rond F
            if(((QopF) f).getQ() instanceof Every && ((QopF) f).getOp() instanceof Ring)
            {
                res_list.clear();
                for(int i = 0; i < model.size(); i++)
                {
                    if(thereIsOneInTheFuture(((QopF) f).getF(), model.get(i)))
                    {
                        model.get(i).mark(f.toString());
                        res_list.add(model.get(i));
                    }
                }
                return res_list;
            }
            //E diamond F
            if(((QopF) f).getQ() instanceof  Every && ((QopF) f).getOp() instanceof Diamond){
                //on commence par marquer tout les sommets qui sont dans sat(F)
                for(int i = 0; i<res_list.size();i++) {
                    model.get(model.indexOf(res_list.get(i))).mark(testSub);
                }
                //répéter jusqu'a ne plus trouver de nouvel état ie taille(res_list) soit stable
                int sizeRes = 0;
                while (sizeRes !=res_list.size()){
                    sizeRes=res_list.size();
                    //marquer les prédécesseurs des états marqués 
                    for (State s : res_list){
                        for (State p : s.getPredecessors()){
                            if (! p.isMarkedBy(testSub)){
                                p.mark(testSub);
                                res_list.add(p);
                            }
                        }
                    } 
                }
                
            }
            //A Diamon F
            if(((QopF) f).getQ() instanceof  ForAll && ((QopF) f).getOp() instanceof Diamond)
            {
                //on comence par marquer tout les sommets qui sons dans sat(F)
                for(int i = 0; i<res_list.size();i++) {
                    model.get(model.indexOf(res_list.get(i))).mark(testSub);
                }
                //recoupere tout les etats qui ne sont pas marques par ADiamond
                ArrayList<State> notIt = new ArrayList<>();
                for(int i = 0; i < model.size(); i++)
                {
                    if(res_list.indexOf(model.get(i))== -1)
                        notIt.add(model.get(i));
                }
                //verifie que les etats non marquees peuve l'etre
                int taille = 0;
                while (notIt.size()!= 0 && taille != notIt.size())
                {
                    taille = notIt.size();
                    for(int i = 0; i< notIt.size(); i++)
                    {
                        if(allMyChildrenHaveIt(testSub,notIt.get(i))){
                            model.get(model.indexOf(notIt.get(i))).mark(testSub);
                            res_list.add(notIt.get(i));
                            notIt.remove(i);
                        }
                    }
                }
                return res_list;

            }
        }
        if(f instanceof QF1opF2)
        {
            ArrayList<State> satF1 = sat_(((QF1opF2) f).getF1());
            ArrayList<State> satF2 = sat_(((QF1opF2) f).getF2());
            // f1 ^ f2
            if(((QF1opF2) f).getOp() instanceof Conjunction && ((QF1opF2) f).getQ() == null)
            {
                //parcour de tout les sommets qui sat(F1)
                for(int i = 0; i<satF1.size();i ++)
                {
                    //si ce sommet existe dans sat(F2) alors on le marque
                    if(satF2.indexOf(satF1.get(i)) != -1)
                    {
                        res_list.add(satF1.get(i));
                        model.get(model.indexOf(satF1.get(i))).mark(testSub);
                    }
                }
            }
            // f1 V f2
            if(((QF1opF2) f).getOp() instanceof Disjunction && ((QF1opF2) f).getQ() == null)
            {
                assert satF2 != null;
                res_list.addAll(satF2);
                assert satF1 != null;
                for (State element : satF1) {
                    if(!satF2.contains(element))res_list.add(element);
                }

            }
             //A(F1 U F2)
            if(((QF1opF2) f).getOp() instanceof Until && ((QF1opF2) f).getQ() instanceof ForAll){
                //marquer tout les sommets dans sat(f2)
                for(int i =0; i<satF2.size(); i++)
                {
                    model.get(model.indexOf(satF2.get(i))).mark(testSub);
                    res_list.add(satF2.get(i));
                }
                int taille = 0;
                //répéter jusqu'à ne plus trouver de nouvel état
                while(taille != satF1.size()){
                    taille = satF1.size();
                    //pour tous les états dans Sat(F1), on vérifie que les successeurs sont tous marqués
                    for(int i = 0; i<satF2.size();i++){
                        boolean containAll = true;
                        for (State s :satF2.get(i).getSuccessors()){
                           if (!res_list.contains(s)) 
                               containAll = false;
                        }
                        //si tous les successeurs sont marqués, marquer l'état
                        if (containAll){
                            model.get(model.indexOf(satF1.get(i))).mark(testSub);
                            res_list.add(satF1.get(i));
                            satF1.remove(i);
                        }
                    }
                }
            }
            
            //E(F1 u F2)
            if(((QF1opF2) f).getOp() instanceof Until && ((QF1opF2) f).getQ() instanceof Every)
            {
                //marquer tout les sommets dans sat(f2)
                for(int i =0; i<satF2.size(); i++)
                {
                    model.get(model.indexOf(satF2.get(i))).mark(testSub);
                    res_list.add(satF2.get(i));
                }
                int taille = 0;
                while(taille != satF1.size())
                {
                    taille = satF1.size();
                    for(int i = 0; i<satF1.size();i++)
                    {
                        if(thereIsOneInTheFuture(f, satF1.get(i)))
                        {
                            model.get(model.indexOf(satF1.get(i))).mark(testSub);
                            res_list.add(satF1.get(i));
                            satF1.remove(i);
                        }
                    }
                }
            }
            return res_list;
        }
        //si on ne rentre pas dans un des if on est un atome et du coup on lance le suivant:
        //parcour de tout les neuds dans l'arbre
        for(int i = 0; i < model.size() ; i++ )
        {
            if(model.get(i).isMarkedBy(testSub))
                res_list.add(model.get(i));
        }
        return res_list;
    }

    //je vais faire une sorte de a* avec un map des sommets deja visit'es et
    private boolean thereIsOneInTheFuture(Formula f, State start)
    {
        ArrayList<State> toVisit = new ArrayList<>();
        ArrayList<String> visited = new ArrayList<>();
        State position = start;
        if(start.getSuccessors().size() != 0)
        {
            //come ça on n'a pas a lanse 50 fois la reecriture en tring qui peut etre longue
            String form = f.toString();
            toVisit.addAll(start.getSuccessors());
            while(toVisit.size() != 0)
            {
                //on recupere le premier de la liste
                position = toVisit.get(0);
                toVisit.remove(0);
                //on si jamais on l'a deja vu on va boucle tant qu'on n'a pas un sommet pas encore vu
                if(visited.indexOf(position.toString()) == -1){
                    while(toVisit.size() != 0  && visited.indexOf(position.toString()) == -1 ) {
                        position = toVisit.get(0);
                        toVisit.remove(0);
                    }
                }
                if(position.isMarkedBy(form))
                    return true;
                if(position.getSuccessors().size()!= 0)
                    toVisit.addAll(position.getSuccessors());
                visited.add(position.toString());
            }
        }
        return false;
    }
    private boolean allMyChildrenHaveIt(String f, State start)
    {
        //recolte des sucesseurs
        int taille = 0;
        ArrayList<State> toCheck = new ArrayList<>();
        ArrayList<State> sons = new ArrayList<>();
        toCheck.addAll(start.getSuccessors());
        while (taille != toCheck.size())
        {
            taille = toCheck.size();
            for(int i = 0; i< toCheck.size(); i++)
            {
                if(!toCheck.get(i).isMarkedBy(f))
                    return false;
                sons = toCheck.get(i).getSuccessors();
                while(sons.size() != 0)
                {
                    if(toCheck.indexOf(sons.get(0))== -1)
                    {
                        toCheck.add(sons.get(0));
                        sons.remove(0);
                    }
                }
            }
        }
        return true;
    }
}
