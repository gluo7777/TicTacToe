package app.william.org.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import java.util.Set;

public class WelcomeActivity extends AppCompatActivity {

    // widgets
    private Button mPlay;
    private Button mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // set action bar properties
        getSupportActionBar().hide();

        // wire widgets
        mPlay = (Button) findViewById(R.id.play_btn);
        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(GameActivity.newIntent(WelcomeActivity.this));
            }
        });
        mSettings = (Button) findViewById(R.id.settings_btn);
        mSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SettingsActivity.newIntent(WelcomeActivity.this));
            }
        });
    }

    /**
     *
     * @param context actvity called from
     * @return new intent
     */
    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,WelcomeActivity.class);
        return intent;
    }
}
