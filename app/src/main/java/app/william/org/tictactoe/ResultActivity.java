package app.william.org.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_result);
    }

    /**
     *
     * @param context actvity called from
     * @return new intent
     */
    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,ResultActivity.class);
        return intent;
    }

}
