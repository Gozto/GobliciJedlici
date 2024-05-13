package com.example.goblicijedlici;

public class Goblik {

    int size; // 0 - small, 1 - medium, 2 - big
    int positionRow;
    int positionCol;
    String color;

    public Goblik(int size, int positionRow, int positionCol, String color) {
        this.size = size;
        this.positionRow = positionRow;
        this.positionCol = positionCol;
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public int getPositionRow() {
        return positionRow;
    }

    public int getPositionCol() {
        return positionCol;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setPositionRow(int positionRow) {
        this.positionRow = positionRow;
    }

    public void setPositionCol(int positionCol) {
        this.positionCol = positionCol;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Goblik{" +
                "size=" + size +
                ", positionRow=" + positionRow +
                ", positionCol=" + positionCol +
                ", color=" + color +
                '}';
    }
}
