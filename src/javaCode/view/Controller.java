package javaCode.view;

import javaCode.data.GestionnaireLivre;
import javaCode.data.Livre;

public class Controller {
    GestionnaireLivre lv = null;
    CustomDialog cd = new CustomDialog(this);

    protected GestionnaireLivre getLv() {
        return lv;
    }
}
