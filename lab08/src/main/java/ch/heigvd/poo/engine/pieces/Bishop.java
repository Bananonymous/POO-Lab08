package ch.heigvd.poo.engine.pieces;

import ch.heigvd.poo.chess.PieceType;
import ch.heigvd.poo.chess.PlayerColor;
import ch.heigvd.poo.engine.board.GCell;

import java.util.LinkedList;
import java.util.List;

/**
 * The Bishop class represents a bishop chess piece.
 * It extends the Piece class and provides specific movement logic for the bishop.
 *
 * @author : Surbeck Léon
 * @author : Nicolet Victor
 */
public class Bishop extends Piece {

    /**
     * Constructs a Bishop with the specified color and initial position.
     *
     * @param color the color of the bishop
     * @param cell  the initial position of the bishop
     */
    public Bishop(PlayerColor color, GCell cell) {
        super(PieceType.BISHOP, color, cell);
    }

    /**
     * Checks if the bishop can move to the specified cell.
     * The bishop can move diagonally, so the distance in rows must equal the distance in columns.
     *
     * @param to the target cell
     * @return true if the bishop can move to the target cell, false otherwise
     */
    @Override
    public boolean canMove(GCell to) {
        if (super.canMove(to))
            return cell.distanceRow(to) == cell.distanceCol(to);
        return false;
    }

    /**
     * Returns the path to the target cell.
     * The path is a list of cells representing the diagonal movement of the bishop.
     *
     * @param to the target cell
     * @return a list of cells representing the path to the target cell
     */
    @Override
    public List<GCell> path(GCell to) {
        List<GCell> path = new LinkedList<>();

        int rowDirection = cell.directionRow(to);
        int colDirection = cell.directionCol(to);

        int colDistance = cell.distanceCol(to);

        for (int i = 1; i < colDistance; i++)
            path.add(new GCell(cell.getRow() + i * rowDirection, cell.getCol() + i * colDirection));

        return path;
    }
}