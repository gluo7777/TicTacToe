package app.william.org.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import app.william.org.tictactoe.Data.*;

public class GameActivity extends AppCompatActivity {

    // widgets
    private GridLayout mGridLayout;
    private TextView mTurn_text;
    private Button mQuitButton;

    // fragments
    GameFragment mGameFragment;

    // tags
    private static final String TAG_RESULT = "app.william.org.tictactoe.GameActivity.result";
    private static final String TAG_FRAG_GAME = "app.william.org.tictactoe.GameActivity.frag_game";

    // board
    private BoardData mBoardData;

    /**
     * inflates views
     * set or reset game board
     * @param savedInstanceState  does nothing
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // empty board
        mGridLayout = (GridLayout) findViewById(R.id.gridLayout);

        // create new data or retrieve data from fragment
        FragmentManager manager = getSupportFragmentManager();
        mGameFragment = (GameFragment) manager.findFragmentByTag(TAG_FRAG_GAME);
        if(mGameFragment == null){
            mBoardData = new BoardData(true,0,mGridLayout.getRowCount(),mGridLayout.getColumnCount());
            mGameFragment = GameFragment.newInstance();
            manager.beginTransaction().add(mGameFragment,TAG_FRAG_GAME).commit();
            mGameFragment.setBoardData(mBoardData);
        }else {
            mBoardData = mGameFragment.getBoardData();
        }

        // turn title
        mTurn_text = (TextView) findViewById(R.id.turn_text);
        setTurnText(mBoardData.isFirstPlayerTurn());

        // quit button
        mQuitButton = (Button) findViewById(R.id.quitbtn);
        mQuitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(WelcomeActivity.newIntent(GameActivity.this));
                finish();
            }
        });

        // initialize board positions and images
        final int[][] board = mBoardData.getBoard();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                final ImageView image = (ImageView) mGridLayout.getChildAt(getGridPos(i,j,board.length));
                final int row = i, col = j;

                // Wire each position of grid
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(isPositionEmpty(row,col)) {
                            final int newGridPos = updateBoard(row, col);
                            setImagePos(image, newGridPos);
                            nextTurn();
                            setTurnText(mBoardData.isFirstPlayerTurn());
                            final int result = getWinCondition(row, col, board[row][col]);
                            createResultDialog(result,getOccupantName(board[row][col]));
                        }
                    }
                });

                // Resets previous occupants upon configuration changes
                setImagePos(image,board[i][j]);
            }
        }
    }

    /**
     * Overridden for trash cleaning purposes
     */
    @Override
    protected void onPause() {
        super.onPause();

        // determines if activity has finished or just paused
        if(isFinishing()){
            FragmentManager manager = getSupportFragmentManager();
            // clean up fragment
            manager.beginTransaction().remove(mGameFragment).commit();
        }
    }

    /**
     * set gridlayout position to appropriate images
     * @param imageHolder ImageView located inside a gridlayout
     * @param occupant player occupying the grid position
     */
    private void setImagePos(ImageView imageHolder, int occupant){
        if(occupant != GameData.BLANK) {
            imageHolder.setImageDrawable(getDrawable(
                    occupant == GameData.PLAYER1 ?
                            R.drawable.ic_player1 :
                            R.drawable.ic_player2
            ));
        }
    }

    /**
     *
     * @param row
     * @param col
     * @param width
     * @return 1-d array position
     */
    private int getGridPos(int row, int col, int width){
        return row * width + col;
    }

    /**
     * @param context actvity called from
     * @return new intent
     */
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, GameActivity.class);
        return intent;
    }

    /**
     * Creates result dialog fragment
     * @param result
     * @param name
     */
    private void createResultDialog(int result, String name) {
        ResultFragment dialog;
        switch (result){
            case -1: // game not over
                return;
            case 0: // draw
                dialog = ResultFragment.newInstance(name, true);
                break;
            default: // a player has won
                dialog = ResultFragment.newInstance(name, false);
                break;
        }
        dialog.show(getSupportFragmentManager(), TAG_RESULT);
    }

    /**
     * sets text on top of game
     * @param isFirst
     */
    private void setTurnText(boolean isFirst){
        mTurn_text.setText(
                getString(
                        R.string.turn_title,
                        isFirst ?
                                GameData.getInstance().getFirstPlayerName() :
                                GameData.getInstance().getSecondPlayerName()
                )
        );
    }

    /*-------------------------------------------------------- Game Board Methods --------------------------------------------------------*/

    /**
     * updates occupant to new player
     * @param row
     * @param col
     * @return
     */
    private int updateBoard(int row, int col) {
       return mBoardData.getBoard()[row][col] = mBoardData.isFirstPlayerTurn() ?
                GameData.PLAYER1 :
                GameData.PLAYER2;
    }

    /**
     *
     * @param row
     * @param col
     * @param player
     * @return player that won | 0 for draw | -1 if neither
     */
    private int getWinCondition(int row, int col, int player) {
        // check for game over condition

        int[][] board = mBoardData.getBoard();
        int len = board.length;

        // Check columns
        for (int i = 0; i < len; i++) {
            if (board[row][i] != player)
                break;
            if (i == len - 1) {
                return player;
            }
        }

        // Check rows
        for (int i = 0; i < len; i++) {
            if (board[i][col] != player)
                break;
            if (i == len - 1) {
                return player;
            }
        }

        // Check diagonal
        int i = 0;
        if (row == col) {
            while (i < len) {
                if (board[i][i] != player)
                    break;
                i++;
            }
            if (i == len) {
                return player;
            }
        }

        // Check counter-diagonal
        i = 0;
        int j = 2;
        if (row + col == len - 1) {
            while (i < len) {
                if (board[i][j] != player)
                    break;
                i++;
                j--;
            }
            if (i == len) {
                return player;
            }
        }

        // Check for draw
        if (mBoardData.getMoveCount() == mGridLayout.getChildCount()) {
            return 0; // draw
        }

        return -1;
    }

    /**
     * resets isFirstPlayerTurn and increments getMoveCount
     */
    public void nextTurn(){
        mBoardData.setFirstPlayerTurn(!mBoardData.isFirstPlayerTurn());
        mBoardData.setMoveCount(mBoardData.getMoveCount() + 1);
    }

    public String getOccupantName(int player){
        switch (player){
            case GameData.PLAYER1:
                return GameData.getInstance().getFirstPlayerName();
            case GameData.PLAYER2:
                return GameData.getInstance().getSecondPlayerName();
            default:
                return "";
        }
    }

    /**
     *
     * @param row
     * @param col
     * @return true if blank occupant
     */
    public boolean isPositionEmpty(int row, int col){
        return mBoardData.getBoard()[row][col] == GameData.BLANK;
    }
}





























