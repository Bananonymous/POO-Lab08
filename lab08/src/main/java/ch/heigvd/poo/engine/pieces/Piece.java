package ch.heigvd.poo.engine.pieces;

import ch.heigvd.poo.chess.*;
import ch.heigvd.poo.engine.board.GCell;

import java.util.List;
import java.util.Objects;

/**
 * The Piece class represents a chess piece with a specific type, color, and position on the board.
 * It provides methods to get the type, color, and position, check if the piece can move to a specific cell,
 * and abstract methods to get the path to a target cell.
 *
 * @author : Surbeck Léon
 * @author : Nicolet Victor
 */
abstract public class Piece implements ChessView.UserChoice {

    protected final PieceType type;
    protected final PlayerColor color;
    protected GCell cell;

    /**
     * Constructs a Piece with the specified type, color, and initial position.
     *
     * @param type  the type of the piece
     * @param color the color of the piece
     * @param cell  the initial position of the piece
     */
    public Piece(PieceType type, PlayerColor color, GCell cell) {
        this.type = type;
        this.color = color;
        this.cell = cell;
    }

    /**
     * Checks if the piece can move to the specified cell.
     *
     * @param to the target cell
     * @return true if the piece can move to the target cell, false otherwise
     */
    public boolean canMove(GCell to) {
        if (to.equals(this.cell))
            return false;

        return to.getRow() < 8 && to.getCol() < 8;
    }

    /**
     * Returns the type of the piece.
     *
     * @return the type of the piece
     */
    public PieceType getType() {
        return type;
    }

    /**
     * Returns the color of the piece.
     *
     * @return the color of the piece
     */
    public PlayerColor getColor() {
        return color;
    }

    /**
     * Returns the current position of the piece.
     *
     * @return the current position of the piece
     */
    public GCell getCell() {
        return cell;
    }

    /**
     * Sets the position of the piece.
     *
     * @param cell the new position of the piece
     */
    public void setCell(GCell cell) {
        this.cell = cell;
    }

    /**
     * Returns the path to the target cell.
     *
     * @param to the target cell
     * @return a list of cells representing the path to the target cell
     */
    public abstract List<GCell> path(GCell to);

    /**
     * Checks if the piece has moved.
     *
     * @return true if the piece has moved, false otherwise
     */
    public boolean getHasMoved() {
        return true;
    }

    /**
     * Returns the text value of the piece.
     *
     * @return the text value of the piece
     */
    public String textValue() {
        return getClass().getSimpleName();
    }

    /**
     * Returns a string representation of the piece.
     *
     * @return a string representation of the piece
     */
    @Override
    public String toString() {
        return "Piece{" +
                "type=" + type +
                ", color=" + color +
                ", cell=" + cell +
                '}';
    }

    /**
     * Checks if this piece is equal to another object.
     *
     * @param o the object to compare with
     * @return true if the object is a Piece with the same type, color, and position, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return type == piece.type &&
                color == piece.color &&
                Objects.equals(cell, piece.cell);
    }

    /**
     * Returns the hash code of this piece.
     *
     * @return the hash code of this piece
     */
    @Override
    public int hashCode() {
        return Objects.hash(type, color, cell);
    }
}