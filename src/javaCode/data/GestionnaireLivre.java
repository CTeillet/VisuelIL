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

    public boolean addSection(Integer num, String texte){
        return lv.add(new Section(texte, num));
    }

    public boolean addEnchainement(int depart, int arrive, List<Objet> objs) {
        Section sDepart = getSectionNumber(depart);
        Section sArrive = getSectionNumber(arrive);
        if(sDepart == null || sArrive == null) return false;
        sDepart.add(new Enchainement(sDepart, sArrive, objs));
        return true;
    }

    public boolean addEnchainement(int depart, int arrive) {
        return addEnchainement(depart, arrive, null);
    }

    public boolean addEnchainement(Section depart, Section arrive, List<Objet> objs) {
        if(depart == null || arrive == null) return false;
        depart.add(new Enchainement(depart, arrive, objs));
        return true;
    }

    public boolean addEnchainement(Section depart, Section arrive) {
        return addEnchainement(depart, arrive, null);
    }

    public boolean addObjet(Objet obj){
        Section s = obj.getSection();
        return s.add(obj);
    }

    public boolean addObjet(String st, Section sect){
        Objet obj = new Objet(st, sect);
        return sect.add(obj);
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

    public List<Objet> getAllObject() {
        List<Objet> res = new ArrayList<>();
        if(getAllSections().size()!=0){
            for(Section sect : getAllSections()){
                res.addAll(sect.getObjets());
            }
        }
        return res;
    }

    public void createNewLivre(String titre){
        lv = new Livre(titre);
    }

    public Livre getLv() {
        return lv;
    }
}
