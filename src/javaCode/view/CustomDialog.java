package javaCode.view;

import javaCode.data.Enchainement;
import javaCode.data.Objet;
import javaCode.data.Section;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import org.controlsfx.control.CheckComboBox;

import javax.naming.Binding;
import java.util.List;

public class CustomDialog {
    Controller c;

    public CustomDialog(Controller c) {
        this.c = c;
    }

    public Dialog<Result3> customEnchainement(){
        //Initialisation de la boite de dialog
        Dialog<Result3> dialog = new Dialog<>();
        dialog.setTitle("Creation enchainement");
        dialog.setHeaderText("Création d'un enchainement en sélectionnant la section de départ, " +
                "la section d'arrivée et des objets condition");
        //dialog.setGraphic(new ImageView(this.getClass().getResource("../../res/test.jpg").toString()));

        //Bouton de validation
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.FINISH, ButtonType.CANCEL);

        //Initialisation de la Grille
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        //Initialisation des Labels
        Label labSecDepart = new Label("Section de départ");
        Label labSecArrive = new Label("Section d'arrivée");
        Label labObjetCondition = new Label("Objet Condition de l'enchainement");

        //Initialisations des ChoiceBox
        ComboBox<Section> cbDepart = new ComboBox<>();
        cbDepart.getItems().addAll(c.getLv().getAllSections());

        ComboBox <Section> cbArrive = new ComboBox<>();
        cbArrive.getItems().addAll(c.getLv().getAllSections());

        CheckComboBox <Objet> cbObjet = new CheckComboBox<>();
        cbObjet.getItems().addAll(c.getLv().getAllObject());

        //Ajout des éléments dans la grille
        grid.add(labSecDepart, 0, 0);
        grid.add(labSecArrive, 0, 1);
        grid.add(labObjetCondition,  0, 2);
        grid.add(cbDepart, 1,0);
        grid.add(cbArrive, 1,1);
        grid.add(cbObjet, 1,2);

        //Ajout de la grille à la pane
        dialog.getDialogPane().setContent(grid);

        Platform.runLater(cbDepart::requestFocus);
        Node test = dialog.getDialogPane().lookupButton(ButtonType.FINISH);

        dialog.setResultConverter(buttonType -> {
            if(buttonType == ButtonType.FINISH){
                Section dep = cbDepart.getSelectionModel().getSelectedItem();
                Section arr = cbArrive.getSelectionModel().getSelectedItem();
                List<Objet> objs = cbObjet.getCheckModel().getCheckedItems();
                return new Result3(dep, arr, objs);
            }
            return null;
        });

        Node loginButton = dialog.getDialogPane().lookupButton(ButtonType.FINISH);
        loginButton.setDisable(true);

