package com.example.goblicijedlici;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Goblíci jedlíci");

        Button singlePlayerButton = new Button("Hra proti počítaču");
        Button twoPlayerButton = new Button("Hra pre 2 hráčov");

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(singlePlayerButton, twoPlayerButton);
        layout.setPadding(new Insets(200, 0, 0, 0));

        Image backgroundImage = new Image(Objects.requireNonNull(getClass()
                .getResourceAsStream("/goblici_img.jpg")));
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        layout.setBackground(new Background(background));

        singlePlayerButton.setOnAction(e -> startSinglePlayerGame(stage));
        twoPlayerButton.setOnAction(e -> startTwoPlayerGame(stage));

        Scene scene = new Scene(layout, 500, 500);
        stage.setScene(scene);
        stage.show();
    }

    private void startSinglePlayerGame(Stage primaryStage) {
        System.out.println("Spúšťa sa hra proti počítaču...");
    }

    private void startTwoPlayerGame(Stage primaryStage) {
        GameLogic gameLogic = new GameLogic();
        GameBoard gameBoard = new GameBoard(gameLogic);

        Scene scene = new Scene(gameBoard, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
