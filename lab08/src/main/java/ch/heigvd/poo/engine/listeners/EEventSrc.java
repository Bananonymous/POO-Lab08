package ch.heigvd.poo.engine.listeners;

import ch.heigvd.poo.chess.PlayerColor;
import ch.heigvd.poo.engine.board.GCell;
import ch.heigvd.poo.engine.pieces.*;

/**
 * The EEventSrc class is an abstract class that serves as a source of various game events.
 * It allows attaching an observer and notifying it of different events such as removing a piece,
 * adding a piece, next turn, pop-up updates, and checking if a player is in check.
 *
 * @author : Surbeck Léon
 * @author : Nicolet Victor
 */
public abstract class EEventSrc {
    private EObserver obs;

    /**
     * Attaches an observer to this event source.
     *
     * @param observer the observer to attach
     */
    public void attach(EObserver observer) {
        obs = observer;
    }

    /**
     * Notifies the observer of a piece removal event.
     *
     * @param cell the cell from which the piece is removed
     */
    public void updateRemovePiece(GCell cell) {
        obs.updateRemovePiece(cell);
    }

    /**
     * Notifies the observer of a piece addition event.
     *
     * @param piece the piece that is added to the board
     */
    public void updateAddPiece(Piece piece) {
        obs.updateAddPiece(piece);
    }

    /**
     * Notifies the observer to update the game to the next turn.
     */
    public void updateNextTurn() {
        obs.updateNextTurn();
    }

    /**
     * Notifies the observer to update the pop-up with the given piece.
     *
     * @param p the piece to update the pop-up with
     * @return the updated piece
     */
    public Piece updatePopUp(Piece p) {
        return obs.updatePopUp(p);
    }

    /**
     * Notifies the observer of a player being in check.
     *
     * @param color the color of the player who is in check
     */
    public void updateInCheck(PlayerColor color) {
        obs.updateInCheck(color);
    }
}