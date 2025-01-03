package ch.heigvd.poo.engine.pieces;

import ch.heigvd.poo.chess.PieceType;
import ch.heigvd.poo.chess.PlayerColor;
import ch.heigvd.poo.engine.board.GCell;

import java.util.LinkedList;
import java.util.List;

/**
 * The Knight class represents a knight chess piece.
 * It extends the Piece class and provides specific implementations for knight movements.
 *
 * @author : Surbeck léon
 * @author : Nicolet Victor
 */
public class Knight extends Piece {

    /**
     * Constructs a Knight with the specified color and initial cell.
     *
     * @param color the color of the knight
     * @param cell the initial cell of the knight
     */
    public Knight(PlayerColor color, GCell cell) {
        super(PieceType.KNIGHT, color, cell);
    }

    /**
     * Checks if the knight can move to the specified cell.
     *
     * @param to the cell to move to
     * @return true if the knight can move to the specified cell, false otherwise
     */
    @Override
    public boolean canMove(GCell to) {
        super.canMove(to);

        return cell.distanceRow(to) * cell.distanceCol(to) == 2;
    }

    /**
     * Returns the path of cells the knight will move through to reach the specified cell.
     * Since the knight jumps directly to the destination cell, the path is empty.
     *
     * @param to the destination cell
     * @return a list of cells representing the path to the destination cell
     */
    @Override
    public List<GCell> path(GCell to) {
        return new LinkedList<>();
    }
}