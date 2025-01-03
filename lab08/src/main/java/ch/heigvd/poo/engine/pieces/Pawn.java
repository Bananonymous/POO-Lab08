package ch.heigvd.poo.engine.pieces;

import ch.heigvd.poo.chess.PieceType;
import ch.heigvd.poo.chess.PlayerColor;
import ch.heigvd.poo.engine.board.GCell;
import ch.heigvd.poo.engine.listeners.BObserver;
import ch.heigvd.poo.engine.listeners.BEventSrc;

import java.util.LinkedList;
import java.util.List;

public class Pawn extends Piece {
    private boolean hasMoved = false;
    private final BEventSrc events = new BEventSrc() {
    };
    private boolean canEnPassant = false;

    public Pawn(PlayerColor color, GCell cell, BObserver observer) {
        super(PieceType.PAWN, color, cell);
        events.attach(observer);
    }

    public boolean getHasMoved() {
        return hasMoved;
    }

    public boolean checkEnPassant(GCell to) {
        int distanceRow = cell.distanceRow(to);
        int distanceCol = cell.distanceCol(to);

        if (distanceCol * distanceRow == 1) {
            Piece pieceTo = null;
            if ((color == PlayerColor.WHITE)) {
                pieceTo = events.notifyPieceTo(this, new GCell(to.getRow(), to.getCol() - 1));
            } else if (color == PlayerColor.BLACK) {
                pieceTo = events.notifyPieceTo(this, new GCell(to.getRow(), to.getCol() + 1));
            }
            if (pieceTo != null && pieceTo.getType() == PieceType.PAWN && ((Pawn) (pieceTo)).getCanEnPassant()) {
                events.notifyEnPassant(pieceTo.getCell());
                return true;
            }
        }
        return false;
    }

    public boolean getCanEnPassant() {
        return canEnPassant;
    }

    public void updateCanEnPassant(int dis) {
        canEnPassant = !hasMoved && dis == 2;
    }

    public void checkPromoted(GCell to) {
        if (to.getRow() == 0 || to.getRow() == 7) {
            Piece pieceTo = events.notifyPieceTo(this, to);

            if (pieceTo == null || pieceTo.getType() != PieceType.KING)
                events.notifyPromotion(this);
        }
    }

    @Override
    public List<GCell> path(GCell to) {
        List<GCell> path = new LinkedList<>();

        if (cell.distanceCol(to) == 2)
            path.add(new GCell(cell.getRow(), cell.getCol() + cell.directionCol(to)));

        return path;
    }

    @Override
    public boolean canMove(GCell to) {
        super.canMove(to);

        int distanceRow = cell.distanceRow(to);
        int distanceCol = to.getCol() - cell.getCol();
        Piece pieceTo = events.notifyPieceTo(this, to);

        if (color == PlayerColor.BLACK) {
            distanceCol = -distanceCol;
        }

        if (cell.getRow() == to.getRow() && distanceCol <= 2 && distanceCol > 0) {

            //checks if it is not the pawn's first move
            if (distanceCol == 2 && hasMoved)
                return false;

            //checks if there is a piece on the destination cell when moving upwards, which is not allowed
            if (pieceTo != null)
                return false;

            updateCanEnPassant(distanceCol);

            //check if promotion is possible
            checkPromoted(to);
            return hasMoved = true;
        }

        //diagonal move when there is an opponent piece
        if (distanceRow == 1 && distanceCol == 1) {

            //Notify the board that we need to know if there is an opponent on the destination cell
            events.notifyPieceTo(this, to);

            //If an opponent is on the diagonal cell, the pawn can move
            if (pieceTo != null && pieceTo.getColor() != color) {
                updateCanEnPassant(distanceCol);
                checkPromoted(to);
                return hasMoved = true;
            }
        }
        return checkEnPassant(to);
    }
}
