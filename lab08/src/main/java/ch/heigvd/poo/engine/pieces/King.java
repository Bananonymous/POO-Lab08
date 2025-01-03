package ch.heigvd.poo.engine.pieces;

import ch.heigvd.poo.chess.PieceType;
import ch.heigvd.poo.chess.PlayerColor;
import ch.heigvd.poo.engine.board.GCell;
import ch.heigvd.poo.engine.listeners.BObserver;
import ch.heigvd.poo.engine.listeners.BEventSrc;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * The King class represents a king chess piece.
 * It extends the Piece class and provides specific implementations for king movements,
 * including castling and checking if the king is in check.
 *
 * @author : Surbeck Léon
 * @author : Nicolet Victor
 */
public class King extends Piece {
    private static final int ROOK_INITIAL_COL_LEFT = 0;
    private static final int ROOK_INITIAL_COL_RIGHT = 7;
    private boolean hasMoved = false;
    private boolean isCheck;
    private HashMap<GCell, Piece> board;
    private final BEventSrc events = new BEventSrc() {
    };

    /**
     * Constructs a King with the specified color, initial cell, board, and observer.
     *
     * @param color the color of the king
     * @param cell the initial cell of the king
     * @param board the board containing all pieces
     * @param observer the observer to attach for event notifications
     */
    public King(PlayerColor color, GCell cell, HashMap<GCell, Piece> board, BObserver observer) {
        super(PieceType.KING, color, cell);
        events.attach(observer);
        this.board = board;
    }

    /**
     * Returns whether the king is in check.
     *
     * @return true if the king is in check, false otherwise
     */
    public boolean getIsCheck() {
        return isCheck;
    }

    /**
     * Sets the check state of the king.
     *
     * @param state the new check state
     */
    public void setIsCheck(boolean state) {
        isCheck = state;
    }

    /**
     * Checks if the king is in check by verifying if any opponent piece can move to the king's cell.
     *
     * @return true if the king is in check, false otherwise
     */
    public boolean isCheck() {
        boolean pieceInBetween;
        // Check if at least one opponent piece can reach the king of the current player color
        for (Piece p : board.values()) {
            if (p != null && p.getType() != PieceType.KING && p.getColor() != color && p.canMove(cell)) {
                pieceInBetween = false;

                // Checks if there is a piece in between the opponent and this king
                for (GCell cell : p.path(cell)) {
                    if (board.get(cell) != null) {
                        pieceInBetween = true;
                        break;
                    }
                }
                if (!pieceInBetween)
                    return true;
            }
        }
        return false;
    }

    /**
     * Performs a castling move if the conditions are met.
     *
     * @param to the destination cell for castling
     * @return true if castling is successful, false otherwise
     */
    public boolean castling(GCell to) {
        if (hasMoved || isCheck()) {
            return false;
        }

        boolean onLeft = to.getCol() < cell.getCol();
        int directionCol = cell.directionCol(to);
        GCell rookCell = new GCell(onLeft ? ROOK_INITIAL_COL_LEFT : ROOK_INITIAL_COL_RIGHT, cell.getRow());
        Piece rook = board.get(rookCell);

        if (!(rook instanceof Rook) || rook.getHasMoved()) {
            return false;
        }

        for (int i = 1; i < cell.distanceCol(rookCell); i++) {
            GCell nextCell = new GCell(cell.getCol() + i * directionCol, cell.getRow());
            if (board.get(nextCell) != null || new King(color, nextCell, board, null).isCheck()) {
                return false;
            }
        }

        events.notifyCastling(this, (Rook) rook, directionCol);
        return true;
    }

    /**
     * Returns the path of cells the king will move through to reach the specified cell.
     *
     * @param to the destination cell
     * @return a list of cells representing the path to the destination cell
     */
    @Override
    public List<GCell> path(GCell to) {
        List<GCell> path = new LinkedList<>();

        if (cell.distanceCol(to) == 2) {
            path.add(new GCell(cell.getRow(), cell.getCol() + cell.directionCol(to)));
        }

        return path;
    }

    /**
     * Checks if the king can move to the specified cell.
     *
     * @param to the cell to move to
     * @return true if the king can move to the specified cell, false otherwise
     */
    @Override
    public boolean canMove(GCell to) {
        super.canMove(to);

        if (cell.distanceRow(to) * cell.distanceCol(to) == 1) {
            hasMoved = true;
            return true;
        }

        if (cell.distanceRow(to) + cell.distanceCol(to) == 1) {
            hasMoved = true;
            return true;
        }

        if (cell.distanceCol(to) == 2 && castling(to)) {
            hasMoved = true;
            return false;
        }

        return false;
    }
}