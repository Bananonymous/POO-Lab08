package ch.heigvd.poo.engine.board;

import ch.heigvd.poo.chess.PlayerColor;
import ch.heigvd.poo.engine.listeners.EObserver;
import ch.heigvd.poo.engine.listeners.EEventSrc;
import ch.heigvd.poo.engine.listeners.BObserver;
import ch.heigvd.poo.engine.pieces.*;

import java.util.HashMap;
import java.util.List;

/**
 * The GBoard class represents the chessboard and manages the pieces on it.
 * It implements the BObserver interface to handle board events.
 *
 * @author : Surbeck Léon
 * @author : Nicolet Victor
 */
public class GBoard implements BObserver {
    private static final int BOARD_SIZE = 8;
    private HashMap<GCell, Piece> board;
    private static final EEventSrc events = new EEventSrc() {
    };
    private Kings kings;

    /**
     * Constructs a GBoard with the specified observer.
     *
     * @param obs the observer to attach for event notifications
     */
    public GBoard(EObserver obs) {
        board = new HashMap<>();
        kings = new Kings(board, this);
        events.attach(obs);
    }

    /**
     * Adds a piece to the board.
     *
     * @param p the piece to add
     */
    public void addPiece(Piece p) {
        board.put(p.getCell(), p);
        events.updateAddPiece(p);
    }

    /**
     * Removes a piece from the board.
     *
     * @param cell the cell of the piece to remove
     * @return the removed piece
     */
    public Piece removePiece(GCell cell) {
        Piece p = board.remove(cell);
        events.updateRemovePiece(cell);
        return p;
    }

    /**
     * Moves a piece to a new cell.
     *
     * @param to the destination cell
     * @param p the piece to move
     * @return the piece that was at the destination cell, if any
     * @throws NullPointerException if the piece or destination cell is null
     */
    public Piece movePiece(GCell to, Piece p) {
        if (p == null || to == null) {
            throw new NullPointerException("The from/to position for the movement is null");
        }

        Piece pieceTo = board.get(to);
        if (pieceTo != null) {
            removePiece(to);
        }

        removePiece(p.getCell());

        p.setCell(to);
        addPiece(p);

        return pieceTo;
    }

    /**
     * Clears the board of all pieces.
     */
    public void clearBoard() {
        board.clear();
    }

    /**
     * Returns the current state of the board.
     *
     * @return a HashMap representing the board
     */
    public HashMap<GCell, Piece> getBoard() {
        return board;
    }

    /**
     * Resets the en passant target for pawns of the specified color.
     *
     * @param color the color of the pawns to reset
     */
    public void resetTargetPassant(PlayerColor color) {
        for (Piece piece : board.values())
            if (piece instanceof Pawn && piece.getColor() != color) ((Pawn) piece).updateCanEnPassant(0);
    }

    /**
     * Checks if the path between cells is empty.
     *
     * @param path the list of cells to check
     * @return true if the path is empty, false otherwise
     */
    private boolean isEmptyBetween(List<GCell> path) {
        for (GCell cell : path)
            if (board.get(cell) != null) return false;

        return true;
    }

    /**
     * Moves a piece from one cell to another if the move is valid.
     *
     * @param fromRow the starting row
     * @param fromCol the starting column
     * @param toRow the destination row
     * @param toCol the destination column
     * @param color the color of the player making the move
     * @return true if the move is successful, false otherwise
     */
    public boolean move(int fromRow, int fromCol, int toRow, int toCol, PlayerColor color) {
        GCell from = new GCell(fromRow, fromCol);
        GCell to = new GCell(toRow, toCol);

        Piece p = board.get(from);
        Piece toP = board.get(to);

        if (p == null || (toP != null && toP.getColor() == color)) return false;

        if (p.getColor() == color && p.canMove(to) && isEmptyBetween(p.path(to))) {

            Piece eaten = movePiece(to, board.get(p.getCell()));

            King inCheck = kings.IsCheck();
            if (inCheck != null) {

                if (inCheck.getIsCheck() || inCheck.getColor() == color) {
                    movePiece(from, board.get(to));

                    if (eaten != null) addPiece(eaten);

                    return false;
                }
                inCheck.setIsCheck(true);
            }
            resetTargetPassant(color);
            return true;
        }
        return false;
    }

