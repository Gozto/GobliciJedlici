package com.example.goblicijedlici;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.util.*;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.application.Platform;
import javafx.stage.Stage;


/**
 * The type Game board.
 */
public class GameBoard extends VBox {

    private Label currentPlayerLabel;
    private final Label selectedGoblikInfo;
    private final GridPane grid;
    private final GameLogic gameLogic;
    private final VBox leftPanel; // pre modreho
    private final VBox rightPanel; // pre cerveneho

    private int selectedGoblikSize = -1;
    private AbstractMap.SimpleEntry<Integer, Integer> movingGoblikPosition = null; // ked presuvame goblika

    /**
     * Instantiates a new Game board.
     *
     * @param gameLogic the game logic
     */
    public GameBoard(GameLogic gameLogic) {

        this.gameLogic = gameLogic;
        Map<AbstractMap.SimpleEntry<Integer, Integer>, Stack<Goblik>> board = gameLogic.getBoard();

        currentPlayerLabel = new Label();
        selectedGoblikInfo = new Label("Žiaden goblík nie je vybraný.");
        selectedGoblikInfo.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

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

        HBox mainContainer = new HBox(0);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.getChildren().addAll(leftPanel, grid, rightPanel);

        this.setOnKeyPressed(event -> {
            String key = event.getText();
            if (key.matches("[123]")) {
                selectedGoblikSize = Integer.parseInt(key) - 1;
                selectedGoblikInfo.setText("Vybraný goblík: Veľkosť " + (selectedGoblikSize + 1));
            }
            else if (key.matches("[r]")) {
                selectedGoblikSize = -1;
                selectedGoblikInfo.setText("Žiaden goblík nie je vybraný.");
            }
        });

        VBox layout = new VBox(5);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(currentPlayerLabel, mainContainer,  selectedGoblikInfo);
        this.getChildren().add(layout);
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);
    }

    private void initializeBoard() {
        int size = 3;
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
        if (selectedGoblikSize == -1) {
            if (movingGoblikPosition == null) {
                Stack<Goblik> stack = gameLogic.getBoard().get(new AbstractMap.SimpleEntry<>(row, col));
                if (!stack.isEmpty() && stack.peek().getColor().equals(gameLogic.getCurrentPlayer())) {
                    movingGoblikPosition = new AbstractMap.SimpleEntry<>(row, col);
                    selectedGoblikInfo.setText("Vybraný goblík na riadku: " + row + " a stĺpci: " + col);
                }
                else {
                    selectedGoblikInfo.setText("Neplatný ťah!");
                    selectedGoblikSize = -1;
                    movingGoblikPosition = null;
                }
                return;
            }
            else {
                if (gameLogic.isValidMove(movingGoblikPosition.getKey(), movingGoblikPosition.getValue(), row, col)) {
                    gameLogic.moveGoblik(movingGoblikPosition.getKey(), movingGoblikPosition.getValue(), row, col);
                    updateBoard();
                    checkForWinner();
                    gameLogic.changeCurPlayer();
                    updateCurrentPlayerLabel();
                    movingGoblikPosition = null;
                    selectedGoblikSize = -1;
                    selectedGoblikInfo.setText("Žiaden goblík nie je vybraný.");
                    return;
                }
                else {
                    selectedGoblikInfo.setText("Neplatný ťah!");
                    selectedGoblikSize = -1;
                    movingGoblikPosition = null;
                    return;
                }
            }
        }

        String currentPlayer = gameLogic.getCurrentPlayer();
        if (gameLogic.placeGoblik(row, col, new Goblik(selectedGoblikSize, row, col, currentPlayer))) {
            gameLogic.takeGoblik(currentPlayer, selectedGoblikSize);
            updateBoard();
            checkForWinner();
            updateGoblikDisplay();
            gameLogic.changeCurPlayer();
            updateCurrentPlayerLabel();
            selectedGoblikSize = -1;
            movingGoblikPosition = null;
            selectedGoblikInfo.setText("Žiaden goblík nie je vybraný.");
        }
        else {
            selectedGoblikInfo.setText("Neplatný ťah!");
            selectedGoblikSize = -1;
            movingGoblikPosition = null;
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
        updateGoblikDisplay();
    }

    private void updateGoblikDisplay() {
        leftPanel.getChildren().clear();
        rightPanel.getChildren().clear();

        Map<String, Integer[]> availableGoblik = gameLogic.getAvailableGoblik();
        displayGobliks(availableGoblik.get("Blue"), leftPanel, Color.BLUE);
        displayGobliks(availableGoblik.get("Red"), rightPanel, Color.RED);
    }

    private void displayGobliks(Integer[] gobliks, VBox panel, Color color) {
        for (int i = 0; i < gobliks.length; i++) {
            for (int j = 0; j < gobliks[i]; j++) {
                Circle circle = new Circle((i + 1) * 10, color);
                final int size = i;
                circle.setOnMouseClicked(e -> {
                    Paint farba = circle.getFill();
                    String c = "";
                    if (farba instanceof Color && ((Color) farba).equals(Color.RED)) {
                        c = "Red";
                    }
                    else {
                        c = "Blue";
                    }
                    if (c.equals(gameLogic.getCurrentPlayer())) {
                        selectedGoblikSize = size;
                        selectedGoblikInfo.setText("Vybraný goblík: Veľkosť " + (selectedGoblikSize + 1));
                        updateSelectedGoblikInfo();
                    }
                });
                panel.getChildren().add(circle);
            }
        }
    }

    private void updateSelectedGoblikInfo() {
        if (selectedGoblikSize != -1) {
            selectedGoblikInfo.setText("Vybraný goblík: Veľkosť " + (selectedGoblikSize + 1));
        }
        else {
            selectedGoblikInfo.setText("Žiaden goblík nie je vybraný.");
        }
    }


    private void updateBoard() {
        grid.getChildren().clear();

        for (int row = 0; row < gameLogic.getSize(); row++) {
            for (int col = 0; col < gameLogic.getSize(); col++) {
                StackPane cellPane = new StackPane();
                cellPane.setMinSize(105, 105);

                Button cell = new Button();
                cell.setMinSize(105, 105);
                cell.setMaxSize(105, 105);
                String top = (row == 0) ? "0" : "1";
                String right = (col == gameLogic.getSize() - 1) ? "0" : "1";
                String bottom = (row == gameLogic.getSize() - 1) ? "0" : "1";
                String left = (col == 0) ? "0" : "1";
                cell.setStyle(String.format("-fx-background-color: transparent; -fx-border-color: black; " +
                        "-fx-border-width: %s %s %s %s;", top, right, bottom, left));

                Stack<Goblik> stack = gameLogic.getBoard().get(new AbstractMap.SimpleEntry<>(row, col));

                if (!stack.isEmpty()) {
                    Goblik topGoblik = stack.peek();
                    Circle circle = new Circle(getCircleRadius(topGoblik.getSize()));
                    circle.setFill(topGoblik.getColor().equals("Red") ? Color.RED : Color.BLUE);
                    cellPane.getChildren().add(circle);
                }

                cellPane.getChildren().add(cell);

                final int r = row;
                final int c = col;
                cell.setOnAction(e -> handleButtonAction(r, c));

                grid.add(cellPane, col, row);
            }
        }
    }

    private double getCircleRadius(int size) {
        switch (size) {
            case 0: return 10;
            case 1: return 20;
            case 2: return 30;
            default: return 10;
        }
    }

    private void checkForWinner() {
        String winner = gameLogic.isWinner();
        if (winner != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText(winner + " vyhral!\nVyber si, čo ďalej:");

            ButtonType buttonTypeNewGame = new ButtonType("Nová hra");
            ButtonType buttonTypeMainMenu = new ButtonType("Vráť sa do menu");
            ButtonType buttonTypeCancel = new ButtonType("Koniec", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeNewGame, buttonTypeMainMenu, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == buttonTypeNewGame) {
                restartGame();
            }
            else if (result.isPresent() && result.get() == buttonTypeMainMenu) {
                goToMainMenu();
            } 
            else if (result.isPresent() && result.get() == buttonTypeCancel) {
                Platform.exit();
            }
        }
    }

    private void restartGame() {
        gameLogic.restartGame();
        resetSelectedGoblikInfo();
        updateBoard();
        updateGoblikDisplay();
        updateCurrentPlayerLabel();
    }

    private void goToMainMenu() {
        Stage stage = (Stage) this.getScene().getWindow();
        stage.setScene(Main.getMainScene());
        stage.show();
    }

    private void resetSelectedGoblikInfo() {
        selectedGoblikInfo.setText("Žiaden goblík nie je vybraný.");
    }

}
