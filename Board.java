package lk.ijse.dep.service;

public interface Board {
    int NUM_OF_ROWS=5;
    int NUM_OF_COLS=6;
    BoardUI getBoardUI();
    int findNextAvailableSpot(int col);
    boolean isLeagalMove(int col);
    boolean existLeagalMoves();
    void updateMove(int col,Piece move);
    void updateMove(int col,int row,Piece move);
    Winner findWinner();
    Piece[][] getPieces();


}
