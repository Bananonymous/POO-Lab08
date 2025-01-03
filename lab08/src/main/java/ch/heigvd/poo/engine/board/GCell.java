package ch.heigvd.poo.engine.board;

import java.util.Objects;

/**
 * The GCell class represents a cell in a grid with a specific row and column.
 * It provides methods to get the row and column, calculate distances and directions
 * to another cell, and override equals, hashCode, and toString methods.
 *
 * @author : Surbeck Léon
 * @author : Nicolet Victor
 */
public class GCell {
    private final int row;
    private final int col;

    /**
     * Constructs a GCell with the specified row and column.
     *
     * @param row the row of the cell
     * @param col the column of the cell
     */
    public GCell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Returns the row of the cell.
     *
     * @return the row of the cell
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column of the cell.
     *
     * @return the column of the cell
     */
    public int getCol() {
        return col;
    }

    /**
     * Calculates the distance in rows to another cell.
     *
     * @param to the target cell
     * @return the absolute distance in rows to the target cell
     */
    public int distanceRow(GCell to) {
        return Math.abs(col - to.getCol());
    }

    /**
     * Calculates the distance in columns to another cell.
     *
     * @param to the target cell
     * @return the absolute distance in columns to the target cell
     */
    public int distanceCol(GCell to) {
        return Math.abs(row - to.getRow());
    }

    /**
     * Calculates the direction in rows to another cell.
     *
     * @param to the target cell
     * @return 1 if the target cell is below, -1 if above, 0 if in the same row
     */
    public int directionRow(GCell to) {
        return Integer.signum(to.getRow() - row);
    }

    /**
     * Calculates the direction in columns to another cell.
     *
     * @param to the target cell
     * @return 1 if the target cell is to the right, -1 if to the left, 0 if in the same column
     */
    public int directionCol(GCell to) {
        return Integer.signum(to.getCol() - col);
    }

    /**
     * Checks if this cell is equal to another object.
     *
     * @param o the object to compare with
     * @return true if the object is a GCell with the same row and column, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GCell gCell)) return false;
        return row == gCell.row && col == gCell.col;
    }

    /**
     * Returns the hash code of this cell.
     *
     * @return the hash code of this cell
     */
    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    /**
     * Returns a string representation of this cell.
     *
     * @return a string representation of this cell
     */
    @Override
    public String toString() {
        return "GCell{" + "row=" + row + ", col=" + col + '}';
    }
}