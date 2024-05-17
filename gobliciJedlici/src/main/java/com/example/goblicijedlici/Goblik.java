package com.example.goblicijedlici;

/**
 * The type Goblik.
 */
public class Goblik {

    private int size; // 0 - small, 1 - medium, 2 - big
    private int positionRow;
    private int positionCol;
    private String color;

    /**
     * Instantiates a new Goblik.
     *
     * @param size        the size
     * @param positionRow the position row
     * @param positionCol the position col
     * @param color       the color
     */
    public Goblik(int size, int positionRow, int positionCol, String color) {
        this.size = size;
        this.positionRow = positionRow;
        this.positionCol = positionCol;
        this.color = color;
    }

    /**
     * Gets size.
     *
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * Gets position row.
     *
     * @return the position row
     */
    public int getPositionRow() {
        return positionRow;
    }

    /**
     * Gets position col.
     *
     * @return the position col
     */
    public int getPositionCol() {
        return positionCol;
    }

    /**
     * Sets size.
     *
     * @param size the size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Sets position row.
     *
     * @param positionRow the position row
     */
    public void setPositionRow(int positionRow) {
        this.positionRow = positionRow;
    }

    /**
     * Sets position col.
     *
     * @param positionCol the position col
     */
    public void setPositionCol(int positionCol) {
        this.positionCol = positionCol;
    }

    /**
     * Gets color.
     *
     * @return the color
     */
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
