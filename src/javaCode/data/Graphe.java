package javaCode.data;

import java.util.HashSet;
import java.util.Set;

public class Graphe {
    private GestionnaireLivre lv;

    public Graphe(GestionnaireLivre lv) {
        this.lv = lv;
    }

    public Set<Section> trouverSectionsInatteignables(){
        HashSet<Section> sections = new HashSet<>();
        sections.add(new Section( "tcvuywxgiusw<çf", 0));
        return sections;
    }
}
