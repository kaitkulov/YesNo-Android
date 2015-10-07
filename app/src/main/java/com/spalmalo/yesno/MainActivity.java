package com.spalmalo.yesno;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.spalmalo.yesno.network.YesNo;
import com.spalmalo.yesno.network.YesNoAPIService;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String BASE_URL = "http://yesno.wtf";
    private SimpleDraweeView imageView;
    private TextView answerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (SimpleDraweeView) findViewById(R.id.mainView);
        answerView= (TextView) findViewById(R.id.answerTextView);
        getAnswer();
    }

    private void getAnswer() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        YesNoAPIService service = retrofit.create(YesNoAPIService.class);

        Call<YesNo> call = service.getYesNo();
        call.enqueue(new Callback<YesNo>() {
            @Override
            public void onResponse(Response<YesNo> response, Retrofit retrofit) {
                Log.i(TAG, response.body().image);
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setAutoPlayAnimations(true)
                        .setUri(Uri.parse(response.body().image))
                        .build();
                imageView.setController(controller);
                answerView.setText(response.body().answer);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }
}
