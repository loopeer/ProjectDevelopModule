package loopeer.com.projectdevelopmodule;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CompatInsetToolbarActivity extends AppCompatActivity {

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, CompatInsetToolbarActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compat_inset_toolbar);


    }
}
