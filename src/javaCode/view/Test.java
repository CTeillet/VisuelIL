package javaCode.view;

public class Test {
    private Controller c;
    public Test(Controller c) {
        this.c = c;
    }

    boolean verifLivre(){
        return !(c.getLv().getLv() == null);
    }

    public boolean verifSections(int nb) {
        return c.getLv().getAllSections().size() < nb;
    }
}
