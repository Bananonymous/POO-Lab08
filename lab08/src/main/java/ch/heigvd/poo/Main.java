package ch.heigvd.poo;

import ch.heigvd.poo.chess.ChessController;
import ch.heigvd.poo.chess.ChessView;
import ch.heigvd.poo.chess.views.console.ConsoleView;
import ch.heigvd.poo.chess.views.gui.GUIView;
import ch.heigvd.poo.engine.CEngine;

public class Main {
    public static void main(String[] args) {
        ChessController controller = new CEngine();
        ChessView view = new GUIView(controller);
        controller.start(view);
    }
}