package javaCode.data;

import java.util.ArrayList;
import java.util.List;

public class GestionnaireLivre {
    private Livre lv = null;

    public GestionnaireLivre() {
    }

    public boolean addSection(Section section) {
        return lv.add(section);
    }

    public boolean addEnchainement(int depart, int arrive, Objet... objs) {
        Section sDepart = getSectionNumber(depart);
        Section sArrive = getSectionNumber(arrive);
        if(sDepart == null || sArrive == null) return false;
        sDepart.add(new Enchainement(sDepart, sArrive, objs ));
        return true;
    }

    public boolean addEnchainement(int depart, int arrive) {
        return addEnchainement(depart, arrive, null);
    }

    public boolean addObjet(Objet obj){
        Section s = obj.getSection();
        return s.add(obj);
    }

    private Section getSectionNumber(int i){
        if(i==0) {
            return lv.getDepart();
        }else{
            for(Section s : lv.getSections()){
                if(s.getNumero()==i){
                    return s;
                }
            }
            return null;
        }
    }


    public List<Section> getAllSections() {
        List<Section> res = new ArrayList<>();
        res.add(lv.getDepart());
        res.addAll(lv.getSections());
        return res;
    }
}
