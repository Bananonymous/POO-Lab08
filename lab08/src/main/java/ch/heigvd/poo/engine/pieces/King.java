package ch.heigvd.poo.engine.pieces;

import ch.heigvd.poo.chess.PieceType;
import ch.heigvd.poo.chess.PlayerColor;
import ch.heigvd.poo.engine.board.GCell;
import ch.heigvd.poo.engine.listeners.BObserver;
import ch.heigvd.poo.engine.listeners.BEventSrc;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class King extends Piece {
    private static final int ROOK_INITIAL_COL_LEFT = 0;
    private static final int ROOK_INITIAL_COL_RIGHT = 7;
    private boolean hasMoved = false;
    private boolean isCheck;
    private HashMap<GCell, Piece> board;
    private final BEventSrc events = new BEventSrc() {
    };

    public King(PlayerColor color, GCell cell, HashMap<GCell, Piece> board, BObserver observer) {
        super(PieceType.KING, color, cell);
        events.attach(observer);
        this.board = board;
    }

    public boolean getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean state) {
        isCheck = state;
    }

    public boolean isCheck() {
        boolean pieceInBetween;
        //Check if at least one opponent piece can reach the king of the current player color
        for (Piece p : board.values()) {
            if (p != null && p.getType() != PieceType.KING && p.getColor() != color && p.canMove(cell)) {
                pieceInBetween = false;

                //Checks if there is a piece in between the opponent and this king
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
}