package ch.eddex.persistenz;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences preferences = this.getPreferences(Context.MODE_PRIVATE);
        int count = preferences.getInt(getString(R.string.resume_count_preference_key), 0) + 1;
        SharedPreferences.Editor edit = preferences.edit();
        edit.putInt(getString(R.string.resume_count_preference_key), count);
        edit.apply();

        TextView label = this.findViewById(R.id.lb_resume_count);
        label.setText(String.format(getString(R.string.test_resume_counter), Integer.toString(count)));
    }
}
