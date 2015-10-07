package com.spalmalo.yesno;

import retrofit.Call;
import retrofit.http.GET;

public interface YesNoService {
    @GET("/api")
    Call<YesNo> getYesNo();
}
