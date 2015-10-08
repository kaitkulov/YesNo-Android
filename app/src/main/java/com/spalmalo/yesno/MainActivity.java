package com.spalmalo.yesno;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
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
    private YesNo mAnswer;
    private Retrofit mRetrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        imageView = (SimpleDraweeView) findViewById(R.id.mainView);
        answerView= (TextView) findViewById(R.id.answerTextView);
        getAnswer();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAnswer();
            }
        });
    }

    private void getAnswer() {
        answerView.setVisibility(View.GONE);

        YesNoAPIService service = mRetrofit.create(YesNoAPIService.class);

        Call<YesNo> call = service.getYesNo();
        call.enqueue(new Callback<YesNo>() {
            @Override
            public void onResponse(Response<YesNo> response, Retrofit retrofit) {
                Log.i(TAG, response.body().image);
                mAnswer = response.body();
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setAutoPlayAnimations(true)
                        .setControllerListener(controllerListener)
                        .setUri(Uri.parse(response.body().image))
                        .build();
                imageView.setController(controller);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    ControllerListener controllerListener=new BaseControllerListener<ImageInfo>(){
        @Override
        public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
            super.onFinalImageSet(id, imageInfo, animatable);
            answerView.setVisibility(View.VISIBLE);
            answerView.setText(mAnswer.answer);
        }

        @Override
        public void onFailure(String id, Throwable throwable) {
            super.onFailure(id, throwable);
        }
    };
}
