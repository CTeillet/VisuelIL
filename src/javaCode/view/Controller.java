package javaCode.view;

import javaCode.data.Enchainement;
import javaCode.data.GestionnaireLivre;
import javaCode.data.Objet;
import javaCode.data.Section;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextFlow;
import javafx.util.Pair;

import java.util.Optional;

public class Controller {
    private GestionnaireLivre lv = new GestionnaireLivre();
    private CustomDialog cd = new CustomDialog(this);

    @SuppressWarnings("rawtypes")
    @FXML
    private TreeView tree;
    @FXML
    private TextFlow textFlow;
    @FXML
    private ImageView imView;



    protected GestionnaireLivre getLv() {
        return lv;
    }

    @FXML
    private void createEnchainement(ActionEvent actionEvent){
        if(!verifLivre()){
            cd.errorDialog("Pas de Livre crée").showAndWait();
        }else{
            if(verifSections(2)){
                cd.errorDialog("Il faut au moins deux sections pour créer un enchainement").showAndWait();
            }else{
                Optional<Result3> result = cd.customEnchainement().showAndWait();
                result.ifPresent(result3 -> {
                    lv.addEnchainement(result3.getFirst(), result3.getSnd(), result3.getTrd());
                    createTreeView();
                });
            }

        }

    }

    @FXML
    private void createLivre(ActionEvent actionEvent){
        if(verifLivre()){
            Optional<ButtonType> result;
            result = cd.confirmationDialog("Etes vous sures de vouloir écraser le livre existant ?")
                    .showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                Optional<String> res = cd.customLivre().showAndWait();
                res.ifPresent(s ->{
                    lv.createNewLivre(s);
                    createTreeView();
                });

            }
        }else{
            Optional<String> res = cd.customLivre().showAndWait();
            res.ifPresent(s -> {
                lv.createNewLivre(s);
                createTreeView();
            });

        }
    }

    @FXML
    private void createSection(ActionEvent actionEvent){
        if(!verifLivre()){
            cd.errorDialog("Pas de Livre crée").showAndWait();
        }else {
            Optional<Pair<Integer, String>> result = cd.customSection().showAndWait();
            result.ifPresent(integerStringPair -> lv.addSection(integerStringPair.getKey(), integerStringPair.getValue()));
            createTreeView();
        }
    }
    @FXML
    private void createObjet(ActionEvent actionEvent) {
        if(!verifLivre()){
            cd.errorDialog("Pas de Livre crée").showAndWait();
        }else {
            if(verifSections(1)){
                cd.errorDialog("Une section est nécessaire pour créer un Objet").showAndWait();
            }else {
                Optional<Pair<String, Section>> result = cd.customObjet().showAndWait();
                result.ifPresent(stringSectionPair -> lv.addObjet(stringSectionPair.getKey(), stringSectionPair.getValue()));
                createTreeView();
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private void createTreeView(){
        //Racine de l'arbre
        TreeItem root;
        root = new TreeItem(getLv().getLv());

        //Section des Sections :
        for(Section section : getLv().getAllSections()){
            setTreeItem(root, section);
        }

        //Ajout de la racine au TreeView
        tree.setRoot(root);
    }


    private void setTreeItem(TreeItem treeItem, Section section){
        TreeItem sect = new TreeItem(section);


        //Ajouts des enchainements à l'arbre
        if(section.getEnchainements().size()>0) {
            TreeItem ench = new TreeItem("Enchainements");
            for (Enchainement enchainement : section.getEnchainements()) {
                TreeItem enchT = new TreeItem(enchainement);
                ench.getChildren().add(enchT);
            }
            sect.getChildren().add(ench);
        }

        if(section.getObjets().size()>0) {
            TreeItem objs = new TreeItem("Objets");
            for (Objet objet : section.getObjets()) {
                TreeItem objT = new TreeItem(objet);
                objs.getChildren().add(objT);
            }
            sect.getChildren().add(objs);
        }
        treeItem.getChildren().add(sect);
    }

    private boolean verifLivre(){
        return !(getLv().getLv() == null);
    }

    private boolean verifSections(int nb) {
        return getLv().getAllSections().size() < nb;
    }





}
