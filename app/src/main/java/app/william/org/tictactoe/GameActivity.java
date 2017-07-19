package app.william.org.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    // widgets
    private TextView mTurn_text;
    private Button mQuitButton;

    // tags
    private static final String RESULT_TAG = "app.william.org.tictactoe.GameActivity.result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // wire widgets
        mTurn_text = (TextView) findViewById(R.id.turn_text);
        mQuitButton = (Button) findViewById(R.id.quitbtn);
        mQuitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResultFragment dialog = ResultFragment.newInstance("Bob");
                dialog.show(getSupportFragmentManager(),RESULT_TAG);
            }
        });

        // link images from model into data
    }

    /**
     *
     * @param context actvity called from
     * @return new intent
     */
    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,GameActivity.class);
        return intent;
    }
}
