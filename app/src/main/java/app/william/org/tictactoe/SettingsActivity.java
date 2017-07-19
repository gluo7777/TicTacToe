package app.william.org.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    private Button mImgButton1;
    private Button mImgButton2;
    private Button mDefault;
    private Button mDone;
    private Button mCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle(R.string.setting_title);

        setContentView(R.layout.activity_settings);

        // wire widgets
        mImgButton1 = (Button) findViewById(R.id.choose_img1);
        mImgButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mImgButton2 = (Button) findViewById(R.id.choose_img2);
        mImgButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mDefault = (Button) findViewById(R.id.choose_default);
        mDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mDone = (Button) findViewById(R.id.done_btn);
        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mCancel = (Button) findViewById(R.id.cancel_btn);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void savePreferences(){
        // Save preferences to model

        finish();
    }

    private void resetPreferences(){

    }

    private void quitToMainMenu(){
        finish();
    }

    /**
     *
     * @param context actvity called from
     * @return new intent
     */
    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,SettingsActivity.class);
        return intent;
    }
}
