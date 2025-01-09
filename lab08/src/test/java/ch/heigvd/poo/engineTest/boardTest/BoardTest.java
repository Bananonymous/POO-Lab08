package ch.heigvd.poo.engineTest.boardTest;

import ch.heigvd.poo.chess.PlayerColor;
import ch.heigvd.poo.engine.board.GBoard;
import ch.heigvd.poo.engine.board.GCell;
import ch.heigvd.poo.engine.listeners.EObserver;
import ch.heigvd.poo.engine.pieces.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest implements EObserver {
    PlayerColor white = PlayerColor.WHITE;
    PlayerColor black = PlayerColor.BLACK;

    /**
     * Function that create a custom board depending on the test
     *
     * @param board  the board to fill
     * @param pieces the pieces to add
     */
    public void createCustomBoard(GBoard board, Piece... pieces) {
        board.initBoard();
        board.clearBoard();

        for (Piece piece : pieces)
            board.addPiece(piece);
    }

    @Test
    @DisplayName("test if the board correctly moves a piece")
    public void testMove() {
        GBoard board = new GBoard(this);
        GCell from = new GCell(1, 1);
        GCell to = new GCell(2, 2);
        Queen queen = new Queen(white, from);
        createCustomBoard(board, queen);

        board.move(queen.getCell().getRow(), queen.getCell().getCol(), to.getRow(), to.getCol(), white);
        assertNotEquals(queen.getCell(), from, "the piece should have moved");
        assertEquals(queen.getCell(), to, "the piece should have moved to the new cell");
    }

    @Test
    @DisplayName("test if addPiece() adds a piece to the board")
    public void testAddPiece() {
        GBoard board = new GBoard(this);
        Pawn pawn = new Pawn(white, new GCell(0, 0), board);
        createCustomBoard(board, pawn);
        assertEquals(board.getBoard().get(new GCell(0, 0)), pawn, "the piece should have been added to the board");
    }

    @Test
    @DisplayName("test if removePiece() removes a piece from the board")
    public void testRemovePiece() {
        GBoard board = new GBoard(this);
        Pawn pawn = new Pawn(white, new GCell(0, 0), board);
        createCustomBoard(board, pawn);
        board.removePiece(pawn.getCell());
        assertNull(board.getBoard().get(new GCell(0, 0)), "the piece should have been removed from the board");
    }


    @Test
    @DisplayName("test if clear() removes all pieces from the board")
    public void testClear() {
        GBoard board = new GBoard(this);
        Piece piece1 = new Rook(white, new GCell(0, 0));
        Piece piece2 = new Rook(white, new GCell(1, 1));
        createCustomBoard(board, piece1, piece2);

        //check if the pieces are on the board
        assertEquals(board.getBoard().get(new GCell(0, 0)), piece1, "the piece should have been added to the board");
        assertEquals(board.getBoard().get(new GCell(1, 1)), piece2, "the piece should have been added to the board");

        board.clearBoard();
        //check if the pieces are removed from the board
        assertNull(board.getBoard().get(new GCell(0, 0)), "the piece should have been removed from the board");
        assertNull(board.getBoard().get(new GCell(1, 1)), "the piece should have been removed from the board");
    }

    @Test
    @DisplayName("test isCheck() returns true if the king is in check")
    public void testIsCheck() {
        GBoard board = new GBoard(this);
        King king = new King(white, new GCell(0, 0), board.getBoard(), board);
        Queen queen = new Queen(PlayerColor.BLACK, new GCell(1, 1));
        createCustomBoard(board, king, queen);

        assertTrue(king.isCheck(), "the king should be in check");
    }

    @Test
    @DisplayName("test isCheck() returns false if the king is not in check")
    public void testIsCheckFalse() {
        GBoard board = new GBoard(this);
        King king = new King(white, new GCell(0, 0), board.getBoard(), board);
        Queen queen = new Queen(PlayerColor.BLACK, new GCell(1, 2));
        createCustomBoard(board, king, queen);

        assertFalse(king.isCheck(), "the king should not be in check");
    }

    @Test
    @DisplayName("test if a piece checks only a piece of the opposite color")
    public void testIsCheckOnlyOppositeColor() {
        GBoard board = new GBoard(this);
        GCell from = new GCell(1, 1);
        GCell checkable = new GCell(2, 2);
        Bishop whiteBishop = new Bishop(black, from);
        Bishop blackBishop = new Bishop(white, checkable);
        createCustomBoard(board, whiteBishop, blackBishop);

        //checks if the bishop can move to the cell
        assertTrue(board.move(whiteBishop.getCell().getRow(), whiteBishop.getCell().getCol(), checkable.getRow(), checkable.getCol(), whiteBishop.getColor()), "the bishop should be able to move to the cell because the cell is occupied by a piece of the opposite color");

        GCell notCheckable = new GCell(3, 3);
        Bishop whiteBishop2 = new Bishop(white, from);
        Bishop notCheckableBishop = new Bishop(white, notCheckable);
        createCustomBoard(board, whiteBishop2, notCheckableBishop);

        //checks if the bishop cannot move to the cell
        assertFalse(board.move(whiteBishop2.getCell().getRow(), whiteBishop2.getCell().getCol(), notCheckable.getRow(), notCheckable.getCol(), whiteBishop2.getColor()), "the bishop should not be able to move to the cell because the cell is occupied by a piece of the same color");

    }

    @Test
    @DisplayName("test if the bishop correctly moves")
    public void testBishopLegalMove() {
        GBoard board = new GBoard(this);
        GCell from = new GCell(1, 1);
        GCell to = new GCell(2, 2);
        Bishop whiteBishop = new Bishop(white, new GCell(1, 1));

        createCustomBoard(board, whiteBishop);
        assertTrue(board.move(whiteBishop.getCell().getRow(), whiteBishop.getCell().getCol(), to.getRow(), to.getCol(), whiteBishop.getColor()), "the bishop should be able to move diagonally");

        GCell from2 = new GCell(3, 3);
        GCell to2 = new GCell(0, 6);
        Bishop whiteBishop2 = new Bishop(white, from2);

        createCustomBoard(board, whiteBishop2);
        assertTrue(board.move(whiteBishop2.getCell().getRow(), whiteBishop2.getCell().getCol(), to2.getRow(), to2.getCol(), whiteBishop2.getColor()), "the bishop should be able to move diagonally");

    }

    @Test
    @DisplayName("test if the bishop cannot move horizontally or vertically")
    public void testBishopIllegalMove() {
        GBoard board = new GBoard(this);
        Bishop whiteBishop = new Bishop(white, new GCell(1, 1));
        createCustomBoard(board, whiteBishop);

        //white bishop
        //horizontal move
        assertFalse(board.move(whiteBishop.getCell().getRow(), whiteBishop.getCell().getCol(), 2, 1, whiteBishop.getColor()), "the bishop should not be able to move horizontally");
        //vertical move
        assertFalse(board.move(whiteBishop.getCell().getRow(), whiteBishop.getCell().getCol(), 1, 2, whiteBishop.getColor()), "the bishop should not be able to move vertically");

    }

    @Test
    @DisplayName("test pieces between moves")
    public void testBishopPiecesBetweenMoves() {
        GBoard board = new GBoard(this);
        GCell from = new GCell(1, 1);
        GCell between = new GCell(2, 2);
        GCell to = new GCell(3, 3);
        Bishop whiteBishop = new Bishop(white, from);
        Bishop blackBishop = new Bishop(black, between);

        createCustomBoard(board, whiteBishop, blackBishop);

        // Test that white bishop cannot move over black bishop
        assertFalse(board.move(whiteBishop.getCell().getRow(), whiteBishop.getCell().getCol(), to.getRow(), to.getCol(), whiteBishop.getColor()), "white bishop cannot move because there is a piece between the two cells")
        ;
    }

    @Test
    @DisplayName("test if the knight correctly moves")
    public void testKnightLegalMove() {
        GBoard board = new GBoard(this);
        Knight whiteKnight = new Knight(white, new GCell(2, 7));
        Knight blackKnight = new Knight(black, new GCell(1, 1));
        createCustomBoard(board, whiteKnight, blackKnight);

        // Test valid L-shaped move for knight
        //white knight
        assertTrue(board.move(whiteKnight.getCell().getRow(), whiteKnight.getCell().getCol(), 4, 6, whiteKnight.getColor()), "The white Knight did a legal move");
        //black knight
        assertTrue(board.move(blackKnight.getCell().getRow(), blackKnight.getCell().getCol(), 0, 3, blackKnight.getColor()), "The black Knight did a legal move");
    }

    @Test
    @DisplayName("test if the knight did an illegal move")
    public void testKnightIllegalMove() {
        GBoard board = new GBoard(this);
        Knight blackKnight = new Knight(black, new GCell(1, 1));

        //horizontal move
        assertFalse(board.move(blackKnight.getCell().getRow(), blackKnight.getCell().getCol(), 2, 1, blackKnight.getColor()), "The black Knight did an illegal move");
        //vertical move
        assertFalse(board.move(blackKnight.getCell().getRow(), blackKnight.getCell().getCol(), 1, 2, blackKnight.getColor()), "The black Knight did an illegal move");
        //diagonal move
        assertFalse(board.move(blackKnight.getCell().getRow(), blackKnight.getCell().getCol(), 2, 2, blackKnight.getColor()), "The black Knight did an illegal move");
        //move of three cases
        assertFalse(board.move(blackKnight.getCell().getRow(), blackKnight.getCell().getCol(), 4, 1, blackKnight.getColor()), "The black Knight did an illegal move");
    }

    //Castling tests
    @Test
    @DisplayName("test if the king can castle")
    public void testValidCastling() {
        GBoard board = new GBoard(this);
        King king = new King(white, new GCell(4, 0), board.getBoard(), board);
        Rook rook = new Rook(white, new GCell(7, 0));
        GCell to = new GCell(6, 0);
        createCustomBoard(board, king, rook);

        //use castling method instead of move because the king is moved by updateCastling

        // Test that white king can castle (castling on the right side)
        assertTrue(king.castling(to) && king.getCell().equals(to) && rook.getCell().equals(new GCell(5, 0)), "king should be able to move during castling");

        Rook rook2 = new Rook(white, new GCell(0, 0));
        King king2 = new King(white, new GCell(4, 0), board.getBoard(), board);
        GCell to2 = new GCell(2, 0);
        createCustomBoard(board, king2, rook2);

        // Test that white king can castle (castling on the left side)
        assertTrue(king2.castling(to2) && king2.getCell().equals(to2) && rook2.getCell().equals(new GCell(3, 0)), "king should be able to move during castling");
    }


    @Test
    @DisplayName("Check if the king is in check during castling")
    public void testCastlingCheck() {
        GBoard board = new GBoard(this);
        Queen queen = new Queen(white, new GCell(1, 4));
        King king = new King(black, new GCell(4, 7), board.getBoard(), board);
        Rook rook = new Rook(black, new GCell(0, 7));
        GCell to = new GCell(2, 7);

        createCustomBoard(board, queen, king, rook);

        assertFalse(king.castling(to) || king.getCell().equals(to) || rook.getCell().equals(new GCell(5, 7)), "king should not be able to castle because he is in check");

    }

    @Test
    @DisplayName("Check illegal castling when the king or the rook have already moved")
    public void testMovedPiecesCastling() {
        GBoard board = new GBoard(this);
        GCell from = new GCell(4, 0);
        GCell to = new GCell(6, 0);
        King king = new King(white, from, board.getBoard(), board);
        Rook rook = new Rook(white, new GCell(7, 0));
        createCustomBoard(board, king, rook);

        // Test that white king cannot castle because the king has already moved

        // move the king
        board.move(king.getCell().getRow(), king.getCell().getCol(), 5, 0, king.getColor());

        //move the king back
        board.move(5, 0, 4, 0, king.getColor());

        //try to castle
        assertFalse(king.castling(to) || king.getCell().equals(to) || rook.getCell().equals(new GCell(5, 0)), "king should not be able to castle because he has already moved");

        // Test that white king cannot castle because the rook has already moved
        GCell to2 = new GCell(2, 0);
        King king2 = new King(white, from, board.getBoard(), board);
        Rook rook2 = new Rook(white, new GCell(0, 0));
        createCustomBoard(board, king, rook);

        // move the rook
        board.move(rook2.getCell().getRow(), rook2.getCell().getCol(), 1, 0, rook2.getColor());

        //move the rook back
        board.move(1, 0, 0, 0, rook2.getColor());
        assertFalse(king.castling(to2) || king2.getCell().equals(to2) || rook2.getCell().equals(new GCell(3, 0)), "king should not be able to castle because the rook has already moved");

        // Test that white king cannot castle because there is a piece between the king and the rook
        Queen queen = new Queen(white, new GCell(5, 0));
        createCustomBoard(board, king, rook, queen);
        assertFalse(king.castling(to), "king should not be able to castle because there is a piece between the king and the rook");


    }

    @Test
    @DisplayName("Check pieces between the king and the rook during castling")
    public void testPiecesBetweenCastling() {
        GBoard board = new GBoard(this);
        GCell to = new GCell(6, 0);
        GCell to2 = new GCell(2, 0);
        King king = new King(white, new GCell(4, 0), board.getBoard(), board);
        Rook rookRight = new Rook(white, new GCell(7, 0));
        Rook rookLeft = new Rook(white, new GCell(0, 0));
        Knight knight = new Knight(white, new GCell(5, 0));
        Knight knight2 = new Knight(white, new GCell(1, 0));
        createCustomBoard(board, king, rookRight, rookLeft, knight, knight2);

        // Test that white king cannot castle because there is a piece between the king and the rook
        assertFalse(king.castling(to) || king.getCell().equals(to) || rookRight.getCell().equals(new GCell(5, 0)), "king should not be able to castle because there is a piece between the king and the rook");

        // Test that white king cannot castle because there is a piece between the king and the rook
        assertFalse(king.castling(to2) || king.getCell().equals(to2) || rookLeft.getCell().equals(new GCell(3, 0)), "king should not be able to castle because there is a piece between the king and the rook");
    }


    @Test
    @DisplayName("test if the queen correctly moves")
    public void testQueenLegalMove() {
        GBoard board = new GBoard(this);

        Queen whiteQueen = new Queen(white, new GCell(4, 4));

        createCustomBoard(board, whiteQueen);

        // Test valid diagonal move for queen
        assertTrue(board.move(whiteQueen.getCell().getRow(), whiteQueen.getCell().getCol(), 5, 5, whiteQueen.getColor()), "Queen : Legal move");
        assertTrue(board.move(5, 5, 7, 3, whiteQueen.getColor()), "Queen : Legal move");

        // Test valid horizontal move for queen
        assertTrue(board.move(whiteQueen.getCell().getRow(), whiteQueen.getCell().getCol(), 1, 3, whiteQueen.getColor()), "Queen : Legal move");

        // Test valid vertical move for queen
        //white queen
        assertTrue(board.move(whiteQueen.getCell().getRow(), whiteQueen.getCell().getCol(), 1, 1, whiteQueen.getColor()), "Queen : Legal move");
    }

    @Test
    @DisplayName("test if the queen did illegal moves")
    public void testQueenIllegalMove() {
        GBoard board = new GBoard(this);

        Queen whiteQueen = new Queen(white, new GCell(2, 2));

        // Test invalid move for queen
        //move right and up
        assertFalse(board.move(whiteQueen.getCell().getRow(), whiteQueen.getCell().getCol(), 3, 4, whiteQueen.getColor()), "Queen : Illegal move");

        //move left and down
        assertFalse(board.move(whiteQueen.getCell().getRow(), whiteQueen.getCell().getCol(), 0, 1, whiteQueen.getColor()), "Queen : Illegal move");


    }


    @Test
    @DisplayName("test pieces between moves for Queen")
    public void testPiecesBetweenMovesForQueen() {
        GBoard board = new GBoard(this);
        GCell from = new GCell(1, 1);
        GCell between = new GCell(2, 2);
        GCell to = new GCell(3, 3);

        Queen whiteQueen = new Queen(white, from);
        Knight whiteKnight = new Knight(white, between);
        createCustomBoard(board, whiteQueen, whiteKnight);

        // Test that white queen cannot move to cell(3,3) because there is a piece between the two cells
        assertFalse(board.move(whiteQueen.getCell().getRow(), whiteQueen.getCell().getCol(), to.getRow(), to.getCol(), whiteQueen.getColor()), "Queen : Illegal move");
    }


    //Rook tests

    @Test
    @DisplayName("test if the rook correctly moves")
    public void testRookLegalMove() {
        GBoard board = new GBoard(this);
        Rook whiteRook = new Rook(white, new GCell(0, 7));

        createCustomBoard(board, whiteRook);

        // Test valid horizontal move for rook
        //white rook
        assertTrue(board.move(whiteRook.getCell().getRow(), whiteRook.getCell().getCol(), 1, 7, whiteRook.getColor()), "The white rook should be able to move horizontally");

        // Test valid vertical move for rook
        //white rook
        assertTrue(board.move(whiteRook.getCell().getRow(), whiteRook.getCell().getCol(), 1, 2, whiteRook.getColor()), "The white rook should be able to move vertically");
    }

    @Test
    public void testRookIllegalMove() {
        GBoard board = new GBoard(this);
        Rook whiteRook = new Rook(white, new GCell(0, 0));
        createCustomBoard(board, whiteRook);

        // Test invalid diagonal move for rook
        //white rook
        assertFalse(board.move(whiteRook.getCell().getRow(), whiteRook.getCell().getCol(), 1, 1, whiteRook.getColor()), "white rook cannot move diagonally");

        // Test invalid L shape move for rook
        //black rook
        assertFalse(board.move(whiteRook.getCell().getRow(), whiteRook.getCell().getCol(), 1, 2, whiteRook.getColor()), "black rook cannot move in L shape");
    }

    @Test
    @DisplayName("test pieces between moves for Rook")
    public void testPiecesBetweenMovesForRook() {
        GBoard board = new GBoard(this);

        GCell from = new GCell(1, 1);
        GCell between = new GCell(1, 2);
        GCell to = new GCell(1, 3);

        Rook whiteRook = new Rook(white, from);
        Pawn blackPawn = new Pawn(black, between, board);
        createCustomBoard(board, whiteRook, blackPawn);

        // Test that white rook cannot move to cell(3,1) because there is a piece between the two cells
        assertFalse(board.move(whiteRook.getCell().getRow(), whiteRook.getCell().getCol(), to.getRow(), to.getCol(), whiteRook.getColor()), "white rook cannot move because there is a piece between the two cells");
    }

    @Test
    @DisplayName("test if the pawn correctly moves forward")
    public void testPawnLegalMove() {
        GBoard board = new GBoard(this);
        Pawn whitePawn = new Pawn(white, new GCell(0, 1), board);
        createCustomBoard(board, whitePawn);

        // Test valid vertical move for pawn
        //white pawn
        assertTrue(board.move(whitePawn.getCell().getRow(), whitePawn.getCell().getCol(), 0, 2, whitePawn.getColor()), "white pawn should be able to move forward");
    }

    @Test
    @DisplayName("test if the pawn cannot move horizontally")
    public void testIllegalHorizontalMove() {
        GBoard board = new GBoard(this);
        Pawn whitePawn = new Pawn(white, new GCell(0, 1), board);
        createCustomBoard(board, whitePawn);

        // Test invalid horizontal move for pawn
        assertFalse(board.move(whitePawn.getCell().getRow(), whitePawn.getCell().getCol(), 0, 4, whitePawn.getColor()), "white pawn cannot move horizontally with a distance > 2");

        //Test other invalid move
        assertFalse(board.move(whitePawn.getCell().getRow(), whitePawn.getCell().getCol(), 1, 2, whitePawn.getColor()), "white pawn should not be able to reach this cell");
    }

    @Test
    @DisplayName("test if the pawn cannot move backwards")
    public void testPawnIllegalBackwardMove() {
        GBoard board = new GBoard(this);
        Pawn whitePawn = new Pawn(white, new GCell(0, 1), board);
        createCustomBoard(board, whitePawn);

        // Test invalid backward move for pawn
        assertFalse(board.move(whitePawn.getCell().getRow(), whitePawn.getCell().getCol(), 0, 0, whitePawn.getColor()), "white pawn cannot move backwards");
    }

    @Test
    @DisplayName("test that pawn second move cannot move two spaces forward")
    public void testIllegalSecondMove() {
        GBoard board = new GBoard(this);
        GCell cell = new GCell(0, 1);
        GCell cell1 = new GCell(0, 3);
        GCell cell2 = new GCell(0, 5);
        Pawn whitePawn = new Pawn(white, cell, board);

        createCustomBoard(board, whitePawn);
        board.move(whitePawn.getCell().getRow(), whitePawn.getCell().getCol(), cell1.getRow(), cell1.getCol(), whitePawn.getColor());

        // Test invalid second move for pawn
        assertFalse(board.move(whitePawn.getCell().getRow(), whitePawn.getCell().getCol(), cell2.getRow(), cell2.getCol(), whitePawn.getColor()), "white pawn cannot move two spaces in second move");
    }

    @Test
    @DisplayName("test pieces between moves")
    public void testPiecesBetweenMoves() {
        GBoard board = new GBoard(this);
        GCell from = new GCell(0, 1);
        GCell between = new GCell(0, 2);
        GCell to = new GCell(0, 3);

        Pawn whitePawn = new Pawn(white, from, board);
        Pawn blackPawn = new Pawn(black, between, board);
        createCustomBoard(board, whitePawn, blackPawn);

        // Test invalid second move for pawn
        assertFalse(board.move(whitePawn.getCell().getRow(), whitePawn.getCell().getCol(), to.getRow(), to.getCol(), whitePawn.getColor()), "white pawn cannot move because there is a piece between the two cells");
    }

    @Test
    @DisplayName("Test enpassant")
    public void testEnPassant() {
        GBoard board = new GBoard(this);
        Pawn whitePawn = new Pawn(white, new GCell(0, 1), board);
        Pawn blackPawn = new Pawn(black, new GCell(1, 3), board);

        createCustomBoard(board, whitePawn, blackPawn);
        GCell to = new GCell(0, 2);

        //simulate white move forward
        board.move(whitePawn.getCell().getRow(), whitePawn.getCell().getCol(), 0, 3, whitePawn.getColor());

        //test enpassant
        assertTrue(board.move(blackPawn.getCell().getRow(), blackPawn.getCell().getCol(), to.getRow(), to.getCol(), blackPawn.getColor()), "white pawn should be able to do enpassant");
    }

    //leave empty
    @Override
    public void updateRemovePiece(GCell cell) {
    }

    @Override
    public void updateAddPiece(Piece piece) {
    }

    @Override
    public void updateNextTurn() {
    }

    @Override
    public Piece updatePopUp(Piece p) {
        return null;
    }

    @Override
    public void updateInCheck(PlayerColor color) {
        String colorMsg = color == PlayerColor.WHITE ? "white" : "black";
        System.out.println(colorMsg + " king is in check");
    }
}
