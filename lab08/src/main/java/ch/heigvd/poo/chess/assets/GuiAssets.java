package ch.heigvd.poo.chess.assets;

import ch.heigvd.poo.chess.PieceType;
import ch.heigvd.poo.chess.PlayerColor;
import ch.heigvd.poo.chess.views.gui.GUIView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GuiAssets {
  public static void loadAssets(GUIView view) {
    try {
      view.registerResource(PieceType.ROOK, PlayerColor.BLACK, GUIView.createResource(assetsImage("rook_black.png")));
      view.registerResource(PieceType.ROOK, PlayerColor.WHITE, GUIView.createResource(assetsImage("rook_white.png")));

      view.registerResource(PieceType.PAWN, PlayerColor.BLACK, GUIView.createResource(assetsImage("pawn_black.png")));
      view.registerResource(PieceType.PAWN, PlayerColor.WHITE, GUIView.createResource(assetsImage("pawn_white.png")));

      view.registerResource(PieceType.KNIGHT, PlayerColor.BLACK, GUIView.createResource(assetsImage("knight_black.png")));
      view.registerResource(PieceType.KNIGHT, PlayerColor.WHITE, GUIView.createResource(assetsImage("knight_white.png")));

      view.registerResource(PieceType.BISHOP, PlayerColor.BLACK, GUIView.createResource(assetsImage("bishop_black.png")));
      view.registerResource(PieceType.BISHOP, PlayerColor.WHITE, GUIView.createResource(assetsImage("bishop_white.png")));

      view.registerResource(PieceType.QUEEN, PlayerColor.BLACK, GUIView.createResource(assetsImage("queen_black.png")));
      view.registerResource(PieceType.QUEEN, PlayerColor.WHITE, GUIView.createResource(assetsImage("queen_white.png")));

      view.registerResource(PieceType.KING, PlayerColor.BLACK, GUIView.createResource(assetsImage("king_black.png")));
      view.registerResource(PieceType.KING, PlayerColor.WHITE, GUIView.createResource(assetsImage("king_white.png")));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static BufferedImage assetsImage(String imageName) throws IOException {
//    File temp = new File("/home/athena/Insync/POO/POO-Lab08/lab08/src/main/java/ch/heigvd/poo/chess/assets/images/" + imageName);
//    System.out.println(temp);
    return ImageIO.read(new File("/home/boletellus/heig-vd/poo/labo/POO-Lab08/lab08/src/main/java/ch/heigvd/poo/chess/assets/images/" + imageName));
//    return ImageIO.read(GuiAssets.class.getResource("images/" + imageName));
  }
}
