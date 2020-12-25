package javaCode.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Section {
    private String texte;
    private int numero;
    private List<Enchainement> enchainements = new ArrayList<>();
    private List<Objet> objets = new ArrayList<>();

    public Section(String texte, int numero) {
        this.texte = texte;
        this.numero = numero;
    }

    public boolean add(Enchainement enchainement) {
        return enchainements.add(enchainement);
    }

    public boolean add(Section depart, Section arrive, List<Objet> objs) {
        Enchainement temp = new Enchainement(depart, arrive, objs);
        return enchainements.add(temp);
    }

    public boolean add(Section depart, Section arrive) {
        Enchainement temp = new Enchainement(depart, arrive);
        return enchainements.add(temp);
    }

    public boolean add(Objet... objs){
        return objets.addAll(Arrays.asList(objs));
    }

    public String getTexte() {
        return texte;
    }

    public int getNumero() {
        return numero;
    }

    public List<Enchainement> getEnchainements() {
        return enchainements;
    }

    public List<Objet> getObjets() {
        return objets;
    }

    @Override
    public String toString() {
        return "Section " + numero ;
    }
}
