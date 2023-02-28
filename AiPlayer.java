package lk.ijse.dep.service;

public class AiPlayer extends Player{

    public AiPlayer(Board board){
        super(board);
    }
    private char[][] tempBoard = new char[6][5];

    /**
     *
     * @param pieces
     * @return
     */
    private char[][] convert(Piece[][]pieces){
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if(pieces[i][j] == Piece.EMPTY)
                    tempBoard[i][j] = 'e';
                if(pieces[i][j] == Piece.GREEN)
                    tempBoard[i][j] = 'a';
                if(pieces[i][j] == Piece.BLUE)
                    tempBoard[i][j] = 'h';
            }
        }
        return tempBoard;
    }

    @Override
    public void movePiece(int col){
        tempBoard = convert(board.getPieces());
        int bestScore = (int) Double.NEGATIVE_INFINITY;;

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if(tempBoard[i][j]=='e'){
                    tempBoard[i][j] = 'a';
                    int heuristicVal = minimax( 0, false);
                    tempBoard[i][j] = 'e';
                    if(heuristicVal > bestScore) {
                        bestScore = heuristicVal;
                        col = i;
                    }
                }
            }
        }

        if(board.isLeagalMove(col)) {
            board.updateMove(col, Piece.GREEN);
            board.getBoardUI().update(col, false);
            Winner winner = board.findWinner();
            if(!winner.getWinningPiece().equals(Piece.EMPTY)){
                board.getBoardUI().notifyWinner(winner);
            }else{
                if(!board.existLeagalMoves()){
                    board.getBoardUI().notifyWinner(new Winner(Piece.EMPTY));
                }
            }
        }
    }
    private int minimax(int depth, boolean maximisingPlayer) {
        String  winner = findWinner();
        if(depth == 4 || winner != null){
            if(winner == null){
                return 0;
            }
            if(winner.equals("tie"))
                return 0;
            if(winner.equals("ai"))
                return 1;
            if(winner.equals("human"))
                return -1;
        }

        if(maximisingPlayer){

            int maxEval=(int) Double.NEGATIVE_INFINITY;

            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 5; j++) {
                    if(tempBoard[i][j]=='e'){
                        tempBoard[i][j] = 'a';
                        int heuristicVal = minimax(depth+1, false);
                        tempBoard[i][j] = 'e';
                        maxEval = Math.max(maxEval,heuristicVal);
                    }
                }
            }
            return maxEval;
        } else {
            int minEval=(int) Double.POSITIVE_INFINITY;
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 5; j++) {
                    if(tempBoard[i][j]=='e'){
                        tempBoard[i][j] = 'h';
                        int heuristicVal = minimax( depth+1, true);
                        tempBoard[i][j] = 'e';

                        minEval = Math.min(minEval,heuristicVal);
                    }
                }
            }
            return minEval;
        }
    }

    private boolean existsAvailableSpots(){
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if(tempBoard[i][j]=='e')
                    return true;
            }
        }
        return false;
    }
    public void min(){
        int[] a=new int[5];
        a[0]=3;
        a[1]=7;
        a[2]=6;
        a[3]=66;
        a[4]=1;
        int z=a[0];
        for (int i = 0; i <a.length; i++) {
            if (z>a[i]) {
               z=a[i];
            }
        }

    }
    private String findWinner(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                if (tempBoard[j][i]=='a' && tempBoard[j +1][i]=='a' && tempBoard[j +2][i]=='a' && tempBoard[j +3][i]=='a'){
                    return "ai";
                }
                if (tempBoard[j][i]=='h' && tempBoard[j +1][i]=='h' &&  tempBoard[j +2][i]=='h' && tempBoard[j +3][i]=='h'){
                    return "human";
                }
            }
        }

        for (int i = 0; i < 6; i++) {
            for (int c = 0; c < 2; c++) {
                if (tempBoard[i][c]=='a' && tempBoard[i][c +1]=='a' && tempBoard[i][c +2]=='a' && tempBoard[i][c +3]=='a'){
                    return "ai";
                }
                if (tempBoard[i][c]=='h' && tempBoard[i][c +1]=='h' && tempBoard[i][c +2]=='h' && tempBoard[i][c +3]=='h'){
                    return "human";
                }
            }
        }
        if(!existsAvailableSpots())
            return "tie";
        return null;
    }
}