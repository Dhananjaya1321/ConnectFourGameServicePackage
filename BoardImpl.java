package lk.ijse.dep.service;

public class BoardImpl implements Board{
    private final Piece[][] pieces=new Piece[NUM_OF_COLS][NUM_OF_ROWS];



    private final BoardUI boardUI;

    public BoardImpl(BoardUI boardUI) {
        this.boardUI=boardUI;
        for (int i = 0; i < NUM_OF_COLS; i++) {
            for (int j = 0; j < NUM_OF_ROWS; j++) {
                pieces[i][j]=Piece.EMPTY;
            }
        }
    }

    @Override
    public BoardUI getBoardUI() {
        return boardUI;
    }

    @Override
    public int findNextAvailableSpot(int col) {
       for (int i = 0; i < NUM_OF_ROWS; i++) {
           if (pieces[col][i].equals(Piece.EMPTY)) {
               return i;
            }
        }
        return -1;
    }

    @Override
    public boolean isLeagalMove(int col) {
        if(findNextAvailableSpot(col)!=-1){
            return true;
        }
        return false;
    }

    @Override
    public boolean existLeagalMoves() {
        for (int i = 0; i < NUM_OF_COLS; i++) {
            for (int j = 0; j < NUM_OF_ROWS; j++) {
                if (pieces[i][j].equals(Piece.EMPTY)) {
                    return true;

                }
            }
        }
        return false;
    }

    @Override
    public void updateMove(int col, Piece move) {
        int row=findNextAvailableSpot(col);
        pieces[col][row]=move;
    }
    public Piece[][] getPieces() {
        return pieces;
    }

    Piece color;
    int tempCol,tempRow;
    @Override
    public void updateMove(int col, int row, Piece move) {
        if (pieces[col][row].equals(Piece.EMPTY)) {
            tempCol=col;
            tempRow=row;
            color=move;
            if (move.equals(Piece.GREEN)) {
                pieces[col][row] = Piece.GREEN;
            } else if (move.equals(Piece.BLUE)) {
                pieces[col][row] = Piece.BLUE;
            }//else {
//                pieces[col][row] = Piece.EMPTY;
//            }
        }else {
            if (tempRow==row && tempCol==col && pieces[tempCol][tempRow].equals(color)) {
                pieces[col][row] = Piece.EMPTY;
            }

        }
    }

    @Override
    public Winner findWinner() {
        Piece p[]=new Piece[6];
        String x;


        for(int j=0;j<NUM_OF_ROWS;j++){
            for(int i=0;i<NUM_OF_COLS;i++){
                p[i]=pieces[i][j];
            }
            for(int k=0;k<3;k++){

                x=p[k]+""+p[k+1]+""+p[k+2]+""+p[k+3];
                if(x.equals("GREENGREENGREENGREEN")){
                    Winner winner=new Winner(Piece.GREEN,k,j,k+3,j);
                    return winner;
                }else if(x.equals("BLUEBLUEBLUEBLUE")){
                    Winner winner=new Winner(Piece.BLUE,k,j,k+3,j);
                    return winner;
                }
            }
        }

        for(int i=0;i<NUM_OF_COLS;i++){
            for(int j=0;j<NUM_OF_ROWS; j++){
                p[j]=pieces[i][j];
            }
            for(int k=0;k<2;k++){

                x=p[k]+""+p[k+1]+""+p[k+2]+""+p[k+3];
                if(x.equals("GREENGREENGREENGREEN")){
                    Winner winner=new Winner(Piece.GREEN,i,k,i,k+3);
                    return winner;
                }else if(x.equals("BLUEBLUEBLUEBLUE")){
                    Winner winner=new Winner(Piece.BLUE,i,k,i,k+3);
                    return winner;
                }
            }

        }
        return  new Winner(Piece.EMPTY,-1,-1,-1,-1);
    }


}
