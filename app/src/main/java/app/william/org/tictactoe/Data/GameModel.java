package app.william.org.tictactoe.Data;

/**
 * Keeps track of game statistics
 */
class GameModel {
    private int[] board;

    public GameModel(){
        board = new int[9];
    }

    public boolean getOccupied(int index){
        return board[index]==0;
    }

}
