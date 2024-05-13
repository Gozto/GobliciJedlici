package com.example.goblicijedlici;

import java.util.*;


public class GameLogic {

    private String currentPlayer;
    private Map<String, Integer[]> availableGoblik = new HashMap<>();
    private String[] players = {"Red", "Blue"};
    Random r = new Random();
    Map<AbstractMap.SimpleEntry<Integer, Integer>, Stack<Goblik>> board;
    private final int size = 3;

    public GameLogic() {
        availableGoblik.put("Blue", new Integer[] {2,2,2});
        availableGoblik.put("Red", new Integer[] {2,2,2});
        currentPlayer = players[r.nextInt(2)];
        board = new HashMap<>();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                board.put(new AbstractMap.SimpleEntry<>(row, col), new Stack<>());
            }
        }
    }

    public void changeCurPlayer() {
        if (currentPlayer.equals("Blue")) {
            currentPlayer = "Red";
            return;
        }
        currentPlayer = "Blue";
    }

    public boolean isGoblikAvailable(String color, int size) {
        return availableGoblik.get(color)[size] != 0;
    }

    public void takeGoblik(String color, int size) {
        if (isGoblikAvailable(color, size)) {
            availableGoblik.get(color)[size]--;
        }
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public String isWinner() {
        String rows = checkWinnerRow();
        if (rows != null) return rows;
        String cols = checkWinnerCol();
        if (cols != null) return cols;
        String diagonals = checkWinnerDiagonals();
        if (diagonals != null) return diagonals;

        return null; // no winner
    }

    private String checkWinnerRow() { // vraciam farbu ktora vyhra
        for (int i = 0; i < 3; i++) {
            Stack<Goblik> firstStack = board.get(new AbstractMap.SimpleEntry<>(i, 0));
            if (firstStack.isEmpty()) continue;
            String firstColor = firstStack.peek().getColor();

            boolean allMatch = true;
            for (int j = 1; j < 3; j++) {
                Stack<Goblik> currentStack = board.get(new AbstractMap.SimpleEntry<>(i, j));
                if (currentStack.isEmpty() || !currentStack.peek().getColor().equals(firstColor)) {
                    allMatch = false;
                    break;
                }
            }
            if (allMatch) return firstColor;
        }
        return null;
    }

    private String checkWinnerCol() {
        for (int i = 0; i < 3; i++) {
            Stack<Goblik> firstStack = board.get(new AbstractMap.SimpleEntry<>(0, i));
            if (firstStack.isEmpty()) continue;
            String firstColor = firstStack.peek().getColor();

            boolean allMatch = true;
            for (int j = 0; j < size; j++) {
                Stack<Goblik> currentStack = board.get(new AbstractMap.SimpleEntry<>(j, i));
                if (currentStack.isEmpty() || !currentStack.peek().getColor().equals(firstColor)) {
                    allMatch = false;
                    break;
                }
            }
            if (allMatch) return firstColor;
        }
        return null;
    }

    private String checkWinnerDiagonals() {

        // prva diagonala
        boolean allMatch = true;
        Stack<Goblik> firstStack = board.get(new AbstractMap.SimpleEntry<>(0,0));
        if (!firstStack.isEmpty()) {
            String firstColor = firstStack.peek().getColor();
            for (int i = 1; i < size; i++) {
                Stack<Goblik> curStack = board.get(new AbstractMap.SimpleEntry<>(i,i));
                if (curStack.isEmpty() || !curStack.peek().getColor().equals(firstColor)) {
                    allMatch = false;
                    break;
                }
            }
            if (allMatch) return firstColor;
        }

        // druha diagonala
        allMatch = true;
        int i = size-1;
        Stack<Goblik> firstStackTwo = board.get(new AbstractMap.SimpleEntry<>(i, 0));
        if (!firstStackTwo.isEmpty()) {
            String firstColorTwo = firstStackTwo.peek().getColor();
            for (int j = 0; j < size; j++) {
                Stack<Goblik> curStackTwo = board.get(new AbstractMap.SimpleEntry<>(i,j));
                if (curStackTwo.isEmpty() || !curStackTwo.peek().getColor().equals(firstColorTwo)) {
                    allMatch = false;
                    break;
                }
                i--;
            }
            if (allMatch) return firstColorTwo;
        }
        return null;
    }

    public Map<AbstractMap.SimpleEntry<Integer, Integer>, Stack<Goblik>> getBoard() {
        return board;
    }

    public Map<String, Integer[]> getAvailableGoblik() {
        return availableGoblik;
    }

    boolean placeGoblik(int row, int col, Goblik goblik) {

        String currentPlayer = getCurrentPlayer();
        int size = goblik.getSize();
        if (!isGoblikAvailable(currentPlayer, size)) {
            System.out.println("Už si minul goblíkov veľkosti " + (size + 1) + "!");
            return false;
        }

        Stack<Goblik> stack = board.get(new AbstractMap.SimpleEntry<>(row, col));
        if (stack.isEmpty() || stack.peek().getSize() < goblik.getSize()) {
            stack.push(goblik);
            System.out.println("Goblik placed at " + row + ", " + col);
            return true;
        }
        else {
            System.out.println("Cannot place a larger Goblik on a smaller one!");
            return false;
        }
    }

    public int getSize() {
        return size;
    }

    public boolean isValidMove(Integer curRow, Integer curCol, int newRow, int newCol) {
        if (curRow.equals(newRow) && curCol.equals(newCol)) {
            return false;
        }

        Stack<Goblik> curGoblikStack = board.get(new AbstractMap.SimpleEntry<>(curRow, curCol));
        if (!curGoblikStack.isEmpty()) {
            Goblik curGoblik = curGoblikStack.peek();
            int curGobliKSize = curGoblik.getSize();
            Stack<Goblik> newPositionGoblikStack = board.get(new AbstractMap.SimpleEntry<>(newRow, newCol));
            if (newPositionGoblikStack.isEmpty() || newPositionGoblikStack.peek().getSize() < curGobliKSize) {
                return true;
            }
        }
        return false;
    }

    public void moveGoblik(Integer curRow, Integer curCol, int newRow, int newCol) {
        Goblik curGoblik = board.get(new AbstractMap.SimpleEntry<>(curRow, curCol)).pop();
        board.get(new AbstractMap.SimpleEntry<>(newRow, newCol)).add(curGoblik);
    }
}
