package ch.eddex.comandcon;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MultiAsyncTask extends AsyncTask<URL, String, Void>
{
    private WeakReference<MainActivity> activity;
    private OkHttpClient okClient;
    private List<String> filmTitles;

    public MultiAsyncTask(MainActivity activity)
    {
        this.activity = new WeakReference<>(activity);
        this.okClient = new OkHttpClient.Builder().build();
        this.filmTitles = new ArrayList<>();
    }

    @Override
    protected Void doInBackground(URL... urls) {
        for (URL url:urls) {
            Request request = new Request.Builder().url(url).build();
            try {
                Response response = okClient.newCall(request).execute();
                if (response.isSuccessful())
                {
                    String title = response.body().string();
                    filmTitles.add(title);
                    this.publishProgress(title);
                }
                Thread.sleep(1000);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null; // forced by interface
    }

    @Override
    protected void onProgressUpdate(final String... values)
    {
        super.onProgressUpdate(values);
        this.activity.get().runOnUiThread(() -> SendToast(values[0]));
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        final MainActivity mainActivity = this.activity.get();
        mainActivity.runOnUiThread(() -> new AlertDialog.Builder(mainActivity)
                .setTitle("Downloaded Film Titles")
                //.setMessage(String.join("\n", filmTitles)) // as text
                .setItems(filmTitles.toArray(new String[0]), null) // as clickable items
                .show());
    }

    public void SendToast(String text)
    {
        Toast.makeText(this.activity.get().getApplicationContext(), String.format("Downloaded: %s",text), Toast.LENGTH_LONG).show();
    }

}
