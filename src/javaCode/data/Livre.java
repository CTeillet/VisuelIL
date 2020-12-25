package javaCode.data;

import java.util.ArrayList;
import java.util.List;

public class Livre {
    private Section depart;
    private List<Section> sections = new ArrayList<>();
    private String titre;

    public Livre(String titre) {
        this.titre = titre;
    }

    public boolean add(Section section) {
        if(section.getNumero()==0){
            depart = section;
            return true;
        }else{
            return sections.add(section);
        }
    }

    public List<Section> getSections() {
        return sections;
    }

    public Section getDepart() {
        return depart;
    }

    public String getTitre() {
        return titre;
    }

    @Override
    public String toString() {
        return "Livre : " + titre;
    }
}
