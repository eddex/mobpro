package ch.eddex.comandcon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openHttpView(View view) {
        Intent i = new Intent(this, HttpDemosActivity.class);
        startActivity(i);
    }

    public void startDemoThreadOnClick(View view) {
        final Button button = findViewById(R.id.btn_demothread);
        button.setText("[DemoThread läuft…]");

        // wait for 7 seconds
        Thread wait7seconds = new Thread(() -> {
            try {
                Thread.sleep(7000);
                runOnUiThread(() -> {
                    String before = getString(R.string.start_demothread);
                    button.setText(before);
                });
            } catch (Exception e) {
                System.out.println("failed");
            }
        });
        wait7seconds.start();
    }

    public void asyncTaskWithProgress(View view) throws MalformedURLException {
        URL url0 = new URL("http://wherever.ch/hslu/title0.txt");
        URL url1 = new URL("http://wherever.ch/hslu/title1.txt");
        URL url2 = new URL("http://wherever.ch/hslu/title2.txt");
        URL url3 = new URL("http://wherever.ch/hslu/title3.txt");
        URL url4 = new URL("http://wherever.ch/hslu/title4.txt");

        MultiAsyncTask multiTasker = new MultiAsyncTask(this);
        multiTasker.execute(url0, url1, url2, url3, url4);
    }
}