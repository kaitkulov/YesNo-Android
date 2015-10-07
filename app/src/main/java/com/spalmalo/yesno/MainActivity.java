package com.spalmalo.yesno;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("sldkfnapsjdfpsdfvi", "OLOLOLOLOLO");
            }
        });

       // mMainView = (SimpleDraweeView) findViewById(R.id.mainView);
    }
}
