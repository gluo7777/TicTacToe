package app.william.org.tictactoe.Data;

/**
 * Created by gluo7 on 7/19/2017.
 */

/**
 * Keeps track of user statistics, images
 */
public class GameData {
    // board data
    public static final int BLANK = 0;
    public static final int PLAYER1 = 1;
    public static final int PLAYER2 = 2;
    // data
    private String firstPlayerName;
    private String secondPlayerName;
    private int playerOneScore;
    private int playerTwoScore;

    private static final GameData ourInstance = new GameData();

    public static GameData getInstance() {
        return ourInstance;
    }

    private GameData() {
        // default values
        firstPlayerName = "Player 1";
        secondPlayerName= "Player 2";
        playerOneScore = 0;
        playerTwoScore = 0;
    }

    public String getFirstPlayerName() {
        return firstPlayerName;
    }

    public void setFirstPlayerName(String firstPlayerName) {
        this.firstPlayerName = firstPlayerName;
    }

    public String getSecondPlayerName() {
        return secondPlayerName;
    }

    public void setSecondPlayerName(String secondPlayerName) {
        this.secondPlayerName = secondPlayerName;
    }

    public int getPlayerOneScore() {
        return playerOneScore;
    }

    public void setPlayerOneScore(int playerOneScore) {
        this.playerOneScore = playerOneScore;
    }

    public int getPlayerTwoScore() {
        return playerTwoScore;
    }

    public void setPlayerTwoScore(int playerTwoScore) {
        this.playerTwoScore = playerTwoScore;
    }
}
