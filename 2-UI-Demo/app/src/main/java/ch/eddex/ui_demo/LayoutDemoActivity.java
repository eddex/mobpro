package ch.eddex.ui_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class LayoutDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Integer v = getIntent().getIntExtra("view", 0);
        if (v > 0) {
            setContentView(R.layout.layoutdemo_constraintlayout);
        }
        else {
            setContentView(R.layout.layoutdemo_linearlayout);
        }

    }

}