        loginButton.disableProperty().bind(Bindings.or(cbDepart.getSelectionModel().selectedIndexProperty().isEqualTo(-1),
                Bindings.or(cbArrive.getSelectionModel().selectedIndexProperty().isEqualTo(-1),
                        cbArrive.getSelectionModel().selectedIndexProperty().isEqualTo(cbDepart.getSelectionModel().selectedIndexProperty()))));
        return dialog;
    }

    public Dialog<String> customLivre(){
        TextInputDialog dialog = new TextInputDialog("Titre");
        dialog.setTitle("Création livre");
        dialog.setHeaderText("Création du livre en entrant le titre");
        dialog.setContentText("Entrer le titre du livre :");
        return dialog;
    }

    public Dialog<Pair<Integer, String>> customSection() {
        //Initialisation de la boite de dialog
        Dialog<Pair<Integer, String>> dialog = new Dialog<>();
        dialog.setTitle("Creation section");
        dialog.setHeaderText("Création d'une section en spécifiant son numéro ainsi que son texte. " +
                "Si le numéro 0 est spécifié alors ce sera la section de départ");

        //Bouton de validation
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.FINISH, ButtonType.CANCEL);

        //Initialisation de la Grille
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        //Initialisations des labels
        Label labNumero = new Label("Numéro :");
        Label labTexte = new Label("Texte :");

        //Initialisation du TextField pour le numéro et du TextArea pour le texte
        TextField txNumero = new TextField();
        txNumero.setPromptText("Numéro");
        TextArea txTexte = new TextArea();
        txTexte.setPromptText("Texte de la section");

        //Ajout des éléments à la grille
        grid.add(labNumero, 0, 0);
        grid.add(txNumero, 1, 0);
        grid.add(labTexte, 0,1);
        grid.add(txTexte, 1, 1);

        //Ajout de la grille à la dialog
        dialog.getDialogPane().setContent(grid);

        Platform.runLater(txNumero::requestFocus);

        Node loginButton = dialog.getDialogPane().lookupButton(ButtonType.FINISH);
        loginButton.setDisable(true);




        //Verification sur le numéro de Section
        Label problemeValeur = new Label("Pas un nombre");

        BooleanProperty isValidNumber = new SimpleBooleanProperty(false);
        problemeValeur.visibleProperty().bind(isValidNumber);

        grid.add(problemeValeur, 2,0);
        isValidNumber.setValue(true);

        txNumero.textProperty().addListener((observableValue, s, t1) -> {
            try{
                Integer temp = Integer.parseInt(t1);
                isValidNumber.setValue(false);
                //if(txTexte.getText()!="") loginButton.setDisable(false);
            }catch(Exception e){
                txNumero.setPromptText("");
                isValidNumber.setValue(true);
            }
        });

        //Verification sur le texte de la section
        Label problemeTexte = new Label("Pas texte");
        grid.add(problemeTexte, 2,1);
        problemeTexte.setVisible(false);
        txTexte.textProperty().addListener((observableValue, s, t1) -> problemeTexte.setVisible(t1.equals("")));

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.FINISH) {
                return new Pair<>(Integer.parseInt(txNumero.getText()), txTexte.getText());
            }
            return null;
        });

        loginButton.disableProperty().bind(Bindings.or(isValidNumber, txTexte.textProperty().isEmpty()));

        return dialog;
    }

    public Dialog<Pair<String, Section>> customObjet() {

        //Initialisation de la boite de dialog
        Dialog<Pair<String, Section>> dialog = new Dialog<>();
        dialog.setTitle("Creation objet");
        dialog.setHeaderText("Création d'un objet en spécifiant son nom et la section " +
                "dans laquelle il est présent");

        //Bouton de validation
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.FINISH, ButtonType.CANCEL);

        //Initialisation de la Grille
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        //Initialisations des labels
        Label labNom = new Label("Nom :");
        Label labSection = new Label("Section :");

        //Initialisation du TextField pour le nom de l'objet
        TextField txNom = new TextField();
        txNom.setPromptText("Nom de l'objet");

        //Initialisation ComboBox
        ComboBox <Section> cbSection = new ComboBox<>();
        cbSection.getItems().addAll(c.getLv().getAllSections());

        //Ajout des éléments à la grille
        grid.add(labNom, 0, 0);
        grid.add(txNom, 1, 0);
        grid.add(labSection, 0,1);
        grid.add(cbSection, 1, 1);

        //Ajout de la grille à la dialog
        dialog.getDialogPane().setContent(grid);

        Platform.runLater(txNom::requestFocus);

        Node loginButton = dialog.getDialogPane().lookupButton(ButtonType.FINISH);
        loginButton.setDisable(true);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.FINISH) {

                return new Pair<>(txNom.getText(), cbSection.getSelectionModel().getSelectedItem());
            }
            return null;
        });

        loginButton.disableProperty().bind(Bindings.or(txNom.textProperty().isEmpty(), cbSection.getSelectionModel().selectedIndexProperty().isEqualTo(-1)));

        return dialog;
    }

    public Alert errorDialog(String txt){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Problème rencontré");
        alert.setHeaderText("Une erreur est apparu");
        alert.setContentText(txt);

        return alert;
    }

    public Alert confirmationDialog(String txt){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Boite de dialogue de confirmation");
        alert.setHeaderText("Attention à ce que vous faites");
        alert.setContentText(txt);
        return alert;
    }

    private boolean verifChampNumero(TextField tx){
        try{
            Integer.parseInt(tx.getText());
            return true;
        }catch(Exception e){
            return false;
        }
    }

}
