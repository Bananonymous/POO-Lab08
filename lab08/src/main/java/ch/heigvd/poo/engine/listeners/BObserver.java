package ch.heigvd.poo.engine.listeners;

import ch.heigvd.poo.engine.board.GCell;
import ch.heigvd.poo.engine.pieces.King;
import ch.heigvd.poo.engine.pieces.Pawn;
import ch.heigvd.poo.engine.pieces.Piece;
import ch.heigvd.poo.engine.pieces.Rook;

/**
 * The BObserver interface defines methods for observing various events in the game.
 * Implementations of this interface can be used to handle events such as piece promotion,
 * en passant, and castling.
 *
 * @author : Surbeck Léon
 * @author : Nicolet Victor
 */
public interface BObserver {

    /**
     * Called when a pawn is promoted.
     *
     * @param pawn the pawn that is being promoted
     */
    void updatePromotion(Pawn pawn);

    /**
     * Called to update the piece to which a pawn is promoted.
     *
     * @param pawn the pawn that is being promoted
     * @param cell the cell where the promotion occurs
     * @return the new piece that the pawn is promoted to
     */
    Piece updatePieceTo(Pawn pawn, GCell cell);

    /**
     * Called when an en passant move is made.
     *
     * @param cell the cell where the en passant move occurs
     */
    void updateEnPassant(GCell cell);

    /**
     * Called when a castling move is made.
     *
     * @param king the king that is castling
     * @param rook the rook that is castling
     * @param direction the direction of the castling move
     */
    void updateCastling(King king, Rook rook, int direction);
}