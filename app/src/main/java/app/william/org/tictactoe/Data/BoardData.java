package app.william.org.tictactoe.Data;

import android.widget.ImageView;

/**
 * Created by gluo7 on 7/20/2017.
 */

public class BoardData {
    private boolean firstPlayerTurn;
    private int moveCount;
    private int[][] board;

    /**
     *
     * @param isFirstPlayerTurn whether player 1 moves first
     * @param startMoveCount turn count at beginning of game
     * @param row row count of grid
     * @param col col count of grid
     */
    public BoardData(boolean isFirstPlayerTurn, int startMoveCount, int row, int col) {
        this.firstPlayerTurn = isFirstPlayerTurn;
        this.moveCount = startMoveCount;
        board = new int[row][col];
    }

    public boolean isFirstPlayerTurn() {
        return firstPlayerTurn;
    }

    public void setFirstPlayerTurn(boolean firstPlayerTurn) {
        this.firstPlayerTurn = firstPlayerTurn;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void setMoveCount(int moveCount) {
        this.moveCount = moveCount;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

}
