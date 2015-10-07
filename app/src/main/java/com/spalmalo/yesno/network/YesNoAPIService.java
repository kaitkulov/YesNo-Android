package com.spalmalo.yesno.network;

import retrofit.Call;
import retrofit.http.GET;

public interface YesNoAPIService {
    @GET("/api")
    Call<YesNo> getYesNo();
}
