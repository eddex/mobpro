package ch.eddex.ui_demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void linearLayoutButtonClicked(View view) {
        Intent i = new Intent(this, LayoutDemoActivity.class);
        i.putExtra("view", 0);
        startActivity(i);
    }

    public void relativeLayoutButtonClicked(View view) {
        Intent i = new Intent(this, LayoutDemoActivity.class);
        i.putExtra("view", 1);
        startActivity(i);
    }
}
