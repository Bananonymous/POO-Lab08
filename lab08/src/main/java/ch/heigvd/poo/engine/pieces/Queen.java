package ch.heigvd.poo.engine.pieces;

import ch.heigvd.poo.chess.PieceType;
import ch.heigvd.poo.chess.PlayerColor;
import ch.heigvd.poo.engine.board.GCell;

import java.util.LinkedList;
import java.util.List;

/**
 * The Queen class represents a queen chess piece.
 * It extends the Piece class and provides specific implementations for queen movements.
 *
 * @author : Surbeck léon
 * @author : Nicolet Victor
 */
public class Queen extends Piece {

    /**
     * Constructs a Queen with the specified color and initial cell.
     *
     * @param color the color of the queen
     * @param cell  the initial cell of the queen
     */
    public Queen(PlayerColor color, GCell cell) {
        super(PieceType.QUEEN, color, cell);
    }

    /**
     * Checks if the queen can move to the specified cell.
     *
     * @param to the cell to move to
     * @return true if the queen can move to the specified cell, false otherwise
     */
    @Override
    public boolean canMove(GCell to) {
        super.canMove(to);
        if (cell.distanceRow(to) == cell.distanceCol(to)) {
            return true;
        }
        return to.getRow() == cell.getRow() || to.getCol() == cell.getCol();
    }

    /**
     * Returns the path of cells the queen will move through to reach the specified cell.
     *
     * @param to the destination cell
     * @return a list of cells representing the path to the destination cell
     */
    @Override
    public List<GCell> path(GCell to) {
        List<GCell> path = new LinkedList<>();
        int distanceRow = cell.distanceRow(to);
        int distanceCol = cell.distanceCol(to);

        if (distanceRow == distanceCol) {
            for (int i = 1; i < distanceRow; ++i) {
                int row = cell.getRow() + i * cell.directionRow(to);
                int col = cell.getCol() + i * cell.directionCol(to);
                path.add(new GCell(row, col));
            }
        } else if (to.getRow() == cell.getRow()) {
            for (int i = 1; i < distanceCol; ++i) {
                int col = cell.getCol() + i * cell.directionCol(to);
                path.add(new GCell(cell.getRow(), col));
            }
        } else if (to.getCol() == cell.getCol()) {
            for (int i = 1; i < distanceRow; ++i) {
                int row = cell.getRow() + i * cell.directionRow(to);
                path.add(new GCell(row, cell.getCol()));
            }
        }

        return path;
    }
}