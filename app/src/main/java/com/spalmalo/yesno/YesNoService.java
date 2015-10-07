package com.spalmalo.yesno;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by kani on 10/7/15.
 */
public interface YesNoService {
    @GET("/api")
    Call<YesNo> getYesNo();
}
