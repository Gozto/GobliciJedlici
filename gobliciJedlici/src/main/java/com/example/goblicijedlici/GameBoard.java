package com.example.goblicijedlici;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import java.util.*;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GameBoard extends VBox {

    private Label currentPlayerLabel;
    private GridPane grid;
    private Map<AbstractMap.SimpleEntry<Integer, Integer>, Stack<Goblik>> board;
    private final int size = 3;
    private GameLogic gameLogic;
    private VBox leftPanel; // pre modreho
    private VBox rightPanel; // pre cerveneho
    private HBox mainContainer;

    public GameBoard(GameLogic gameLogic) {

        this.gameLogic = gameLogic;
        board = gameLogic.getBoard();

        currentPlayerLabel = new Label();
        this.setAlignment(Pos.CENTER);
        updateCurrentPlayerLabel();

        leftPanel = new VBox(10);
        leftPanel.setPadding(new Insets(10));
        leftPanel.setAlignment(Pos.CENTER);

        rightPanel = new VBox(10);
        rightPanel.setPadding(new Insets(10));
        rightPanel.setAlignment(Pos.CENTER);

        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setGridLinesVisible(false);
        grid.setHgap(0);
        grid.setVgap(0);
        grid.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: #ADD8E6;");

        initializeBoard();
        initializeGoblikDisplays();

        mainContainer = new HBox(0);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.getChildren().addAll(leftPanel, grid, rightPanel);

        this.getChildren().addAll(currentPlayerLabel, mainContainer);
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);
    }

    private void initializeBoard() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Button button = new Button();
                button.setMinSize(105, 105);
                String top = (row == 0) ? "0" : "1";
                String right = (col == size - 1) ? "0" : "1";
                String bottom = (row == size - 1) ? "0" : "1";
                String left = (col == 0) ? "0" : "1";
                button.setStyle(String.format("-fx-background-color: transparent; " +
                        "-fx-border-color: black; -fx-border-width: %s %s %s %s;", top, right, bottom, left));
                final int r = row, c = col;
                button.setOnAction(e -> handleButtonAction(r, c));
                grid.add(button, col, row);
            }
        }
    }

    private void handleButtonAction(int row, int col) {
        System.out.println("Tlačidlo stlačené na riadku: " + row + ", stĺpci: " + col);
        gameLogic.changeCurPlayer();
        updateCurrentPlayerLabel();
    }


    public void showBoard() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Stack<Goblik> stack = board.get(new AbstractMap.SimpleEntry<>(row, col));
                if (stack.isEmpty()) {
                    System.out.print(". ");
                }
                else {
                    System.out.print("G ");
                }
            }
            System.out.println();
        }
    }

    private void updateCurrentPlayerLabel() {
        String currentPlayer = gameLogic.getCurrentPlayer();
        String color = currentPlayer.equals("Red") ? "#DC143C" : "#1E90FF";
        currentPlayerLabel.setText("Na rade je: " + currentPlayer);
        currentPlayerLabel.setStyle("-fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: "
                + color + ";");
    }

    private void initializeGoblikDisplays() {
        Map<String, Integer[]> availableGoblik = gameLogic.getAvailableGoblik();
        displayGobliks(availableGoblik.get("Blue"), leftPanel, Color.BLUE);
        displayGobliks(availableGoblik.get("Red"), rightPanel, Color.RED);
    }

    private void displayGobliks(Integer[] gobliks, VBox panel, Color color) {
        for (int i = 0; i < gobliks.length; i++) {
            for (int j = 0; j < gobliks[i]; j++) {
                Circle circle = new Circle((i + 1) * 10, color);
                panel.getChildren().add(circle);
            }
        }
    }

}