    /**
     * Initializes the board with the starting positions of all pieces.
     */
    public void initBoard() {
        // Re-initialize the kings
        kings = new Kings(board, this);

        for (PlayerColor color : PlayerColor.values()) {
            int line = color == PlayerColor.WHITE ? 0 : BOARD_SIZE - 1;

            addPiece(new Rook(color, new GCell(0, line)));
            addPiece(new Knight(color, new GCell(1, line)));
            addPiece(new Bishop(color, new GCell(2, line)));
            addPiece(new Queen(color, new GCell(3, line)));
            addPiece(new Bishop(color, new GCell(5, line)));
            addPiece(new Knight(color, new GCell(6, line)));
            addPiece(new Rook(color, new GCell(7, line)));

            // Get the corresponding colored king to put it in the board
            King king = kings.getKing(color);
            king.setCell(new GCell(4, line));
            addPiece(king);

            int pawnLine = (color == PlayerColor.WHITE ? 1 : 6);
            for (int j = 0; j < BOARD_SIZE; j++) {
                Pawn p = new Pawn(color, new GCell(j, pawnLine), this);
                addPiece(p);
            }
        }
    }

    /**
     * Updates the board when a pawn is promoted.
     *
     * @param p the pawn to promote
     * @throws NullPointerException if the pawn to promote is null
     */
    @Override
    public void updatePromotion(Pawn p) {
        if (p == null) throw new NullPointerException("The pawn to promote is null");

        Piece prom = events.updatePopUp(p);

        if (board.remove(prom.getCell()) == null) throw new NullPointerException("The piece to remove is null");

        board.remove(p.getCell());
        board.put(prom.getCell(), prom);
    }

    /**
     * Updates the board to get the piece at a specific cell for en passant.
     *
     * @param pawn the pawn performing en passant
     * @param cell the cell to move to
     * @return the piece at the specified cell
     * @throws NullPointerException if the pawn or cell is null
     */
    @Override
    public Piece updatePieceTo(Pawn pawn, GCell cell) {
        if (pawn == null) throw new NullPointerException("The pawn to move is null");

        if (cell == null) throw new NullPointerException("The cell to move to is null");

        return board.get(cell);
    }

    /**
     * Updates the board to remove a piece for en passant.
     *
     * @param cell the cell to remove the piece from
     * @throws NullPointerException if the cell is null
     */
    @Override
    public void updateEnPassant(GCell cell) {
        if (cell == null) throw new NullPointerException("The cell to move to is null");

        board.remove(cell);
    }

    /**
     * Updates the board for castling.
     *
     * @param king the king performing castling
     * @param rook the rook involved in castling
     * @param direction the direction of castling
     */
    @Override
    public void updateCastling(King king, Rook rook, int direction) {
        int distance = direction + king.getCell().getRow();

        removePiece(king.getCell());
        king.setCell(new GCell(king.getCell().getRow() + direction * 2, king.getCell().getCol()));
        addPiece(king);

        removePiece(rook.getCell());
        rook.setCell(new GCell(distance, rook.getCell().getCol()));
        addPiece(rook);

        events.updateNextTurn();
    }

    /**
     * The Kings class manages the kings on the board and checks if they are in check.
     */
    private static class Kings {
        private final King whiteKing;
        private final King blackKing;

        /**
         * Constructs a Kings object with the specified board and observer.
         *
         * @param board the board containing all pieces
         * @param obs the observer to attach for event notifications
         */
        Kings(HashMap<GCell, Piece> board, GBoard obs) {
            whiteKing = new King(PlayerColor.WHITE, null, board, obs);
            blackKing = new King(PlayerColor.BLACK, null, board, obs);
        }

        /**
         * Returns the king of the specified color.
         *
         * @param color the color of the king
         * @return the king of the specified color
         */
        King getKing(PlayerColor color) {
            return color == PlayerColor.WHITE ? whiteKing : blackKing;
        }

        /**
         * Checks if either king is in check.
         *
         * @return the king that is in check, or null if neither king is in check
         */
        King IsCheck() {
            if (whiteKing.isCheck()) {
                blackKing.setIsCheck(false);
                events.updateInCheck(whiteKing.getColor());
                return whiteKing;
            }

            if (blackKing.isCheck()) {
                whiteKing.setIsCheck(false);
                events.updateInCheck(blackKing.getColor());
                return blackKing;
            }
            whiteKing.setIsCheck(false);
            blackKing.setIsCheck(false);
            return null;
        }
    }
}