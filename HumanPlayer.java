package lk.ijse.dep.service;

public class HumanPlayer extends Player{
    public HumanPlayer(Board board) {
        super(board);
    }

    @Override
    public void movePiece(int col) {
            if (board.isLeagalMove(col)) {
                board.updateMove(col,Piece.BLUE);
                board.getBoardUI().update(col,true);
                Winner winner=board.findWinner();
                if (!winner.getWinningPiece().equals(Piece.EMPTY)) {
                    board.getBoardUI().notifyWinner(winner);
                }else {
                    if (board.existLeagalMoves() == false) {
                        board.getBoardUI().notifyWinner(new Winner(Piece.EMPTY));
                    }
                }
            }

    }
}
