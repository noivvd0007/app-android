package com.vuvannoi.ndapptracnghiem.Login_Activity.presenter;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Admin on 26-07-17.
 */

public interface RequestInterface {
    @POST("learn-login-register/")
    Call<ServerResponse> operation(@Body ServerRequest request);
}
