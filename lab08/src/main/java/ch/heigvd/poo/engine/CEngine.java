package ch.heigvd.poo.engine;

/**
 * The CEngine class implements the ChessController and EObserver interfaces.
 * It manages the game logic and communicates with the view.
 *
 * @author : Surbeck Léon
 * @author : Nicolet Victor
 */
import ch.heigvd.poo.chess.ChessController;
import ch.heigvd.poo.chess.ChessView;
import ch.heigvd.poo.chess.PlayerColor;
import ch.heigvd.poo.engine.board.GBoard;
import ch.heigvd.poo.engine.board.GCell;
import ch.heigvd.poo.engine.listeners.EObserver;
import ch.heigvd.poo.engine.pieces.*;

public class CEngine implements ChessController, EObserver  {
    private ChessView view;
    private GBoard board;
    private int turn;

    /**
     * Constructs a CEngine object and initializes the board.
     */
    public CEngine() {
        board = new GBoard(this);
    }

    /**
     * Determines the current player's turn based on the turn count.
     *
     * @return the color of the player whose turn it is
     */
    private PlayerColor playerTurn() {
        return turn % 2 == 0 ? PlayerColor.WHITE : PlayerColor.BLACK;
    }

    /**
     * Displays a message indicating the current turn and player.
     */
    private void displayMessage(){
        view.displayMessage("Turn " + turn + " : " + playerTurn() + " player's turn");
    }

    /**
     * Advances to the next turn and updates the display message.
     */
    private void nextTurn(){
        turn++;
        displayMessage();
    }

    /**
     * Updates the game state to the next turn.
     */
    public void updateNextTurn() {
        nextTurn();
    }

    /**
     * Starts the game with the specified view.
     *
     * @param view the view to start the game with
     */
    @Override
    public void start(ChessView view) {
        this.view = view;
        view.startView();
    }

    /**
     * Prompts the user to choose a piece for promotion.
     *
     * @param p the piece to be promoted
     * @return the chosen piece for promotion
     */
    @Override
    public Piece updatePopUp(Piece p) {
        Piece[] promotion = {
                new Queen(p.getColor(), p.getCell()),
                new Rook(p.getColor(), p.getCell()),
                new Bishop(p.getColor(), p.getCell()),
                new Knight(p.getColor(), p.getCell())
        };

        return view.askUser("Promotion", "Choose a piece to promote", promotion);
    }

    /**
     * Starts a new game by clearing the board and resetting the turn count.
     */
    @Override
    public void newGame() {
        for (Piece piece : board.getBoard().values())
            view.removePiece(piece.getCell().getRow(), piece.getCell().getCol());
        board.clearBoard();
        turn = 0;
        displayMessage();
        board.initBoard();
    }

    /**
     * Moves a piece from one cell to another.
     *
     * @param fromRow the starting row
     * @param fromCol the starting column
     * @param toRow the destination row
     * @param toCol the destination column
     * @return true if the move is successful, false otherwise
     */
    @Override
    public boolean move(int fromRow, int fromCol, int toRow, int toCol){
        displayMessage();
        if(board.move(fromRow, fromCol, toRow, toCol, playerTurn())){
            nextTurn();
            return true;
        }
        return false;
    }

    /**
     * Updates the view to add a piece to the board.
     *
     * @param p the piece to add
     */
    @Override
    public void updateAddPiece(Piece p) {
        view.putPiece(p.getType(), p.getColor(), p.getCell().getRow(), p.getCell().getCol());
    }

    /**
     * Updates the view to remove a piece from the board.
     *
     * @param cell the cell of the piece to remove
     */
    @Override
    public void updateRemovePiece(GCell cell) {
        view.removePiece(cell.getRow(), cell.getCol());
    }

    /**
     * Updates the view to indicate that a player is in check.
     *
     * @param color the color of the player in check
     */
    @Override
    public void updateInCheck(PlayerColor color) {
        String colorMsg = color == PlayerColor.WHITE ? "white" : "black";
        view.displayMessage(colorMsg + " is in check");
    }
}