package javaCode.data;

import java.util.ArrayList;
import java.util.List;

public class Enchainement {
    private List<Objet> condition = new ArrayList<>();
    private Section depart;
    private Section arrive;

    public Enchainement(Section depart, Section arrive) {
        this.depart = depart;
        this.arrive = arrive;
    }

    public Enchainement(Section depart, Section arrive, List<Objet> objs) {
        this(depart, arrive);
        condition.addAll(objs);
    }

    public Section getArrive() {
        return arrive;
    }

    public Section getDepart() {
        return depart;
    }

    public List<Objet> getCondition() {
        return condition;
    }

    @Override
    public String toString() {
        //if(condition.size()>0) res.append(", ")
        return "Enchainement " + depart + " - " + arrive;
    }
}
