package ch.heigvd.poo.engine.listeners;

import ch.heigvd.poo.chess.PlayerColor;
import ch.heigvd.poo.engine.board.GCell;
import ch.heigvd.poo.engine.pieces.*;

/**
 * The EObserver interface defines methods for observing various events in the game.
 * Implementations of this interface can be used to handle events such as removing a piece,
 * adding a piece, next turn, pop-up updates, and checking if a player is in check.
 *
 * @author : Surbeck Léon
 * @author : Nicolet Victor
 */
public interface EObserver {

    /**
     * Called when a piece is removed from the board.
     *
     * @param cell the cell from which the piece is removed
     */
    void updateRemovePiece(GCell cell);

    /**
     * Called when a piece is added to the board.
     *
     * @param piece the piece that is added to the board
     */
    void updateAddPiece(Piece piece);

    /**
     * Called to update the game to the next turn.
     */
    void updateNextTurn();

    /**
     * Called to update the pop-up with the given piece.
     *
     * @param p the piece to update the pop-up with
     * @return the updated piece
     */
    Piece updatePopUp(Piece p);

    /**
     * Called to update the game state when a player is in check.
     *
     * @param color the color of the player who is in check
     */
    void updateInCheck(PlayerColor color);
}