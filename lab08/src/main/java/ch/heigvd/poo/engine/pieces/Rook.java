package ch.heigvd.poo.engine.pieces;

import ch.heigvd.poo.chess.PieceType;
import ch.heigvd.poo.chess.PlayerColor;
import ch.heigvd.poo.engine.board.GCell;

import java.util.LinkedList;
import java.util.List;

/**
 * The Rook class represents a rook chess piece.
 * It extends the Piece class and provides specific implementations for rook movements.
 *
 * @author : Surbeck Léon
 * @author : Nicolet Victor
 */
public class Rook extends Piece {
    private boolean hasMoved = false;

    /**
     * Constructs a Rook with the specified color and initial cell.
     *
     * @param color the color of the rook
     * @param cell the initial cell of the rook
     */
    public Rook(PlayerColor color, GCell cell) {
        super(PieceType.ROOK, color, cell);
    }

    /**
     * Checks if the rook has moved.
     *
     * @return true if the rook has moved, false otherwise
     */
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * Returns the text representation of the rook.
     *
     * @return the text representation of the rook
     */
    public String textValue() {
        return super.textValue();
    }

    /**
     * Checks if the rook can move to the specified cell.
     *
     * @param to the cell to move to
     * @return true if the rook can move to the specified cell, false otherwise
     */
    @Override
    public boolean canMove(GCell to) {
        if (super.canMove(to)) {
            return to.getRow() == cell.getRow() || to.getCol() == cell.getCol();
        }
        return false;
    }

    /**
     * Returns the path of cells the rook will move through to reach the specified cell.
     *
     * @param to the destination cell
     * @return a list of cells representing the path to the destination cell
     */
    @Override
    public List<GCell> path(GCell to) {
        List<GCell> path = new LinkedList<>();

        int distanceRow = cell.distanceRow(to);
        int distanceCol = cell.distanceCol(to);

        if (distanceCol == 0) {
            for (int i = 1; i < distanceRow; i++) {
                path.add(new GCell(cell.getRow(), cell.getCol() + i * cell.directionCol(to)));
            }
        } else if (distanceRow == 0) {
            for (int i = 1; i < distanceCol; i++) {
                path.add(new GCell(cell.getRow() + i * cell.directionRow(to), cell.getCol()));
            }
        }

        if (!path.isEmpty())
            hasMoved = true;
        return path;
    }
}