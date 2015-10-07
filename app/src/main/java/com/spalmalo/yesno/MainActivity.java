package com.spalmalo.yesno;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    private SimpleDraweeView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (SimpleDraweeView) findViewById(R.id.mainView);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://yesno.wtf")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        YesNoService service = retrofit.create(YesNoService.class);

        Call<YesNo> call = service.getYesNo();
        call.enqueue(new Callback<YesNo>() {
            @Override
            public void onResponse(Response<YesNo> response, Retrofit retrofit) {
                Log.i("sldkfnapsjdfpsdfvi", response.body().image);
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setAutoPlayAnimations(true)
                        .setUri(Uri.parse(response.body().image))
                        .build();
                imageView.setController(controller);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("sldkfnapsjdfpsdfvi", "OLOLOLOLOLO");
            }
        });

    }
}
