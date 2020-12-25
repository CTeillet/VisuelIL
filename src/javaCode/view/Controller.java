package javaCode.view;

import javaCode.data.GestionnaireLivre;
import javaCode.data.Section;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.util.Pair;

import java.util.Optional;

public class Controller {
    private GestionnaireLivre lv = new GestionnaireLivre();
    private CustomDialog cd = new CustomDialog(this);
    private Test t = new Test(this);



    protected GestionnaireLivre getLv() {
        return lv;
    }

    @FXML
    private void createEnchainement(ActionEvent actionEvent){
        if(!t.verifLivre()){
            cd.errorDialog("Pas de Livre crée").showAndWait();
        }else{
            if(t.verifSections(2)){
                cd.errorDialog("Il faut au moins deux sections pour créer un enchainement").showAndWait();
            }else{
                Optional<Result3> result = cd.customEnchainement().showAndWait();
                result.ifPresent(result3 -> lv.addEnchainement(result3.getFirst(), result3.getSnd(), result3.getTrd()));
            }

        }

    }

    @FXML
    private void createLivre(ActionEvent actionEvent){
        if(t.verifLivre()){
            Optional<ButtonType> result;
            result = cd.confirmationDialog("Etes vous sures de vouloir écraser le livre existant ?")
                    .showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                Optional<String> res = cd.customLivre().showAndWait();
                res.ifPresent(s -> lv.createNewLivre(s));
            }
        }else{
            Optional<String> res = cd.customLivre().showAndWait();
            res.ifPresent(s -> lv.createNewLivre(s));
        }
    }

    @FXML
    private void createSection(ActionEvent actionEvent){
        if(!t.verifLivre()){
            cd.errorDialog("Pas de Livre crée").showAndWait();
        }else {
            Optional<Pair<Integer, String>> result = cd.customSection().showAndWait();
            result.ifPresent(integerStringPair -> lv.addSection(integerStringPair.getKey(), integerStringPair.getValue()));
        }
    }
    @FXML
    private void createObjet(ActionEvent actionEvent) {
        if(!t.verifLivre()){
            cd.errorDialog("Pas de Livre crée").showAndWait();
        }else {
            if(t.verifSections(1)){
                cd.errorDialog("Une section est nécessaire pour créer un Objet").showAndWait();
            }else {
                Optional<Pair<String, Section>> result = cd.customObjet().showAndWait();
                result.ifPresent(stringSectionPair -> lv.addObjet(stringSectionPair.getKey(), stringSectionPair.getValue()));
            }
        }
    }


}
