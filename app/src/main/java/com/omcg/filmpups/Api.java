package com.omcg.filmpups;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

import retrofit2.http.Query;

public interface Api {
    String BASE_URL = "http://omcg.ml/api/";

    @GET("movies")
    Call<List<Results>> getMovies();

    @Headers({"Content-Type: application/json"})
    @PUT("movies")
    Call<ResponseBody> putRating(@Query("id") int id,
                                 @Body Rating rating);

    @Headers({"Content-Type: application/json"})
    @POST("movies")
    Call<ResponseBody> postMovie(@Body AddMovie movie);
}
