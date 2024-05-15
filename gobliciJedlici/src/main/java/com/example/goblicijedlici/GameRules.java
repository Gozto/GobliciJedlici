package com.example.goblicijedlici;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameRules {

    public static void showRules(Stage ownerStage) {
        Stage rulesStage = new Stage();
        rulesStage.setTitle("Pravidlá hry");

        Label rulesLabel = getLabel();

        VBox rulesLayout = new VBox(20);
        rulesLayout.setAlignment(Pos.CENTER);
        rulesLayout.getChildren().add(rulesLabel);
        rulesLayout.setPadding(new Insets(20));

        rulesLayout.setStyle("-fx-background-color: #ADD8E6;");

        Scene rulesScene = new Scene(rulesLayout, 450, 300);
        rulesStage.setScene(rulesScene);

        rulesStage.initOwner(ownerStage);
        rulesStage.showAndWait();
    }

    private static Label getLabel() {
        Label rulesLabel = new Label("Každý hráč ma k dispozícií 6 goblíkov - dvoch z každej veľkosti. Goblíka môže " +
                "hráč položiť buď na voľné miesto, alebo na iného goblíka, ktorý je ale menší (na farbe goblíka " +
                "nezáleží). Cieľom hry je uložiť vedľa seba troch goblíkov rovnakej farby, buď vertikálne, horizontálne, " +
                "alebo na diagonále.");
        rulesLabel.setStyle("-fx-font-size: 16px;");

        rulesLabel.setWrapText(true);
        rulesLabel.setMaxWidth(400);
        return rulesLabel;
    }
}
