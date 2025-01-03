package ch.heigvd.poo.engine.listeners;

import ch.heigvd.poo.engine.board.GCell;
import ch.heigvd.poo.engine.pieces.King;
import ch.heigvd.poo.engine.pieces.Pawn;
import ch.heigvd.poo.engine.pieces.Piece;
import ch.heigvd.poo.engine.pieces.Rook;

/**
 * The BEventSrc class is an abstract class that serves as a source of various game events.
 * It allows attaching an observer and notifying it of different events such as promotion,
 * en passant, and castling.
 *
 * @author : Surbeck Léon
 * @author : Nicolet Victor
 */
public abstract class BEventSrc {
    private BObserver obs = null;

    /**
     * Attaches an observer to this event source.
     *
     * @param mobs the observer to attach
     */
    public void attach(BObserver mobs) {
        obs = mobs;
    }

    /**
     * Notifies the observer of a pawn promotion event.
     *
     * @param pawn the pawn that is being promoted
     */
    public void notifyPromotion(Pawn pawn) {
        obs.updatePromotion(pawn);
    }

    /**
     * Notifies the observer to update the piece to which a pawn is promoted.
     *
     * @param pawn the pawn that is being promoted
     * @param cell the cell where the promotion occurs
     * @return the new piece that the pawn is promoted to
     */
    public Piece notifyPieceTo(Pawn pawn, GCell cell) {
        return obs.updatePieceTo(pawn, cell);
    }

    /**
     * Notifies the observer of an en passant move.
     *
     * @param cell the cell where the en passant move occurs
     */
    public void notifyEnPassant(GCell cell) {
        obs.updateEnPassant(cell);
    }

    /**
     * Notifies the observer of a castling move.
     *
     * @param king the king that is castling
     * @param rook the rook that is castling
     * @param direction the direction of the castling move
     */
    public void notifyCastling(King king, Rook rook, int direction) {
        obs.updateCastling(king, rook, direction);
    }
}