package javaCode.view;

import javaCode.data.Section;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class CustomDialog {
    Controller c;

    public CustomDialog(Controller c) {
        this.c = c;
    }

    public Dialog<Boolean> customEnchainement(){
        //Initialisation de la boite de dialog
        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Creation enchainement");
        dialog.setHeaderText("Création d'un enchainement en sélectionnant la section de départ, " +
                "la section d'arrivée et des objets condition");
        dialog.setGraphic(new ImageView(this.getClass().getResource("../../res/test.jpg").toString()));

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
        ChoiceBox <Section> cbDepart = new ChoiceBox<>();
        cbDepart.getItems().addAll(c.getLv().getAllSections());

        ChoiceBox <Section> cbArrive = new ChoiceBox<>();
        cbArrive.getItems().addAll(c.getLv().getAllSections());

        Node valid = dialog.getDialogPane().lookupButton(ButtonType.FINISH);

        return dialog;
    }
}
