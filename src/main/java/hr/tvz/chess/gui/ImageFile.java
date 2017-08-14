package hr.tvz.chess.gui;

/**
 * Created by Marko on 13.8.2016..
 */
public enum ImageFile {

    BLACK_KING("Chess_kdt45.svg"),
    WHITE_KING("Chess_klt45.svg"),
    BLACK_QUEEN("Chess_qdt45.svg"),
    WHITE_QUEEN("Chess_qlt45.svg"),
    BLACK_ROOK("Chess_rdt45.svg"),
    WHITE_ROOK("Chess_rlt45.svg"),
    BLACK_BISHOP("Chess_bdt45.svg"),
    WHITE_BISHOP("Chess_blt45.svg"),
    BLACK_KNIGHT("Chess_ndt45.svg"),
    WHITE_KNIGHT("Chess_nlt45.svg"),
    BLACK_PAWN("Chess_pdt45.svg"),
    WHITE_PAWN("Chess_plt45.svg");

    private String file;

    ImageFile(String file) {
        this.file = file;
    }

    public String getFile() {
        return file;
    }
}
