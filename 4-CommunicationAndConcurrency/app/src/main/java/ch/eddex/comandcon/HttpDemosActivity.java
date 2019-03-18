package ch.eddex.comandcon;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class HttpDemosActivity extends AppCompatActivity {
    private OkHttpClient okClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_demos);
        okClient = new OkHttpClient.Builder().build();
    }

    public void loadBinaryOnClick(View view) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder().url("http://www.wherever.ch/hslu/homer.jpg").build();
                try {
                    Response response = okClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        final Bitmap image = BitmapFactory.decodeStream(response.body().byteStream());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ImageView imageView = (ImageView) findViewById(R.id.imageViewForHomer);
                                imageView.setImageBitmap(image);
                            }
                        });
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void loadTextOnClick(View view) {
        AsyncTask.execute(() -> {
            Request request = new Request.Builder().url("http://www.wherever.ch/hslu//loremIpsum.txt").build();
            try {
                Response response = okClient.newCall(request).execute();
                if (response.isSuccessful()) {
                    final String image = response.body().string();
                    runOnUiThread(() -> {
                        TextView imageView = (TextView) findViewById(R.id.textView2);
                        imageView.setText(image);
                    });
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void loadJsonOnClick(View view) {
        AsyncTask.execute(() -> {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(new OkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://www.nactem.ac.uk/software/acromine/")
                    .build();
            AcronymService service = retrofit.create(AcronymService.class);
            try {
                retrofit2.Response<List<AcronymDef>> response = service.getDefinitionsOf("http").execute();
                if (response.isSuccessful()) {
                    showJson(response.body());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void showJson(List<AcronymDef> acronyms) {
        TextView view = findViewById(R.id.textView);
        StringBuilder builder = new StringBuilder();
        for (AcronymDef acronym : acronyms) {
            builder.append(acronym.sf);
            builder.append(':');
            for (AcronymDef.LongForm lf : acronym.lfs) {
                builder.append(lf.lf);
                builder.append(" (since ");
                builder.append(lf.since);
                builder.append(')');
            }
        }
        view.setText(builder.toString());
    }

    public interface AcronymService {
        @GET("dictionary.py")
        Call<List<AcronymDef>> getDefinitionsOf(@Query("sf") String sf);
    }
}