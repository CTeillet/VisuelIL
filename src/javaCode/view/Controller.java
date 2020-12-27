package javaCode.view;

import javaCode.data.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.*;
import javafx.util.Pair;

import java.util.Optional;
import java.util.Set;

public class Controller {
    private final GestionnaireLivre lv = new GestionnaireLivre();
    private final Graphe g = new Graphe(lv);
    private final CustomDialog cd = new CustomDialog(this);
    private final int TAILLETEXTE = 18;
    private Set<Section> inatteignables;

    @SuppressWarnings("rawtypes")
    @FXML
    private TreeView tree;
    @FXML
    private TextFlow textFlow;
    @FXML
    private ImageView imView;
    @FXML
    private Label labTexte;
    @FXML
    private Label labStatus;
    @FXML
    private Button toolBut;



    protected GestionnaireLivre getLv() {
        return lv;
    }

    @FXML
    private void createEnchainement(){
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
                    refreshLabelStatus();
                });
            }

        }

    }

    @FXML
    private void createLivre(){
        if(verifLivre()){
            Optional<ButtonType> result;
            result = cd.confirmationDialog("Etes vous sures de vouloir écraser le livre existant ?")
                    .showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                Optional<String> res = cd.customLivre().showAndWait();
                res.ifPresent(s ->{
                    lv.createNewLivre(s);
                    createTreeView();
                    refreshLabelStatus();
                });

            }
        }else{
            Optional<String> res = cd.customLivre().showAndWait();
            res.ifPresent(s -> {
                lv.createNewLivre(s);
                createTreeView();
                refreshLabelStatus();
            });
        }
    }

    @FXML
    private void createSection(){
        if(!verifLivre()){
            cd.errorDialog("Pas de Livre crée").showAndWait();
        }else {
            Optional<Pair<Integer, String>> result = cd.customSection().showAndWait();
            result.ifPresent(integerStringPair -> lv.addSection(integerStringPair.getKey(),
                                                                integerStringPair.getValue()));
            createTreeView();
            refreshLabelStatus();
        }
    }
    @FXML
    private void createObjet() {
        if(!verifLivre()){
            cd.errorDialog("Pas de Livre crée").showAndWait();
        }else {
            if(verifSections(1)){
                cd.errorDialog("Une section est nécessaire pour créer un Objet").showAndWait();
            }else {
                Optional<Pair<String, Section>> result = cd.customObjet().showAndWait();
                result.ifPresent(stringSectionPair -> lv.addObjet(stringSectionPair.getKey(), stringSectionPair.getValue()));
                createTreeView();
                refreshLabelStatus();
            }
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
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
        tree.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) -> {
            if(t1!=null){
                Object temp = ((TreeItem)t1).getValue();
                if(temp instanceof Enchainement){
                    Enchainement ench = (Enchainement) temp;
                    labTexte.setText(ench.toString());
                    //Creation des Textes
                    Text tx = new Text("Section de départ : ");
                    tx.setFont(Font.font("Helvetica", FontWeight.BOLD, TAILLETEXTE));
                    Text tx1 = new Text(ench.getDepart()+"\n");
                    tx1.setFont(Font.font("Helvetica", TAILLETEXTE));
                    Text tx2 = new Text("Section d'arrivée : ");
                    tx2.setFont(Font.font("Helvetica", FontWeight.BOLD, TAILLETEXTE));
                    Text tx3 = new Text(ench.getArrive()+"\n");
                    tx3.setFont(Font.font("Helvetica", TAILLETEXTE));
                    Text tx4 = new Text("Condition : \n");
                    tx4.setFont(Font.font("Helvetica", FontWeight.BOLD, TAILLETEXTE));
                    if(ench.getCondition().size()==0){
                        Text tx5 = new Text("Aucune\n");
                        tx5.setFont(Font.font("Helvetica", TAILLETEXTE));

                        //Ajout des Textes au TextFlow
                        textFlow.getChildren().setAll(tx, tx1, tx2, tx3, tx4, tx5);
                    }else{
                        textFlow.getChildren().setAll(tx, tx1, tx2, tx3, tx4);
                        for(Objet obj : ench.getCondition()){
                            Text txx = new Text("\t-"+obj+"\n");
                            txx.setFont(Font.font("Arial", FontPosture.ITALIC, TAILLETEXTE));
                            textFlow.getChildren().add(txx);
                        }
                    }

                }else{
                    if(temp instanceof  Section){
                        Section sect = (Section) temp;
                        labTexte.setText(sect.toString());
                        int nbObjetsSect = sect.getObjets().size();
                        int nbEnchainement = sect.getEnchainements().size();
                        Text tx = new Text("Numéro : ");
                        tx.setFont(Font.font("Helvetica", FontWeight.BOLD, TAILLETEXTE));
                        Text tx1 = new Text(sect.getNumero()+"\n");
                        tx1.setFont(Font.font("Helvetica", TAILLETEXTE));
                        Text tx2 = new Text("Texte : ");
                        tx2.setFont(Font.font("Helvetica", FontWeight.BOLD, TAILLETEXTE));
                        Text tx3 = new Text(sect.getTexte()+"\n");
                        tx3.setFont(Font.font("Helvetica", TAILLETEXTE));
                        Text tx4 = new Text("Nombre d'objets dans la sections : ");
                        tx4.setFont(Font.font("Helvetica", FontWeight.BOLD, TAILLETEXTE));
                        Text tx5 = new Text(nbObjetsSect+"");
                        tx5.setFont(Font.font("Helvetica", TAILLETEXTE));
                        Text tx6 = new Text("Nombre d'enchainements dans la sections : ");
                        tx6.setFont(Font.font("Helvetica", FontWeight.BOLD, TAILLETEXTE));
                        Text tx7 = new Text(nbEnchainement+"");
                        tx7.setFont(Font.font("Helvetica", TAILLETEXTE));
                        textFlow.getChildren().setAll(tx, tx1, tx2, tx3, tx4, tx5, tx7);
                    }else{
                        if(temp instanceof Livre){
                            Livre lv = (Livre) temp;
                            labTexte.setText(lv.toString());
                            int nbSections = 0;
                            if(lv.getDepart()!=null) nbSections++;
                            nbSections+=lv.getSections().size();
                            Text tx = new Text("Titre : ");
                            tx.setFont(Font.font("Helvetica", FontWeight.BOLD, TAILLETEXTE));
                            Text tx1 = new Text(lv.getTitre()+"\n");
                            tx1.setFont(Font.font("Helvetica", TAILLETEXTE));
                            Text tx2 = new Text("Nombre de Sections : ");
                            tx.setFont(Font.font("Helvetica", FontWeight.BOLD, TAILLETEXTE));
                            Text tx3 = new Text(nbSections+"\n");
                            textFlow.getChildren().setAll(tx, tx1, tx2, tx3);
                        }else{
                            if(temp instanceof String){
                                String str = (String) temp;
                                labTexte.setText(str);
                                Text tx = new Text(str);
                                tx.setFont(Font.font("Helvetica", FontWeight.BOLD, TAILLETEXTE));
                                textFlow.getChildren().setAll(tx);
                            }else{
                                if(temp instanceof Objet){
                                    Objet obj = (Objet) temp;
                                    labTexte.setText(obj.toString());
                                    Text tx = new Text("Nom : ");
                                    tx.setFont(Font.font("Helvetica", FontWeight.BOLD, TAILLETEXTE));
                                    Text tx1 = new Text(obj.getNom()+"\n");
                                    tx1.setFont(Font.font("Helvetica", TAILLETEXTE));
                                    Text tx2 = new Text("Section : ");
                                    tx2.setFont(Font.font("Helvetica", FontWeight.BOLD, TAILLETEXTE));
                                    Text tx3 = new Text(obj.getSection()+"\n");
                                    tx3.setFont(Font.font("Helvetica", TAILLETEXTE));
                                    textFlow.getChildren().setAll(tx, tx1, tx2, tx3);
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
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

    public void refreshLabelStatus(){
        inatteignables = g.trouverSectionsInatteignables();
        if(inatteignables.size()==0){
            labStatus.setText("Aucun problèmes");
            toolBut.setDisable(true);
            toolBut.setVisible(false);
        }else{
            labStatus.setText("Il y a " + inatteignables.size() + " sections inatteignables");
            toolBut.setDisable(false);
            toolBut.setVisible(true);
        }
    }

    @FXML
    private void toolButAction(){
        if(inatteignables.size()>0){
            StringBuilder s = new StringBuilder();
            for(Section sect : inatteignables){
                s.append("\t- ").append(sect).append("\n");
            }
            cd.problemesGraphe(s.toString()).showAndWait();
        }
    }



}
