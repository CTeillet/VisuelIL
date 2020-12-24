package javaCode.data;

public class Objet {
    private String nom;
    private Section section;

    public Objet(String nom, Section sect) {
        this.nom = nom;
        this.section = sect;
    }

    public Section getSection() {
        return section;
    }

    public String getNom() {
        return nom;
    }
}
