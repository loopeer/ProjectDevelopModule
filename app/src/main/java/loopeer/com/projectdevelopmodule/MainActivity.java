package loopeer.com.projectdevelopmodule;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onCompatInsetToolbarClick(View view) {
        CompatInsetToolbarActivity.startActivity(this);
    }

    public void onCompatInsetImageHeaderActivity(View view) {
        CompatInsetImageHeaderActivity.startActivity(this);
    }

    public void onSingleCompatInsetImageHeaderActivity(View view) {
        CompatSingleInsetImageHeaderActivity.startActivity(this);
    }

    public void onCompatSingleInsetToolbarActivity(View view) {
        CompatSingleInsetToolbarActivity.startActivity(this);
    }
}
