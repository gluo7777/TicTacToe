package app.william.org.tictactoe;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import app.william.org.tictactoe.Data.GameData;

/**
 * Created by gluo7 on 7/19/2017.
 */

public class ResultFragment extends DialogFragment {

    // constants
    public static final String EXTRA_WINNER = "app.william.org.tictactoe.ResultFragment.winner";
    public static final String EXTRA_DRAW = "app.william.org.tictactoe.ResultFragment.draw";

    // widgets
    private TextView mWinnerText;
    private TextView mScoreText;
    private Button mReplayButton;
    private Button mMainMenuButton;

    /**
     * Creates a new instance of a dialog fragment that displays the result
     *
     * @param winner passed in as a parameter from game activity
     * @return a new dialog fragment to be displayed in game activity
     */
    public static ResultFragment newInstance(String winner, boolean isDraw) {
        Bundle args = new Bundle();
        args.putString(EXTRA_WINNER, winner);
        args.putBoolean(EXTRA_DRAW, isDraw);
        ResultFragment fragment = new ResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Wire views and widgets
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.dialog_result, null);
        mWinnerText = view.findViewById(R.id.winnerText);

        // Retrieve extras
        String winner = getArguments().getString(EXTRA_WINNER);
        boolean isDraw = getArguments().getBoolean(EXTRA_DRAW);

        // Wire TextViews
        if(isDraw){
            mWinnerText.setText(R.string.draw_text);
        }else{
            mWinnerText.setText(getString(R.string.winner_text,winner));
            // adjust scores
            if(winner == GameData.getInstance().getFirstPlayerName()){
                GameData.getInstance().setPlayerOneScore(GameData.getInstance().getPlayerOneScore() + 1);
            }else{
                GameData.getInstance().setPlayerTwoScore(GameData.getInstance().getPlayerTwoScore() + 1);
            }
        }
        mScoreText = view.findViewById(R.id.scoreText);
        mScoreText.setText(getString(
                R.string.score_text,
                GameData.getInstance().getFirstPlayerName(),
                GameData.getInstance().getPlayerOneScore(),
                GameData.getInstance().getSecondPlayerName(),
                GameData.getInstance().getPlayerTwoScore()
        ));

        // Wire Buttons
        mReplayButton = (Button) view.findViewById(R.id.reply_btn);
        mReplayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(GameActivity.newIntent(getActivity()));
                getActivity().finish();
            }
        });
        mMainMenuButton = (Button) view.findViewById(R.id.main_menu_btn);
        mMainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(WelcomeActivity.newIntent(getActivity()));
                getActivity().finish();
            }
        });

        // Build and return an alert dialog
        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.result_title)
                .create();
        return dialog;
    }
}
