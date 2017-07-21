package app.william.org.tictactoe;

import android.content.Context;
import android.content.Intent;
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

    // tags
    private static final String RESULT_TAG = "app.william.org.tictactoe.GameActivity.result";

    // onSaveInstanceState extras
    private static final String EXTRA_FIRST = "app.william.org.tictactoe.GameActivity.first_turn";
    private static final String EXTRA_MOVE = "app.william.org.tictactoe.GameActivity.move_count";

    private boolean firstPlayerTurn;
    private static Position[][] board;
    private int moveCount;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(EXTRA_FIRST, firstPlayerTurn);
        outState.putInt(EXTRA_MOVE, moveCount);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // initialize or retrieve variables
        if (savedInstanceState != null) {
            firstPlayerTurn = savedInstanceState.getBoolean(EXTRA_FIRST, true);
            moveCount = savedInstanceState.getInt(EXTRA_MOVE, 0);
        } else {
            firstPlayerTurn = true;
            moveCount = 0;
        }

        // wire widgets
        mTurn_text = (TextView) findViewById(R.id.turn_text);
        mTurn_text.setText(
                getString(
                        R.string.turn_title,
                        firstPlayerTurn ?
                                GameData.getInstance().getFirstPlayerName() :
                                GameData.getInstance().getSecondPlayerName()
                )
        );
        mQuitButton = (Button) findViewById(R.id.quitbtn);
        mQuitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(WelcomeActivity.newIntent(GameActivity.this));
                finish();
            }
        });

        // create empty game board
        mGridLayout = (GridLayout) findViewById(R.id.gridLayout);
        if (moveCount == 0) {
            board = new Position[mGridLayout.getRowCount()][mGridLayout.getColumnCount()];
        }

        // initialize board positions and images
        int gridIndex = 0;
        ImageView image;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                image = (ImageView) mGridLayout.getChildAt(gridIndex);
                final int row = i, col = j;

                // Wire each position of grid
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        actionTurn(row, col, GameActivity.this.firstPlayerTurn ? GameData.PLAYER1 : GameData.PLAYER2);
                        firstPlayerTurn = !firstPlayerTurn;
                        mTurn_text.setText(
                                getString(
                                        R.string.turn_title,
                                        firstPlayerTurn ?
                                                GameData.getInstance().getFirstPlayerName() :
                                                GameData.getInstance().getSecondPlayerName()
                                )
                        );
                    }
                });

                // Initializes blank positions or retrieves previous occupants
                if (board[i][j] != null) {
                    board[i][j].image = image;
                    board[i][j].image.setImageDrawable(getDrawable(
                            board[i][j].occupant == GameData.PLAYER1 ?
                                    R.drawable.ic_player1 :
                                    R.drawable.ic_player2
                    ));
                } else {
                    board[i][j] = new Position(image, GameData.BLANK);
                }

                gridIndex++;
            }
        }
    }

    /**
     * @param context actvity called from
     * @return new intent
     */
    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, GameActivity.class);
        return intent;
    }

    /*-------------------------------------------------------- Game Board Methods --------------------------------------------------------*/

    private void actionTurn(int row, int col, int player) {
        Position position = board[row][col];

        // make sure to update model
        if (position.occupant == GameData.BLANK) {

            moveCount++;
            position.occupant = player;

            ImageView image = position.image;
            image.setImageDrawable(
                    getDrawable(
                            player == GameData.PLAYER1 ?
                                    R.drawable.ic_player1 :
                                    R.drawable.ic_player2
                    )
            );

            int result = getWinCondition(row, col, player);
            if (result == player) {
                createResultDialog(
                        player == GameData.PLAYER1 ?
                                GameData.getInstance().getFirstPlayerName() :
                                GameData.getInstance().getSecondPlayerName(),
                        false
                );
            } else if (result == 0) {
                createResultDialog(
                        "",
                        true
                );
            }
        }
    }

    private int getWinCondition(int row, int col, int player) {
        // check for game over condition

        int len = board.length;

        // Check columns
        for (int i = 0; i < len; i++) {
            if (board[row][i].occupant != player)
                break;
            if (i == len - 1) {
                return player;
            }
        }

        // Check rows
        for (int i = 0; i < len; i++) {
            if (board[i][col].occupant != player)
                break;
            if (i == len - 1) {
                return player;
            }
        }

        // Check diagonal
        int i = 0;
        if (row == col) {
            while (i < len) {
                if (board[i][i].occupant != player)
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
                if (board[i][j].occupant != player)
                    break;
                i++;
                j--;
            }
            if (i == len) {
                return player;
            }
        }

        // Check for draw
        if (moveCount == mGridLayout.getChildCount()) {
            return 0; // draw
        }

        return -1;
    }

    private void createResultDialog(String winnerName, boolean isDraw) {
        ResultFragment dialog = ResultFragment.newInstance(winnerName, isDraw);
        dialog.show(getSupportFragmentManager(), RESULT_TAG);
    }

    class Position {
        ImageView image;
        int occupant;

        Position(ImageView image, int player) {
            this.image = image;
            this.occupant = player;
        }
    }
}





























